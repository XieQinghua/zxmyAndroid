package com.weishi.yiye.activity.mine;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
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
import com.weishi.yiye.databinding.ActivitySetPaymentBinding;

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
 * @Date：2018/4/4
 * @Description：设置支付密码
 * @Version:v1.0.0
 *****************************/
public class SetPaymentActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = SetPaymentActivity.class.getSimpleName();
    private ActivitySetPaymentBinding setPaymentBinding;

    private String phoneNumber = "", verifyCode = "", payPassWd = "";

    private TimeCount time;

    @Override
    protected void initView() {
        setPaymentBinding = DataBindingUtil.setContentView(SetPaymentActivity.this, R.layout.activity_set_payment);
        if (mSp.getBoolean(Constants.IS_EXISTS, false)) {
            setTitleCenter("修改支付密码");
        } else {
            setTitleCenter("设置支付密码");
        }

        setPaymentBinding.ivEmpty1.setOnClickListener(this);
        setPaymentBinding.ivEmpty2.setOnClickListener(this);
        setPaymentBinding.ivEmpty3.setOnClickListener(this);

        clearText(setPaymentBinding.etAccountNumber, setPaymentBinding.ivEmpty1);
        clearText(setPaymentBinding.etVerificationCode, setPaymentBinding.ivEmpty2);
        clearText(setPaymentBinding.etPaymentCode, setPaymentBinding.ivEmpty3);

        if (!"".equals(mSp.getString(Constants.PHONE, ""))) {
            setPaymentBinding.etAccountNumber.setText(mSp.getString(Constants.PHONE, ""));
            setPaymentBinding.etAccountNumber.setSelection(setPaymentBinding.etAccountNumber.getText().length());
        }

        setPaymentBinding.cbEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //显示密文数字
                    setPaymentBinding.etPaymentCode.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                } else {
                    //显示明文数字
                    setPaymentBinding.etPaymentCode.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
        });

        setPaymentBinding.tvGetCode.setOnClickListener(this);
        setPaymentBinding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty1:
                setPaymentBinding.etAccountNumber.setText("");
                break;
            case R.id.iv_empty2:
                setPaymentBinding.etVerificationCode.setText("");
                break;
            case R.id.iv_empty3:
                setPaymentBinding.etPaymentCode.setText("");
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_confirm:
                confirmChange();
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
        phoneNumber = setPaymentBinding.etAccountNumber.getText().toString().trim();
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(phoneNumber)) {
            Toast.makeText(SetPaymentActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        time.start();

        //获取验证码
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
                            Toast.makeText(SetPaymentActivity.this, verifyCodeBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 确认修改支付密码
     */
    private void confirmChange() {
        phoneNumber = setPaymentBinding.etAccountNumber.getText().toString().trim();
        verifyCode = setPaymentBinding.etVerificationCode.getText().toString().trim();
        payPassWd = setPaymentBinding.etPaymentCode.getText().toString().trim();
        //点击登录按键隐藏键盘
        ((InputMethodManager) setPaymentBinding.etVerificationCode.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SetPaymentActivity.this
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

        if (phoneNumber.equals("")) {
            Toast.makeText(SetPaymentActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (verifyCode.equals("")) {
            Toast.makeText(SetPaymentActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        //校验六位数字
        if (payPassWd.equals("")) {
            Toast.makeText(SetPaymentActivity.this, "请输入六位数字支付密码", Toast.LENGTH_SHORT).show();
            return;
        }

        startAnim(null);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("phone", phoneNumber);
            jsonParams.put("verifyCode", verifyCode);
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            //MD5加密
            //jsonParams.put("passWord", MD5Util.getMessageDigest(payPassWd.getBytes()));
            jsonParams.put("passWord", payPassWd);
            jsonParams.put("loginType", mSp.getString(Constants.LOGIN_TYPE, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.MODIFY_PAY_PWD, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
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
                            mSp.putBoolean(Constants.IS_EXISTS, true);
                            Log.e(TAG, "支付密码设置成功");
                            Toast.makeText(SetPaymentActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();

                            finish();
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
            setPaymentBinding.tvGetCode.setClickable(false);
            setPaymentBinding.tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            setPaymentBinding.tvGetCode.setText("获取验证码");
            setPaymentBinding.tvGetCode.setClickable(true);
        }
    }
}
