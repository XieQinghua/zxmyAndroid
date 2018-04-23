package com.weishi.yiye.activity.order;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.FragmentTabAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.databinding.ActivityOrderAllBinding;
import com.weishi.yiye.fragment.order.SecondOrderFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：全部订单
 * @Version:v1.0.0
 *****************************/
public class AllOrderActivity extends BaseSwipeBackActivity {

    private List<Fragment> fragments = new ArrayList<>();
    private ActivityOrderAllBinding orderAllBinding;
    public static FragmentTabAdapter tabAdapter;

    @Override
    protected void initView() {
        orderAllBinding = DataBindingUtil.setContentView(AllOrderActivity.this, R.layout.activity_order_all);

        setTitleCenter("全部订单");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        fragments.add(new SecondOrderFragment(Constants.ORDER_TYPE_WHOLE));
        fragments.add(new SecondOrderFragment(Constants.ORDER_TYPE_TAKEPAYMENT));
        fragments.add(new SecondOrderFragment(Constants.ORDER_TYPE_BANDUSE));
        fragments.add(new SecondOrderFragment(Constants.ORDER_TYPE_EVALUATION));
        fragments.add(new SecondOrderFragment(Constants.ORDER_TYPE_REFUND));

        tabAdapter = new FragmentTabAdapter(AllOrderActivity.this, getSupportFragmentManager(),
                fragments, R.id.content, orderAllBinding.rg);

        if (getIntent() != null && getIntent().getStringExtra("type") != null) {
            String type = getIntent().getStringExtra("type");
            switch (type) {
                case Constants.ORDER_TYPE_WHOLE:
                    orderAllBinding.rbWhole.setChecked(true);
                    tabAdapter.showTab(0);
                    break;
                case Constants.ORDER_TYPE_TAKEPAYMENT:
                    orderAllBinding.rbTakepayment.setChecked(true);
                    tabAdapter.showTab(1);
                    break;
                case Constants.ORDER_TYPE_BANDUSE:
                    orderAllBinding.rbBanduse.setChecked(true);
                    tabAdapter.showTab(2);
                    break;
                case Constants.ORDER_TYPE_EVALUATION:
                    orderAllBinding.rbEvaluation.setChecked(true);
                    tabAdapter.showTab(3);
                    break;
                case Constants.ORDER_TYPE_REFUND:
                    orderAllBinding.rbRefund.setChecked(true);
                    tabAdapter.showTab(4);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadingState(LoadingStateEvent loadingStateEvent) {
        switch (loadingStateEvent.getType()) {
            case LoadingStateEvent.START_ANIM:
                startAnim(loadingStateEvent.getLoadingText());
                break;
            case LoadingStateEvent.STOP_ANIM:
                stopAnim();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
