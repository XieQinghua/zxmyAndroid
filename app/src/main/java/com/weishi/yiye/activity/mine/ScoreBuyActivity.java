package com.weishi.yiye.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.weishi.yiye.adapter.ScoreBuyAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.PayResultBean;
import com.weishi.yiye.bean.RechargeBean;
import com.weishi.yiye.bean.ScoreBalanceBean;
import com.weishi.yiye.bean.ScoreBean;
import com.weishi.yiye.bean.eventbus.ChangeScoreEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.databinding.ActivityScoreBuyBinding;
import com.weishi.yiye.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：积分充值
 * @Version:v1.0.0
 *****************************/

public class ScoreBuyActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ScoreBuyActivity.class.getSimpleName();

    private ActivityScoreBuyBinding scoreBuyBinding;

    private CheckPermission checkPermission;

    private ScoreBuyAdapter scoreBuyAdapter;
    private ArrayList<ScoreBean.DataBean> scoreDatas;

    @Override
    protected void initView() {
        scoreBuyBinding = DataBindingUtil.setContentView(ScoreBuyActivity.this, R.layout.activity_score_buy);
        setTitleCenter("余额充值");

        scoreBuyAdapter = new ScoreBuyAdapter(ScoreBuyActivity.this, R.layout.item_score_buy);
        scoreBuyBinding.gvScoreBuy.setAdapter(scoreBuyAdapter);
        scoreBuyBinding.gvScoreBuy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new PopupWindows(ScoreBuyActivity.this, view, scoreDatas.get(position).getId() + "");
            }
        });

        scoreBuyBinding.tvWechatOfficial.setOnClickListener(this);
        scoreBuyBinding.tvServiceTel.setOnClickListener(this);
        checkPermission = new CheckPermission(ScoreBuyActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                callPhone();
            }

            @Override
            public void negativeButton() {
                Toast.makeText(ScoreBuyActivity.this, "没有权限无法拨打电话", Toast.LENGTH_LONG).show();
            }
        };

        //获取积分余额
        getScoreBalance();
    }

    @Override
    protected void initData() {
        //获取积分模板
        HttpUtils.doGet(Api.GET_RECHARGE_SET, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e(TAG, result);

                final ScoreBean scoreBean = GsonUtil.GsonToBean(result, ScoreBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (scoreBean != null &&
                                Api.STATE_SUCCESS.equals(scoreBean.getCode()) &&
                                scoreBean.getData() != null &&
                                scoreBean.getData().size() != 0) {
                            scoreDatas = (ArrayList<ScoreBean.DataBean>) scoreBean.getData();
                            scoreBuyAdapter.setData(scoreDatas);
                            scoreBuyAdapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
            }
        });
    }

    private void getScoreBalance() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("uid", SPUtils.getInstance().getInt(Constants.USER_ID, 0));

        HttpUtils.doGet(Api.GET_SCORE_BALANCE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e(TAG, result);

                final ScoreBalanceBean scoreBalanceBean = GsonUtil.GsonToBean(result, ScoreBalanceBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (scoreBalanceBean != null && Api.STATE_SUCCESS.equals(scoreBalanceBean.getCode())) {
                            scoreBuyBinding.tvScoreNumber.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format((double) scoreBalanceBean.getData().getTotalScore() / 10000));
                        } else {
                            scoreBuyBinding.tvScoreNumber.setText(getString(R.string.money_unit) + "0.00");
                        }
                    }
                });
            }
        });
    }

    /**
     * 积分充值
     *
     * @param rechargeTempId 模板ID
     * @param paymentMode    AlipayApp 支付宝APP支付 TengPayApp 微信APP支付
     */
    private void recharge(String rechargeTempId, String paymentMode) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("payMethod", paymentMode);
            jsonParams.put("payType", "Recharge");
            //jsonParams.put("amount", scoreNumebr);
            jsonParams.put("rechargeTempId", rechargeTempId);
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
                Toast.makeText(ScoreBuyActivity.this, "银联支付暂未开通", Toast.LENGTH_SHORT).show();
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
        IWXAPI api = WXAPIFactory.createWXAPI(ScoreBuyActivity.this, Constants.WETCHAT_APP_ID, true);
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
            Toast.makeText(ScoreBuyActivity.this, "您的手机不支持微信支付！", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(ScoreBuyActivity.this, "支付成功", Toast.LENGTH_LONG).show();

                        EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.SCORE_BUY));

                        //finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ScoreBuyActivity.this, "支付失败", Toast.LENGTH_LONG).show();
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
                PayTask alipay = new PayTask(ScoreBuyActivity.this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat_official:
                //TODO 跳转微信服务号
                break;
            case R.id.tv_service_tel:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_PHONE);
                } else {
                    callPhone();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 拨打客服电话
     */
    public void callPhone() {
        CustomDialog.Builder builder = new CustomDialog.Builder(ScoreBuyActivity.this);
        builder.setMessage( getResources().getString(R.string.yiye_service_tel_number));
        builder.setTitleVisibility(View.GONE);
        builder.setPositiveButton("呼叫", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getResources().getString(R.string.yiye_service_tel_number)));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 选择支付方式弹窗
     */
    private class PopupWindows extends PopupWindow {

        private PopupWindows(final Context mContext, View parent, final String rechargeTempId) {
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
                        recharge(rechargeTempId, Constants.ALIPAY_APP);
                        dismiss();
                    } else if (rbWeChatPay.isChecked()) {
                        recharge(rechargeTempId, Constants.TENGPAY_APP);
                        dismiss();
                    } else if (rbUnionPay.isChecked()) {
                        recharge(rechargeTempId, Constants.UNIONPAY_APP);
                        dismiss();
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateScore(ChangeScoreEvent changeScoreEvent) {
        getScoreBalance();
    }
}