package com.weishi.yiye.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.VerifyCodeBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.IsIDCard;
import com.weishi.yiye.databinding.ActivitySettingPhoneBinding;

import org.json.JSONException;
import org.json.JSONObject;

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
 * @Date：2018/2/7
 * @Description：设置手机号码
 * @Version:v1.0.0
 *****************************/
public class SettingPhoneActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = SettingPhoneActivity.class.getSimpleName();
    private ActivitySettingPhoneBinding settingPhoneBinding;

    private String phone = "", verifyCode = "";

    private TimeCount time;

    private int RESULT_CODE = 0;

    @Override
    protected void initView() {
        settingPhoneBinding = DataBindingUtil.setContentView(SettingPhoneActivity.this, R.layout.activity_setting_phone);

        setTitleCenter("设置手机号");

        settingPhoneBinding.ivEmpty1.setOnClickListener(this);
        settingPhoneBinding.ivEmpty2.setOnClickListener(this);

        //监听et_account_number的文字变化
        clearText(settingPhoneBinding.etAccountNumber, settingPhoneBinding.ivEmpty1);
        //监听et_password的文字变化
        clearText(settingPhoneBinding.etVerificationCode, settingPhoneBinding.ivEmpty2);

        if (!"".equals(mSp.getString(Constants.PHONE, ""))) {
            settingPhoneBinding.etAccountNumber.setText(mSp.getString(Constants.PHONE, ""));
            settingPhoneBinding.etAccountNumber.setSelection(settingPhoneBinding.etAccountNumber.getText().length());
        }

        settingPhoneBinding.tvGetCode.setOnClickListener(this);
        settingPhoneBinding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty1:
                settingPhoneBinding.etAccountNumber.setText("");
                break;
            case R.id.iv_empty2:
                settingPhoneBinding.etVerificationCode.setText("");
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_confirm:
                confirmSetting();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        time = new TimeCount(60000, 1000);
        phone = settingPhoneBinding.etAccountNumber.getText().toString().trim();
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(phone)) {
            Toast.makeText(SettingPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        time.start();

        //获取登录验证码
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("phone", phone);
        HttpUtils.doGet(Api.GET_VERIFY_CODE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final VerifyCodeBean verifyCodeBean = GsonUtil.GsonToBean(result, VerifyCodeBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(verifyCodeBean.getCode())) {
                            //获取验证码成功，开启倒计时
                            //time.start();
                        } else {
                            Toast.makeText(SettingPhoneActivity.this, verifyCodeBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    /**
     * 确认设置
     */
    private void confirmSetting() {
        phone = settingPhoneBinding.etAccountNumber.getText().toString().trim();
        verifyCode = settingPhoneBinding.etVerificationCode.getText().toString().trim();
        //点击登录按键隐藏键盘
        ((InputMethodManager) settingPhoneBinding.etVerificationCode.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SettingPhoneActivity.this
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

        if (phone.equals("")) {
            Toast.makeText(SettingPhoneActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (verifyCode.equals("")) {
            Toast.makeText(SettingPhoneActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("phone", phone);
            jsonParams.put("verifyCode", verifyCode);
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.REFRESH_PHONE, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Log.e(TAG, result);


                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            mSp.putString(Constants.PHONE, phone);
                            Intent intent = new Intent();
                            setResult(RESULT_CODE, intent);
                            finish();
                        } else {
                            Toast.makeText(SettingPhoneActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            settingPhoneBinding.tvGetCode.setClickable(false);
            settingPhoneBinding.tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            settingPhoneBinding.tvGetCode.setText("获取验证码");
            settingPhoneBinding.tvGetCode.setClickable(true);
        }
    }
}
