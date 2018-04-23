package com.weishi.yiye.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.CleanCacheUtil;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivitySettingBinding;
import com.weishi.yiye.view.CustomDialog;

import java.io.IOException;
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
 * @Description：系统设置
 * @Version:v1.0.0
 *****************************/

public class SettingActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = SettingActivity.class.getSimpleName();
    private ActivitySettingBinding settingBinding;

    @Override
    protected void initView() {
        settingBinding = DataBindingUtil.setContentView(SettingActivity.this, R.layout.activity_setting);
        setTitleCenter("系统设置");

        try {
            settingBinding.tvClearCache.setText(CleanCacheUtil.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        settingBinding.tvSafety.setOnClickListener(this);
        settingBinding.tvOpinion.setOnClickListener(this);
        settingBinding.tvClearCache.setOnClickListener(this);
        settingBinding.tvAboutUs.setOnClickListener(this);
        settingBinding.btnLogout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        CustomDialog.Builder builder;
        switch (view.getId()) {
            case R.id.tv_safety:
                startActivity(new Intent(SettingActivity.this, SafetyActivity.class));
                break;
            case R.id.tv_opinion:
                startActivity(new Intent(SettingActivity.this, OpinionActivity.class));
                break;
            case R.id.tv_clear_cache:
                builder = new CustomDialog.Builder(SettingActivity.this);
                builder.setMessage("确定清除本地缓存？");
                builder.setTitleVisibility(View.GONE);
                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CleanCacheUtil.clearAllCache(getApplicationContext());
                        try {
                            settingBinding.tvClearCache.setText(CleanCacheUtil.getTotalCacheSize(SettingActivity.this));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SettingActivity.this, "清除缓存成功", Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
                break;
            case R.id.btn_logout:
                builder = new CustomDialog.Builder(SettingActivity.this);
                builder.setMessage("确定退出登录？");
                builder.setTitleVisibility(View.GONE);
                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
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
                break;
            default:
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        HttpUtils.doGet(Api.USER_LOGIN_OUT, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            mSp.putBoolean(Constants.IS_LOGIN, false);
                            mSp.putString(Constants.PHONE, StringConstants.STR_EMPTY);
                            //startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}
