package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.AgentDataAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.AgentDataBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityAgentDataBinding;

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
 * @Date：2018/3/30
 * @Description：用户代理数据
 * @Version:v1.0.0
 *****************************/
public class AgentDataActivity extends BaseSwipeBackActivity {
    private static final String TAG = AgentDataActivity.class.getSimpleName();
    private ActivityAgentDataBinding agentDataBinding;

    protected Handler mHandler = new Handler();
    private AgentDataAdapter agentDataAdapter;
    private List<AgentDataBean.DataBean.UserAgentDataItemsBean> datas = new ArrayList<>();
    private int userLeveType;

    @Override
    protected void initView() {
        agentDataBinding = DataBindingUtil.setContentView(AgentDataActivity.this, R.layout.activity_agent_data);

        userLeveType = getIntent().getIntExtra("userLeveType", 0);
        if (userLeveType == 1) {
            setTitleCenter("一级会员");
        } else {
            setTitleCenter("二级会员");
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        agentDataAdapter = new AgentDataAdapter(AgentDataActivity.this, R.layout.item_agent_data);
        agentDataBinding.lvAgentData.setAdapter(agentDataAdapter);
    }

    @Override
    protected void initData() {
        getMyPartner();
    }

    private void getMyPartner() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("uid", mSp.getInt(Constants.USER_ID, 0));
        mapParams.put("userLeveType", userLeveType);
        mapParams.put("currentPage", 1);
        mapParams.put("pageSize", 10);

        HttpUtils.doGet(Api.GET_AGENTDATA, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                //验证合法格式json
                final AgentDataBean agentDataBean = GsonUtil.GsonToBean(result, AgentDataBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(agentDataBean.getCode()) &&
                                agentDataBean.getData().getUserAgentDataItems() != null &&
                                agentDataBean.getData().getUserAgentDataItems().size() != 0) {
                            datas = agentDataBean.getData().getUserAgentDataItems();
                            agentDataAdapter.setData(datas);
                            agentDataAdapter.notifyDataSetChanged();
                        } else {
                            agentDataBinding.lvAgentData.setVisibility(View.GONE);
                            agentDataBinding.tvNoAgentData.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}
