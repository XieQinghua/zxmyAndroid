package com.weishi.yiye.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.LoginBean;
import com.weishi.yiye.bean.VerifyCodeBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.IsIDCard;
import com.weishi.yiye.common.util.ValidatorUtils;
import com.weishi.yiye.databinding.ActivityRegisterBinding;

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
 * @Description：注册页面
 * @Version:v1.0.0
 *****************************/
public class RegisterActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ActivityRegisterBinding registerBinding;

    private String invitationCode = "", phoneNumber = "", verifyCode = "";
    /**
     * 倒计时提示
     */
    private TimeCount time;

    @Override
    protected void initView() {
        registerBinding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        setTitleCenter("用户注册");

        registerBinding.ivEmpty1.setOnClickListener(this);
        registerBinding.ivEmpty2.setOnClickListener(this);
        registerBinding.ivEmpty3.setOnClickListener(this);
        registerBinding.tvGetCode.setOnClickListener(this);
        registerBinding.cbYiyeAgreement.setChecked(true);
        registerBinding.tvYiyeAgreement.setOnClickListener(this);

        clearText(registerBinding.etInvitationCode, registerBinding.ivEmpty1);
        clearText(registerBinding.etPhoneNumber, registerBinding.ivEmpty2);
        clearText(registerBinding.etVerificationCode, registerBinding.ivEmpty3);
        invitationCode = getIntent().getStringExtra(Constants.INVITE_CODE);
        if (ValidatorUtils.isNotEmptyString(invitationCode)) {
            registerBinding.etInvitationCode.setText(invitationCode);
        }

        //下划线并加清晰
        registerBinding.tvYiyeAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        registerBinding.tvYiyeAgreement.setOnClickListener(this);
        registerBinding.btnRegister.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left_title:
                finish();
                //overridePendingTransition(0, R.anim.activity_close);
                break;
            case R.id.iv_empty1:
                registerBinding.etInvitationCode.setText("");
                break;
            case R.id.iv_empty2:
                registerBinding.etPhoneNumber.setText("");
                break;
            case R.id.iv_empty3:
                registerBinding.etVerificationCode.setText("");
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.tv_yiye_agreement:
                //跳转服务协议页面
                Intent intent = new Intent(RegisterActivity.this, CommonWebViewActivity.class);
                intent.putExtra(Constants.INTENT_COMMON_ADV_URL, mSp.getString(ConfigConstants.USER_AGREEMENT, ""));
                intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, getString(R.string.license_agreement));
                startActivity(intent);
                break;
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }

    /**
     * 注册操作
     */
    private void register() {
        invitationCode = registerBinding.etInvitationCode.getText().toString().trim();
        //判断注册验证码是否正确
        phoneNumber = registerBinding.etPhoneNumber.getText().toString().trim();
        verifyCode = registerBinding.etVerificationCode.getText().toString().trim();

        if (invitationCode.equals("")) {
            invitationCode = "SYSTEM";
        }
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(phoneNumber)) {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (verifyCode.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        //登录操作
        startAnim("注册中···");

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("loginName", phoneNumber);
            jsonParams.put("loginPasswd", verifyCode);
            jsonParams.put("loginType", "LOGIN_BY_VERIFYCODE");
            jsonParams.put("inviteCode", invitationCode);
            jsonParams.put("operType", 1);
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

                //final LoginBean loginBean = new Gson().fromJson(result, LoginBean.class);
                final LoginBean loginBean = GsonUtil.GsonToBean(result, LoginBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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
                    //注册成功，直接登录
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        time = new TimeCount(60000, 1000);
        phoneNumber = registerBinding.etPhoneNumber.getText().toString().trim();
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(phoneNumber)) {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        time.start();

        //获取登录验证码
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("phone", phoneNumber);
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
                            Toast.makeText(RegisterActivity.this, verifyCodeBean.getMessage(), Toast.LENGTH_SHORT).show();
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
            registerBinding.tvGetCode.setClickable(false);
            registerBinding.tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            registerBinding.tvGetCode.setText("获取验证码");
            registerBinding.tvGetCode.setClickable(true);
        }
    }
}
