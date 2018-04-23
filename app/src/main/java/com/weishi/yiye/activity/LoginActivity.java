package com.weishi.yiye.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.LoginBean;
import com.weishi.yiye.bean.VerifyCodeBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.IsIDCard;
import com.weishi.yiye.databinding.ActivityLoginBinding;
import com.weishi.yiye.wxapi.WXEntryActivity;

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
 * @Date：2018/1/19
 * @Description：登录页面
 * @Version:v1.0.0
 *****************************/
public class LoginActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding loginBinding;

    private String loginName = "", verifyCode = "";

    private TimeCount time;

    @Override
    protected void initView() {
        loginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        setTitleCenter("用户登录");

        loginBinding.ivEmpty1.setOnClickListener(this);
        loginBinding.ivEmpty2.setOnClickListener(this);

        //监听et_account_number的文字变化
        clearText(loginBinding.etAccountNumber, loginBinding.ivEmpty1);
        //监听et_password的文字变化
        clearText(loginBinding.etVerificationCode, loginBinding.ivEmpty2);

        if (!"".equals(mSp.getString(Constants.PHONE, ""))) {
            loginBinding.etAccountNumber.setText(mSp.getString(Constants.PHONE, ""));
            loginBinding.etAccountNumber.setSelection(loginBinding.etAccountNumber.getText().length());
        }

        loginBinding.tvGetCode.setOnClickListener(this);
        loginBinding.tvRegister.setOnClickListener(this);
        loginBinding.btnLogin.setOnClickListener(this);
        loginBinding.ivWechat.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void back(View view) {
        super.back(view);
        overridePendingTransition(0, R.anim.activity_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty1:
                loginBinding.etAccountNumber.setText("");
                break;
            case R.id.iv_empty2:
                loginBinding.etVerificationCode.setText("");
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.tv_register:
                //跳转注册页面
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.iv_wechat:
                WXEntryActivity.wxLogin(getApplicationContext(), YiyeApplication.mWxApi, false,
                        new WXEntryActivity.WXLoginStateListener() {
                            @Override
                            public void onSuccess() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(LoginActivity.this, "微信登录成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFail(String errorMsg) {
                                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        });
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
        loginName = loginBinding.etAccountNumber.getText().toString().trim();
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(loginName)) {
            Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        time.start();

        //获取登录验证码
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("phone", loginName);
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
                            Toast.makeText(LoginActivity.this, verifyCodeBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 登陆
     */
    private void login() {
        loginName = loginBinding.etAccountNumber.getText().toString().trim();
        verifyCode = loginBinding.etVerificationCode.getText().toString().trim();
        //点击登录按键隐藏键盘
        ((InputMethodManager) loginBinding.etVerificationCode.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(LoginActivity.this
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

        if (loginName.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (verifyCode.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        //登录操作
        startAnim("正在登陆中···");

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("loginName", loginName);
            jsonParams.put("loginPasswd", verifyCode);
            jsonParams.put("loginType", "LOGIN_BY_VERIFYCODE");
            jsonParams.put("inviteCode", "SYSTEM");
            jsonParams.put("operType", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.USER_LOGIN, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                String result = response.body().string();
                Log.e(TAG, result);

                final LoginBean loginBean = GsonUtil.GsonToBean(result, LoginBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(loginBean.getCode())) {
                            //保存数据
                            mSp.putBoolean(Constants.IS_LOGIN, true);
                            mSp.putString(Constants.PHONE, loginBean.getData().getUserInfo().getPhone());
                            mSp.putInt(Constants.SEX, loginBean.getData().getUserInfo().getSex());
                            mSp.putString(Constants.NICKNAME, loginBean.getData().getUserInfo().getNickname());
                            mSp.putInt(Constants.USER_TYPE, loginBean.getData().getUserInfo().getUserType());
                            mSp.putString(Constants.AVATAR, loginBean.getData().getUserInfo().getAvatar());
                            mSp.putInt(Constants.USER_ID, loginBean.getData().getUserInfo().getUserId());
                            mSp.putString(Constants.TOKEN, loginBean.getData().getToken());
                            mSp.putBoolean(Constants.IS_EXISTS, loginBean.getData().isIsExists());
                            mSp.putBoolean(Constants.IS_BIND, loginBean.getData().isIsBind());

                            mSp.putString(Constants.LOGIN_TYPE, "LOGIN_BY_VERIFYCODE");
                            //登录成功
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
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
            loginBinding.tvGetCode.setClickable(false);
            loginBinding.tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            loginBinding.tvGetCode.setText("获取验证码");
            loginBinding.tvGetCode.setClickable(true);
        }
    }
}
