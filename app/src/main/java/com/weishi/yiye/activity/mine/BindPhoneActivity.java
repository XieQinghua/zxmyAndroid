package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.common.util.IsIDCard;
import com.weishi.yiye.databinding.ActivityBindPhoneBinding;

import java.util.HashMap;
import java.util.Map;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/25
 * @Description：绑定手机号码
 * @Version:v1.0.0
 *****************************/
public class BindPhoneActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private ActivityBindPhoneBinding bindPhoneBinding;

    private String invitationCode = "", phoneNumber = "", verifyCode = "";
    /**
     * 倒计时提示
     */
    private TimeCount time;

    @Override
    protected void initView() {
        bindPhoneBinding = DataBindingUtil.setContentView(BindPhoneActivity.this, R.layout.activity_bind_phone);

        setTitleCenter("绑定手机号");

        bindPhoneBinding.ivEmpty1.setOnClickListener(this);
        bindPhoneBinding.ivEmpty2.setOnClickListener(this);
        bindPhoneBinding.ivEmpty3.setOnClickListener(this);
        bindPhoneBinding.tvGetCode.setOnClickListener(this);

        clearText(bindPhoneBinding.etInvitationCode, bindPhoneBinding.ivEmpty1);
        clearText(bindPhoneBinding.etPhoneNumber, bindPhoneBinding.ivEmpty2);
        clearText(bindPhoneBinding.etVerificationCode, bindPhoneBinding.ivEmpty3);

        bindPhoneBinding.btnBind.setOnClickListener(this);
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
                bindPhoneBinding.etInvitationCode.setText("");
                break;
            case R.id.iv_empty2:
                bindPhoneBinding.etPhoneNumber.setText("");
                break;
            case R.id.iv_empty3:
                bindPhoneBinding.etVerificationCode.setText("");
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_bind:
                bind();
                break;
            default:
                break;
        }
    }

    /**
     * 绑定操作
     */
    private void bind() {
        invitationCode = bindPhoneBinding.etInvitationCode.getText().toString().trim();
        //判断注册验证码是否正确
        phoneNumber = bindPhoneBinding.etPhoneNumber.getText().toString().trim();
        verifyCode = bindPhoneBinding.etVerificationCode.getText().toString().trim();

        if (invitationCode.equals("")) {
            Toast.makeText(BindPhoneActivity.this, "请输入邀请码", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(phoneNumber)) {
            Toast.makeText(BindPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (verifyCode.equals("")) {
            Toast.makeText(BindPhoneActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("Type", "1");
        map.put("recver", phoneNumber);
        map.put("code", verifyCode);
        //TODO 获取绑定验证码
//        showDialog(LOADING_DIALOG);
//        XUtils.ssoGet(Api.SSO_VERIFYCODE, map, new MyCallBack<String>() {
//            @Override
//            public void onSuccess(String result) {
//                super.onSuccess(result);
//                if (JsonValidator.validate(result)) {
//                    try {
//                        JSONObject json = new JSONObject(result);
//                        if (json.getInt("returnCode") == 0) {
//                            //{"returnCode":0,"message":"执行成功","space":1}
////                            Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegisterActivity.this, SecondRegisterActivity.class);
//                            intent.putExtra("mobileNumber", phoneNumber);
//                            intent.putExtra("verifyCode", verifyCode);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(RegisterActivity.this, R.string.unable_to_get_data, Toast.LENGTH_SHORT).show();
//                }
//                removeDialog();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//            }
//        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        time = new TimeCount(60000, 1000);
        phoneNumber = bindPhoneBinding.etPhoneNumber.getText().toString().trim();
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(phoneNumber)) {
            Toast.makeText(BindPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        time.start();
        //获取注册验证码
//        Map<String, String> map = new HashMap<>();
//        map.put("mobileNumber", phoneNumber);
//        showDialog(LOADING_DIALOG);
//        XUtils.ssoGet(Api.SSO_REGISTERSMS, map, new MyCallBack<String>() {
//            @Override
//            public void onSuccess(String result) {
//                super.onSuccess(result);
//                if (JsonValidator.validate(result)) {
//                    try {
//                        JSONObject json = new JSONObject(result);
//                        if (json.getInt("returnCode") == 0) {
//                            //获取验证码成功，倒计时开始
//                            time.start();
////                            Toast.makeText(ForgotPasswordActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(RegisterActivity.this, R.string.unable_to_get_data, Toast.LENGTH_SHORT).show();
//                }
//                removeDialog();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//            }
//        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            bindPhoneBinding.tvGetCode.setClickable(false);
            bindPhoneBinding.tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            bindPhoneBinding.tvGetCode.setText("获取验证码");
            bindPhoneBinding.tvGetCode.setClickable(true);
        }
    }
}
