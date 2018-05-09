package com.weishi.yiye.activity.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.GoodsDetailBean;
import com.weishi.yiye.bean.SubmitOrderBean;
import com.weishi.yiye.bean.eventbus.OrderActionEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivitySubmitOrderBinding;
import com.weishi.yiye.view.AmountView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/2
 * @Description：提交订单页面
 * @Version:v1.0.0
 *****************************/
public class SubmitOrderActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = SubmitOrderActivity.class.getSimpleName();
    private ActivitySubmitOrderBinding submitOrderBinding;
    private GoodsDetailBean goodsDetailBean;
    private double subscriptionRate;

    @Override
    protected void initView() {
        submitOrderBinding = DataBindingUtil.setContentView(SubmitOrderActivity.this, R.layout.activity_submit_order);

        setTitleCenter("提交订单");

        goodsDetailBean = (GoodsDetailBean) getIntent().getSerializableExtra("goods_detail");
        submitOrderBinding.btnSubmitOrder.setOnClickListener(this);

        subscriptionRate = Double.parseDouble(mSp.getString(ConfigConstants.SUBSCRIPTION_RATE, "")) / 100;
    }

    @Override
    protected void initData() {
        submitOrderBinding.sdvOrdersHead.setImageURI(Uri.parse(goodsDetailBean.getData().getProductInfo().getShowImg()));
        submitOrderBinding.tvGoodsName.setText(goodsDetailBean.getData().getProductInfo().getProductName());
        submitOrderBinding.tvGoodsScore.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(goodsDetailBean.getData().getProductInfo().getPrice()));
        submitOrderBinding.tvGoodsAmount.setText("数量：x1");
        submitOrderBinding.tvServiceTime.setText("有效期至：" + TimeUtils.millis2String(goodsDetailBean.getData().getProductInfo().getValidTime(),
                new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())));

        submitOrderBinding.amountView.setAmount("1");
        submitOrderBinding.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                submitOrderBinding.tvSubtotalScore.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format((goodsDetailBean.getData().getProductInfo().getPrice()) * amount));
                submitOrderBinding.tvPaymentScore.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format((goodsDetailBean.getData().getProductInfo().getPrice()) * amount * subscriptionRate));
                submitOrderBinding.tvFinalPayment.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(goodsDetailBean.getData().getProductInfo().getPrice() * amount * (1 - subscriptionRate)));
            }
        });
        //总计
        submitOrderBinding.tvSubtotalScore.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(goodsDetailBean.getData().getProductInfo().getPrice()));
        //预约金小计
        submitOrderBinding.tvPaymentScore.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(goodsDetailBean.getData().getProductInfo().getPrice() * subscriptionRate));
        //尾款小计
        submitOrderBinding.tvFinalPayment.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(goodsDetailBean.getData().getProductInfo().getPrice() * (1 - subscriptionRate)));
        //手机号码隐藏中间四位
        submitOrderBinding.tvPhone.setText(mSp.getString(Constants.PHONE, "")
                .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));

        submitOrderBinding.llOrderInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_order:
                //TODO 创建订单
                submitOrder();
                break;
            default:
                break;
        }

    }

    private void submitOrder() {
        submitOrderBinding.btnSubmitOrder.setClickable(false);
        startAnim(null);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("storeId", goodsDetailBean.getData().getProductInfo().getStoreId());
            jsonParams.put("storeName", goodsDetailBean.getData().getProductInfo().getStoreName());
            jsonParams.put("productId", goodsDetailBean.getData().getProductInfo().getId());
            jsonParams.put("mobile", mSp.getString(Constants.PHONE, ""));
            jsonParams.put("number", submitOrderBinding.amountView.getAmount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.CREATE_ORDER, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final SubmitOrderBean submitOrderBean = GsonUtil.GsonToBean(result, SubmitOrderBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(submitOrderBean.getCode())) {

                            EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.SUBMIT_ORDER));

                            //跳转积分支付页面，传值
                            Intent intent = new Intent(SubmitOrderActivity.this, PayScoreActivity.class);
                            //intent.putExtra("orderPrice", submitOrderBean.getData().getOrderPrice());
                            intent.putExtra("orderNum", submitOrderBean.getData().getOrderNum());
                            //intent.putExtra("payInBalance", submitOrderBean.getData().getPayInBalance());
                            //intent.putExtra("payInCash", submitOrderBean.getData().getPayInCash());
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(SubmitOrderActivity.this, submitOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                            submitOrderBinding.btnSubmitOrder.setClickable(true);
                        }
                    }
                });
            }
        });
    }
}
