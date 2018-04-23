package com.weishi.yiye.activity.mine;

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
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.BusinessApplyBean;
import com.weishi.yiye.bean.PayResultBean;
import com.weishi.yiye.bean.RechargeBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityShopsJoinBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：商家入驻
 * @Version:v1.0.0
 *****************************/

public class ShopsJoinActivity extends BaseSwipeBackActivity {
    private static final String TAG = ShopsJoinActivity.class.getSimpleName();
    private ActivityShopsJoinBinding shopsJoinBinding;

    @Override
    protected void initView() {
        shopsJoinBinding = DataBindingUtil.setContentView(ShopsJoinActivity.this, R.layout.activity_shops_join);
        setTitleCenter("商家入驻");
    }

    @Override
    protected void initData() {
        HttpUtils.doGet(Api.GET_BUSINESS_APPLY + "/" + mSp.getInt(Constants.USER_ID, 0), null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final BusinessApplyBean businessApplyBean = GsonUtil.GsonToBean(result, BusinessApplyBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(businessApplyBean.getCode())) {
                            switch (businessApplyBean.getData().getApplyStatus()) {
                                case 0:
                                    shopsJoinBinding.btnJoin.setText("正在审核");
                                    shopsJoinBinding.btnJoin.setClickable(false);
                                    break;
                                case 1:
                                    shopsJoinBinding.btnJoin.setText("审核通过");
                                    shopsJoinBinding.btnJoin.setClickable(false);
                                    break;
                                case 2:
                                    shopsJoinBinding.btnJoin.setText("立即入驻");
                                    shopsJoinBinding.btnJoin.setClickable(true);
                                    shopsJoinBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(ShopsJoinActivity.this, ShopsJoinDataActivity.class));
                                            finish();
                                        }
                                    });
                                    break;
                                case 3:
                                    shopsJoinBinding.btnJoin.setText("支付入驻费" + businessApplyBean.getData().getPayMoney() + "元");
                                    shopsJoinBinding.btnJoin.setClickable(true);
                                    shopsJoinBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new PayPopupWindows(ShopsJoinActivity.this,
                                                    shopsJoinBinding.btnJoin,
                                                    businessApplyBean.getData().getBusinessId() + "");
                                        }
                                    });
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 选择支付方式弹窗
     */
    private class PayPopupWindows extends PopupWindow {

        private PayPopupWindows(final Context mContext, View parent, final String orderNum) {
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
            jsonParams.put("payType", "ApplyMerchantPay");
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
                Toast.makeText(ShopsJoinActivity.this, "银联支付暂未开通", Toast.LENGTH_SHORT).show();
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
        IWXAPI api = WXAPIFactory.createWXAPI(ShopsJoinActivity.this, Constants.WETCHAT_APP_ID, true);
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
            Toast.makeText(ShopsJoinActivity.this, "您的手机不支持微信支付！", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(ShopsJoinActivity.this, "商家入驻申请提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ShopsJoinActivity.this, "支付失败", Toast.LENGTH_LONG).show();
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
                PayTask alipay = new PayTask(ShopsJoinActivity.this);
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
