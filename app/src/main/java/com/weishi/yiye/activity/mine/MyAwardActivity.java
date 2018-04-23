package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.MyAwardAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.MyAwardBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityMyAwardBinding;

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
 * @Author：xieqinghua
 * @Date：2018/1/25
 * @Description：我的奖励
 * @Version:v1.0.0
 *****************************/

public class MyAwardActivity extends BaseSwipeBackActivity {
    private static final String TAG = MyAwardActivity.class.getSimpleName();
    private ActivityMyAwardBinding myAwardBinding;

    protected Handler mHandler = new Handler();
    private MyAwardAdapter myAwardAdapter;
    private List<MyAwardBean.DataBean> myAwardDatas = new ArrayList<>();

    @Override
    protected void initView() {
        myAwardBinding = DataBindingUtil.setContentView(MyAwardActivity.this, R.layout.activity_my_award);

        setTitleCenter("我的奖励");

        myAwardAdapter = new MyAwardAdapter(MyAwardActivity.this, R.layout.item_my_award);
        myAwardBinding.lvMyAward.setAdapter(myAwardAdapter);
    }

    @Override
    protected void initData() {
        getMyAward();
    }

    private void getMyAward() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        mapParams.put("pageNo", 1);
        mapParams.put("pageSize", 10);

        HttpUtils.doGet(Api.USER_MY_REWARD_COUNT, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final MyAwardBean myAwardBean = GsonUtil.GsonToBean(result, MyAwardBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != myAwardBean && null != myAwardBean.getData() && myAwardBean.getData().size() != 0) {
                            myAwardDatas = myAwardBean.getData();
                            myAwardAdapter.setData(myAwardDatas);
                            myAwardAdapter.notifyDataSetChanged();
                        } else {
                            myAwardBinding.lvMyAward.setVisibility(View.GONE);
                            myAwardBinding.tvNoAward.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}
