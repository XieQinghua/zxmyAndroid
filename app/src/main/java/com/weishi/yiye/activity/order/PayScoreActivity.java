package com.weishi.yiye.activity.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.mine.SetPaymentActivity;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.OrderPayBean;
import com.weishi.yiye.bean.OrderPayDetailBean;
import com.weishi.yiye.bean.PayResultBean;
import com.weishi.yiye.bean.RechargeBean;
import com.weishi.yiye.bean.eventbus.ChangeScoreEvent;
import com.weishi.yiye.bean.eventbus.OrderActionEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityPayScoreBinding;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/4
 * @Description：积分支付
 * @Version:v1.0.0
 *****************************/

public class PayScoreActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = PayScoreActivity.class.getSimpleName();

    private ActivityPayScoreBinding payScoreBinding;

    private String orderNum;
    private String payPassWd = "";

    private double orderPrice, payInBalance, payInCash;

    @Override
    protected void initView() {
        payScoreBinding = DataBindingUtil.setContentView(PayScoreActivity.this, R.layout.activity_pay_score);
        setTitleCenter("余额支付");

        orderNum = getIntent().getStringExtra("orderNum");

        payScoreBinding.btnAffirmPayment.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //检查是否设置支付密码
        if (!mSp.getBoolean(Constants.IS_EXISTS, false)) {
            startActivity(new Intent(PayScoreActivity.this, SetPaymentActivity.class));
        }
        getOrderPayDetail();
    }

    /**
     * 获取订单支付信息
     */
    private void getOrderPayDetail() {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("oid", orderNum);
        mapParams.put("uid", mSp.getInt(Constants.USER_ID, 0));
        HttpUtils.doGet(Api.GEI_ORDER_PAY_DETAIL, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final OrderPayDetailBean orderPayDetailBean = GsonUtil.GsonToBean(result, OrderPayDetailBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(orderPayDetailBean.getCode())) {
                            if (orderPayDetailBean.getData() != null) {
                                orderPrice = orderPayDetailBean.getData().getAmount();
                                payInBalance = orderPayDetailBean.getData().getPayInBalance();
                                payInCash = orderPayDetailBean.getData().getPayInCash();
                                payScoreBinding.tvPaymentScore.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(orderPrice));
                                payScoreBinding.tvPayInBalance.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(payInBalance));
                                payScoreBinding.tvPayInCash.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(payInCash));
                            }
                        } else {
                            Toast.makeText(PayScoreActivity.this, orderPayDetailBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_affirm_payment:
                payScore();
                break;
            default:
                break;
        }
    }

    /**
     * 积分支付
     */
    private void payScore() {
        payScoreBinding.btnAffirmPayment.setClickable(false);
        payPassWd = payScoreBinding.etPaymentCode.getText().toString().trim();

        //校验六位数字
        if (payPassWd.equals("")) {
            Toast.makeText(PayScoreActivity.this, "请输入六位数字支付密码", Toast.LENGTH_SHORT).show();
            return;
        }

        startAnim(null);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("token", mSp.getString(Constants.TOKEN, ""));
            jsonParams.put("payPasswd", payPassWd);
            jsonParams.put("orderPrice", orderPrice);
            jsonParams.put("orderNum", orderNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.ORDER_PAY, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final OrderPayBean orderPayBean = GsonUtil.GsonToBean(result, OrderPayBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(orderPayBean.getCode())) {
                            if (orderPayBean.getData().isIsNeedToPay()) {
                                //现金支付
                                new PopupWindows(PayScoreActivity.this, payScoreBinding.btnAffirmPayment, orderNum);
                                EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.SCORE_PAY));
                                payScoreBinding.btnAffirmPayment.setClickable(true);
                            } else {
                                //支付成功
                                Intent intent = new Intent(PayScoreActivity.this, PaySuccessActivity.class);
                                intent.putExtra("orderPrice", orderPrice);
                                startActivity(intent);

                                EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.SCORE_PAY));
                                EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.PAY_SUCCESS));

                                finish();
                            }
                        } else {
                            Toast.makeText(PayScoreActivity.this, orderPayBean.getMessage(), Toast.LENGTH_SHORT).show();
                            payScoreBinding.btnAffirmPayment.setClickable(true);
                        }

                    }
                });
            }
        });
    }

    /**
     * 选择支付方式弹窗
     */
    private class PopupWindows extends PopupWindow {

        private PopupWindows(final Context mContext, View parent, final String orderNum) {
            View view = View.inflate(mContext, R.layout.popup_payment_mode, null);
            RelativeLayout rl_popup = (RelativeLayout) view.findViewById(R.id.rl_popup);
            rl_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new ColorDrawable(0xb0000000));
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int height = view.findViewById(R.id.rl_popup).getTop();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            update();

            ImageView del = (ImageView) view.findViewById(R.id.iv_del_pop);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            final RadioButton rbAlipay = (RadioButton) view.findViewById(R.id.rb_alipay);
            final RadioButton rbWeChatPay = (RadioButton) view.findViewById(R.id.rb_wechat_pay);
            final RadioButton rbUnionPay = (RadioButton) view.findViewById(R.id.rb_unionpay);
            Button btnPayment = (Button) view.findViewById(R.id.btn_payment);
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rbAlipay.isChecked()) {
                        recharge(orderNum, Constants.ALIPAY_APP);
                        dismiss();
                    } else if (rbWeChatPay.isChecked()) {
                        recharge(orderNum, Constants.TENGPAY_APP);
                        dismiss();
                    } else if (rbUnionPay.isChecked()) {
                        recharge(orderNum, Constants.UNIONPAY_APP);
                        dismiss();
                    }
                }
            });
        }
    }

    /**
     * 积分充值
     *
     * @param orderNum
     * @param paymentMode AlipayApp 支付宝APP支付 TengPayApp 微信APP支付
     */
    private void recharge(String orderNum, String paymentMode) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("payMethod", paymentMode);
            jsonParams.put("payType", "OnlineOrderPay");
            //jsonParams.put("amount", scoreNumebr);
            jsonParams.put("objId", orderNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (paymentMode) {
            case Constants.ALIPAY_APP:
                HttpUtils.doPost(Api.USER_SCORE_RECHARGE, jsonParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.e(TAG, result);

                        final RechargeBean rechargeBean = GsonUtil.GsonToBean(result, RechargeBean.class);
                        //支付宝支付
                        aliPay(rechargeBean);
                    }
                });
                break;
            case Constants.TENGPAY_APP:
                HttpUtils.doPost(Api.USER_SCORE_RECHARGE, jsonParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.e(TAG, result);

                        final RechargeBean rechargeBean = GsonUtil.GsonToBean(result, RechargeBean.class);
                        //微信支付
                        wechatPay(rechargeBean);
                    }
                });
                break;
            case Constants.UNIONPAY_APP:
                Toast.makeText(PayScoreActivity.this, "银联支付暂未开通", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 充值-微信支付处理
     *
     * @param rechargeBean 充值参数实体类
     */
    private void wechatPay(RechargeBean rechargeBean) {
        // 通过WXAPIFactory工厂，获取IWXAPI实例
        IWXAPI api = WXAPIFactory.createWXAPI(PayScoreActivity.this, Constants.WETCHAT_APP_ID, true);
        api.registerApp(Constants.WETCHAT_APP_ID);
        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            // 将应用的appId注册到微信
            req.appId = Constants.WETCHAT_APP_ID;
            req.partnerId = rechargeBean.getData().wxdata.partnerid;
            req.prepayId = rechargeBean.getData().wxdata.prepayid;
            req.packageValue = "Sign=WXPay";
            req.nonceStr = rechargeBean.getData().wxdata.noncestr;
            req.timeStamp = rechargeBean.getData().wxdata.timestamp;
            req.sign = rechargeBean.getData().wxdata.sign;
            api.sendReq(req);
        } else {
            Toast.makeText(PayScoreActivity.this, "您的手机不支持微信支付！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 支付宝成功返回码
     */
    public static final String SDK_PAY_FLAG_SUCCESS = "9000";
    /**
     * 支付宝消息通知
     */
    private static final int SDK_PAY_FLAG = 10000;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResultBean payResult = new PayResultBean((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, SDK_PAY_FLAG_SUCCESS)) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent(PayScoreActivity.this, PaySuccessActivity.class);
                        intent.putExtra("orderPrice", orderPrice);
                        startActivity(intent);

                        EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.SCORE_PAY));
                        EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.PAY_SUCCESS));

                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayScoreActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 充值-支付宝支付处理
     *
     * @param rechargeBean 充值参数实体类
     */
    private void aliPay(final RechargeBean rechargeBean) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayScoreActivity.this);
                Map<String, String> result = alipay.payV2(rechargeBean.getData().orderStr, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
