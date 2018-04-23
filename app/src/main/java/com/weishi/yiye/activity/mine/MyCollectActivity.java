package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.FragmentTabAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.databinding.ActivityMyCollectBinding;
import com.weishi.yiye.fragment.mine.CollectGoodsFragment;
import com.weishi.yiye.fragment.mine.CollectShopsFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/2
 * @Description：我的收藏
 * @Version:v1.0.0
 *****************************/

public class MyCollectActivity extends BaseSwipeBackActivity {
    private ActivityMyCollectBinding myCollectBinding;

    private ArrayList<Fragment> fragments;

    @Override
    protected void initView() {
        myCollectBinding = DataBindingUtil.setContentView(MyCollectActivity.this, R.layout.activity_my_collect);

        setTitleCenter("我的收藏");

        fragments = new ArrayList<Fragment>();
        fragments.add(new CollectGoodsFragment());
        fragments.add(new CollectShopsFragment());

        new FragmentTabAdapter(MyCollectActivity.this, getSupportFragmentManager(),
                fragments, R.id.content, myCollectBinding.rg);
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
