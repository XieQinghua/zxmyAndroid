package com.weishi.yiye.activity.order;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.databinding.ActivityPaySuccessBinding;

import java.text.DecimalFormat;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/6
 * @Description：支付成功
 * @Version:v1.0.0
 *****************************/
public class PaySuccessActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = PaySuccessActivity.class.getSimpleName();

    private ActivityPaySuccessBinding paySuccessBinding;

    private double orderPrice;

    @Override
    protected void initView() {
        paySuccessBinding = DataBindingUtil.setContentView(PaySuccessActivity.this, R.layout.activity_pay_success);
        setTitleCenter("成功支付");

        orderPrice = getIntent().getDoubleExtra("orderPrice", 0);

        paySuccessBinding.tvOrderPrice.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(orderPrice));

        paySuccessBinding.btnAccomplish.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accomplish:
                finish();
                break;
            default:
                break;
        }
    }
}
