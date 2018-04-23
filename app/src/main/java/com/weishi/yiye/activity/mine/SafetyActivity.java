package com.weishi.yiye.activity.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.BindRelationBean;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.databinding.ActivitySafetyBinding;
import com.weishi.yiye.wxapi.WXEntryActivity;

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
 * @Date：2018/1/19
 * @Description：邀请好友
 * @Version:v1.0.0
 *****************************/

public class SafetyActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = SafetyActivity.class.getSimpleName();
    private ActivitySafetyBinding safetyBinding;

    private static final int SETTING_PHONE = 0;
    private boolean isBindWeChat;

    @Override
    protected void initView() {
        safetyBinding = DataBindingUtil.setContentView(SafetyActivity.this, R.layout.activity_safety);
        setTitleCenter("账号与安全");

        safetyBinding.tvPhone.setText(mSp.getString(Constants.PHONE, ""));

        if (mSp.getBoolean(Constants.IS_EXISTS, false)) {
            safetyBinding.btnChangePayPassword.setText(R.string.modification);
        } else {
            safetyBinding.btnChangePayPassword.setText(R.string.setting);
        }
        safetyBinding.btnChangePhone.setOnClickListener(this);
        safetyBinding.btnWechatUnbundling.setOnClickListener(this);
        safetyBinding.btnChangePayPassword.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //获取绑定关系
        startAnim(null);
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        HttpUtils.doGet(Api.IS_BIND_RELATION, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();

                String result = response.body().string();
                Log.e(TAG, result);

                final BindRelationBean bindRelationBean = GsonUtil.GsonToBean(result, BindRelationBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(bindRelationBean.getCode())) {
                            if (bindRelationBean.getData() != null) {
                                isBindWeChat = (bindRelationBean.getData().getIsBindThird() == 1);
                                if (isBindWeChat) {
                                    safetyBinding.btnWechatUnbundling.setText(R.string.unbundling);
                                } else {
                                    safetyBinding.btnWechatUnbundling.setText(R.string.bundling);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_phone:
                startActivityForResult(new Intent(SafetyActivity.this, SettingPhoneActivity.class), SETTING_PHONE);
                break;
            case R.id.btn_wechat_unbundling:
                if (!isBindWeChat) {
                    WXEntryActivity.wxLogin(getApplicationContext(), YiyeApplication.mWxApi, true,
                            new WXEntryActivity.WXLoginStateListener() {
                                @Override
                                public void onSuccess() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SafetyActivity.this, "绑定微信成功", Toast.LENGTH_SHORT).show();
                                            safetyBinding.btnWechatUnbundling.setText(R.string.unbundling);
                                            isBindWeChat = true;
                                        }
                                    });
                                }

                                @Override
                                public void onFail(String errorMsg) {
                                    Toast.makeText(SafetyActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    //解绑微信
                    weiXinBindRemove();
                }
                break;
            case R.id.btn_change_pay_password:
                startActivity(new Intent(SafetyActivity.this, SetPaymentActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SETTING_PHONE:
                safetyBinding.tvPhone.setText(mSp.getString(Constants.PHONE, ""));
                break;
            default:
                break;
        }
    }


    private void weiXinBindRemove() {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        HttpUtils.doGet(Api.WEIXIN_BIND_REMOVE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();

                String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            Toast.makeText(SafetyActivity.this, "解除微信绑定", Toast.LENGTH_SHORT).show();
                            safetyBinding.btnWechatUnbundling.setText(R.string.bundling);
                            isBindWeChat = false;
                        } else {
                            Toast.makeText(SafetyActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
