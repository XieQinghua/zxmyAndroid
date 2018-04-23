package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.MyPartnerAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.MyPartnerBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityMyPartnerBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/25
 * @Description：我的伙伴
 * @Version:v1.0.0
 *****************************/
public class MyPartnerActivity extends BaseSwipeBackActivity {
    private static final String TAG = MyPartnerActivity.class.getSimpleName();
    private ActivityMyPartnerBinding myPartnerBinding;

    protected Handler mHandler = new Handler();
    private MyPartnerAdapter myPartnerAdapter;
    private List<MyPartnerBean.DataBean> myAwardDatas = new ArrayList<>();

    @Override
    protected void initView() {
        myPartnerBinding = DataBindingUtil.setContentView(MyPartnerActivity.this, R.layout.activity_my_partner);
        setTitleCenter("我的伙伴");

        myPartnerAdapter = new MyPartnerAdapter(MyPartnerActivity.this, R.layout.item_my_partner);
        myPartnerBinding.lvMyPartner.setAdapter(myPartnerAdapter);
    }

    @Override
    protected void initData() {
        getMyPartner();
    }

    private void getMyPartner() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        mapParams.put("pageNo", 1);
        mapParams.put("pageSize", 10);

        HttpUtils.doGet(Api.USER_MY_PARTNER_COUNT, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final MyPartnerBean myPartnerBean = GsonUtil.GsonToBean(result, MyPartnerBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != myPartnerBean && null != myPartnerBean.getData() && myPartnerBean.getData().size() != 0) {
                            myAwardDatas = myPartnerBean.getData();
                            myPartnerAdapter.setData(myAwardDatas);
                            myPartnerAdapter.notifyDataSetChanged();
                        } else {
                            myPartnerBinding.lvMyPartner.setVisibility(View.GONE);
                            myPartnerBinding.tvNoPartner.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}
