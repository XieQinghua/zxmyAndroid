package com.weishi.yiye.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.tools.LightStatusBarUtils;
import com.wang.avi.AVLoadingIndicatorView;
import com.weishi.yiye.R;
import com.weishi.yiye.bean.eventbus.ExceptionEvent;
import com.weishi.yiye.bean.eventbus.NetWorkEvent;
import com.weishi.yiye.common.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/9
 * @Description：Activity基类
 * @Version:v1.0.0
 *****************************/
public abstract class BaseActivity extends FragmentActivity {
    /**
     * 用户保存所有用户操作过的activity
     */
    private static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    public SPUtils mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }

        LightStatusBarUtils.setLightStatusBar(this, true);

        mSp = SPUtils.getInstance(this);

        initView();

        EventBus.getDefault().register(this);

        initData();
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        mActivities.remove(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkEvent(NetWorkEvent event) {
        //Toast.makeText(getActivity(), event.message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExceptionEvent(ExceptionEvent event) {
        Toast.makeText(this, event.getException(), Toast.LENGTH_LONG).show();
        return;
    }

    public void setTitleCenter(String titleCenter) {
        ((TextView) findViewById(R.id.tv_title_center)).setText(titleCenter);
    }

    /**
     * 开启Loading(备注：调用此方法xml必须 <include layout="@layout/activity_layout_foot" />)
     */
    protected void startAnim(String loadingText) {
        findViewById(R.id.rl_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.avi_loading).setVisibility(View.VISIBLE);
        if (loadingText != null) {
            ((TextView) findViewById(R.id.tv_recover_loading)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tv_recover_loading)).setText(loadingText);
        }
        ((AVLoadingIndicatorView) findViewById(R.id.avi_loading)).show();
    }

    /**
     * 关闭Loading
     */
    protected void stopAnim() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                (findViewById(R.id.rl_loading)).setVisibility(View.GONE);
                findViewById(R.id.avi_loading).setVisibility(View.GONE);
                findViewById(R.id.tv_recover_loading).setVisibility(View.GONE);
                ((AVLoadingIndicatorView) findViewById(R.id.avi_loading)).hide();
            }
        });
    }

    /**
     * Activity的返回操作调用此方法
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 关闭所有Activity，除了参数传递的Activity
     */
    public static void finishAll(BaseActivity except) {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            if (activity != except) {
                activity.finish();
            }
        }
    }

    /**
     * 封装监听EditText的文字变化及清空键
     *
     * @param editText
     * @param imageView
     */
    public void clearText(final EditText editText, final ImageView imageView) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setClickable(true);
                if (editText.getText().toString().trim().equals("")) {
                    imageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }
}
