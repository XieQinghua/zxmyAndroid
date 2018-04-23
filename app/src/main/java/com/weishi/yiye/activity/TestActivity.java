package com.weishi.yiye.activity;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.tencent.bugly.crashreport.CrashReport;
import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.JsonValidator;
import com.weishi.yiye.databinding.ActivityTestBinding;

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
 * @Date：2018/1/26
 * @Description：测试页面
 * @Version:v1.0.0
 *****************************/

public class TestActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = TestActivity.class.getSimpleName();
    private ActivityTestBinding testBinding;
    private boolean is = true;

    @Override
    protected void initView() {
        testBinding = DataBindingUtil.setContentView(TestActivity.this, R.layout.activity_test);
        setTitleCenter("测试接口");

        testBinding.btnTest.setOnClickListener(this);
        testBinding.btnCrash.setOnClickListener(this);
        testBinding.btnCreateOrder.setOnClickListener(this);
        testBinding.btnCreateOrderComment.setOnClickListener(this);
        testBinding.btnOrderList.setOnClickListener(this);
        testBinding.btnCancelOrder.setOnClickListener(this);
        testBinding.btnScoreBalance.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        JSONObject jsonParams;
        Map<String, Object> mapParams = null;
        switch (view.getId()) {
            case R.id.btn_test:
//                if (is) {
//                    //显示
//                    startAnim("加载中···");
//                    is = false;
//                } else {
//                    //隐藏
//                    stopAnim();
//                    is = true;
//                }
                //startActivity(new Intent(TestActivity.this,ShopsDetailActivity.class));
                break;
            case R.id.btn_crash:
                CrashReport.testJavaCrash();
                break;
            case R.id.btn_create_order:
                startAnim(null);

                jsonParams = new JSONObject();
                try {
                    jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
                    jsonParams.put("orderPrice", "100");
                    jsonParams.put("mobile", "15200917596");
                    jsonParams.put("productId", 1);
                    jsonParams.put("productName", "德鲁大叔手抓饼");
                    jsonParams.put("storeId", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.doPost(Api.CREATE_ORDER, jsonParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        stopAnim();

                        final String result = response.body().string();
                        Log.e(TAG, result);

                        //验证合法格式json
                        if (JsonValidator.validate(result)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testBinding.tvJson.setText(result);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.btn_create_order_comment:
                startAnim(null);

                jsonParams = new JSONObject();
                try {
                    jsonParams.put("productId", 2);
                    jsonParams.put("storeId", 2);
                    jsonParams.put("userId", 2);
                    jsonParams.put("orderId", 16);
                    jsonParams.put("content", "好评");
                    jsonParams.put("commentImg", "2132131231321");
                    jsonParams.put("commentLv", 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                mapParams = new HashMap<>();
//                mapParams.put("productId", 2);
//                mapParams.put("storeId", 2);
//                mapParams.put("userId", 2);
//                mapParams.put("orderId", 16);
//                mapParams.put("content", "好评");
//                mapParams.put("commentImg", "2132131231321");
//                mapParams.put("commentLv", 2);

                HttpUtils.doPost(Api.CREATE_ORDER_COMMENT, jsonParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        stopAnim();

                        final String result = response.body().string();
                        Log.e(TAG, result);

                        //验证合法格式json
                        if (JsonValidator.validate(result)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testBinding.tvJson.setText(result);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.btn_order_list:
                startAnim(null);

                mapParams = new HashMap<>();
                //mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
                mapParams.put("userId", 116);
                mapParams.put("tagType", 2);
                mapParams.put("pageNum", 1);
                mapParams.put("pageSize", 10);

                HttpUtils.doGet(Api.GET_ORDER_LIST, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        stopAnim();

                        final String result = response.body().string();
                        Log.e(TAG, result);

                        //验证合法格式json
                        if (JsonValidator.validate(result)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testBinding.tvJson.setText(result);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.btn_cancel_order:
                startAnim(null);

//                jsonParams = new JSONObject();
//                try {
//                    jsonParams.put("orderId", 1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                mapParams = new HashMap<>();
                mapParams.put("orderId", 1);

                HttpUtils.doGet(Api.CANCEL_ORDER, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        stopAnim();

                        final String result = response.body().string();
                        Log.e(TAG, result);

                        //验证合法格式json
                        if (JsonValidator.validate(result)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testBinding.tvJson.setText(result);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.btn_score_balance:
                startAnim(null);

                mapParams = new HashMap<>();
                mapParams.put("uid", mSp.getInt(Constants.USER_ID, 0));

                HttpUtils.doGet(Api.GET_SCORE_BALANCE, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        stopAnim();

                        final String result = response.body().string();
                        Log.e(TAG, result);

                        //验证合法格式json
                        if (JsonValidator.validate(result)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testBinding.tvJson.setText(result);
                                }
                            });
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
