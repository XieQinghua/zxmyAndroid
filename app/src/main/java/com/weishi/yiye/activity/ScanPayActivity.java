package com.weishi.yiye.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.activity.mine.SetPaymentActivity;
import com.weishi.yiye.activity.order.PaySuccessActivity;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.eventbus.ChangeScoreEvent;
import com.weishi.yiye.bean.eventbus.OrderActionEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.ValidatorUtils;
import com.weishi.yiye.databinding.ActivityScanPayBinding;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/4
 * @Description：扫码支付
 * @Version:v1.0.0
 *****************************/
public class ScanPayActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ScanPayActivity.class.getSimpleName();

    private ActivityScanPayBinding payScoreBinding;
    private String payPassWd = StringConstants.STR_EMPTY;
    private double payAmount;
    private int storeId = 0;
    private String storeName = StringConstants.STR_EMPTY;

    @Override
    protected void initView() {
        payScoreBinding = DataBindingUtil.setContentView(ScanPayActivity.this, R.layout.activity_scan_pay);
        setTitleCenter("扫码支付");
        storeId = getIntent().getIntExtra(ShopConstants.KEY_SHOP_ID, 0);
        storeName = getIntent().getStringExtra(ShopConstants.KEY_SHOP_NAME);
        payScoreBinding.btnAffirmPayment.setOnClickListener(this);
        payScoreBinding.tvShopName.setText(storeName);
        //动态监听支付积分的输入
        payScoreBinding.etPaymentScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ValidatorUtils.isNotEmptyString(s.toString())) {
                    double textContent = Double.parseDouble(s.toString());
                    payScoreBinding.tvServiceCharge.setText("注：需收取10%服务费，合计支付" + (textContent * 110 / 100));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void initData() {
        //检查是否设置支付密码
        if (!mSp.getBoolean(Constants.IS_EXISTS, false)) {
            startActivity(new Intent(ScanPayActivity.this, SetPaymentActivity.class));
        }
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
        payPassWd = payScoreBinding.etPaymentCode.getText().toString().trim();

        if (ValidatorUtils.isEmptyString(payScoreBinding.etPaymentScore.getText().toString())) {
            Toast.makeText(ScanPayActivity.this, "请输入支付积分", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ValidatorUtils.isEmptyString(payPassWd)) {
            Toast.makeText(ScanPayActivity.this, "请输入支付密码", Toast.LENGTH_SHORT).show();
            return;
        }
        payAmount = Double.parseDouble(payScoreBinding.etPaymentScore.getText().toString());

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("token", mSp.getString(Constants.TOKEN, ""));
            jsonParams.put("payPasswd", payPassWd);
            jsonParams.put("payPoint", payAmount);
            jsonParams.put("storeId", storeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.SCAN_QRCODE_PAY, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            //支付成功
                            Intent intent = new Intent(ScanPayActivity.this, PaySuccessActivity.class);
                            intent.putExtra("orderPrice", payAmount);
                            startActivity(intent);

                            EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.SCORE_PAY));
                            EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.PAY_SUCCESS));

                            finish();
                        } else {
                            Toast.makeText(ScanPayActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
