package com.weishi.yiye.activity;

import android.content.Intent;
import android.os.Handler;

import com.weishi.yiye.base.BaseActivity;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/6
 * @Description：启动页
 * @Version:v1.0.0
 *****************************/
public class SplashActivity extends BaseActivity {

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void initData() {

    }
}
