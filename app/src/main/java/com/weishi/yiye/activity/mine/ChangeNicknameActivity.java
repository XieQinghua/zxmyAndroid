package com.weishi.yiye.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.eventbus.ChangeUserInfoEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityChangeNicknameBinding;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/26
 * @Description：修改昵称
 * @Version:v1.0.0
 *****************************/
public class ChangeNicknameActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ChangeNicknameActivity.class.getSimpleName();

    private ActivityChangeNicknameBinding changeNicknameBinding;

    private int RESULT_CODE = 0;

    @Override
    protected void initView() {
        changeNicknameBinding = DataBindingUtil.setContentView(ChangeNicknameActivity.this, R.layout.activity_change_nickname);

        setTitleCenter("修改昵称");

        changeNicknameBinding.etNickname.setText(getIntent().getStringExtra("nickname"));

        //获取编辑框焦点
        changeNicknameBinding.etNickname.setFocusable(true);
        changeNicknameBinding.etNickname.setFocusableInTouchMode(true);
        changeNicknameBinding.etNickname.requestFocus();
        //打开软键盘
        Executors.newScheduledThreadPool(1)
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        InputMethodManager inputManager =
                                (InputMethodManager) changeNicknameBinding.etNickname.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(changeNicknameBinding.etNickname, 0);
                    }
                }, 500, TimeUnit.MILLISECONDS);
        //监听EditText的文字变化
        //clearText(changeNicknameBinding.etNickname, changeNicknameBinding.ivEmpty);

        changeNicknameBinding.etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("NewApi")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeNicknameBinding.ivEmpty.setVisibility(View.VISIBLE);
                changeNicknameBinding.ivEmpty.setClickable(true);
                changeNicknameBinding.btnConfirm.setClickable(true);
                changeNicknameBinding.btnConfirm.setBackgroundResource(R.drawable.btn_red_selector);
                if (changeNicknameBinding.etNickname.getText().toString().trim().equals("")) {
                    changeNicknameBinding.ivEmpty.setVisibility(View.INVISIBLE);
                    changeNicknameBinding.btnConfirm.setClickable(false);
                    changeNicknameBinding.btnConfirm.setBackgroundResource(R.drawable.btn_red_pressed_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        changeNicknameBinding.etNickname.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    changeNicknameBinding.ivEmpty.setVisibility(View.INVISIBLE);
                }
            }
        });

        changeNicknameBinding.ivEmpty.setOnClickListener(this);
        changeNicknameBinding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_empty:
                changeNicknameBinding.etNickname.setText("");
                break;
            case R.id.btn_confirm:
                changeNickname();
                break;
            default:
                break;
        }
    }

    private void changeNickname() {
        startAnim(null);
        JSONObject jsonParams = new JSONObject();
        try {
            if (!(mSp.getInt(Constants.USER_ID, 0) == 0)) {
                jsonParams.put("id", mSp.getInt(Constants.USER_ID, 0));
                //jsonParams.put("uid", mSp.getInt(Constants.USER_ID, 0));
            }
            jsonParams.put("nickName", changeNicknameBinding.etNickname.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.MODIFY_USER_INFO + "?uid=" + mSp.getInt(Constants.USER_ID, 0), jsonParams, new Callback() {
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
                            Intent intent = new Intent();
                            intent.putExtra("nickname", changeNicknameBinding.etNickname.getText().toString().trim());
                            setResult(RESULT_CODE, intent);

                            mSp.putString(Constants.NICKNAME, changeNicknameBinding.etNickname.getText().toString().trim());

                            //发送EventBus修改个人信息事件
                            EventBus.getDefault().post(new ChangeUserInfoEvent(changeNicknameBinding.etNickname.getText().toString().trim(),
                                    ""));
                            finish();
                        } else {
                            Toast.makeText(ChangeNicknameActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
