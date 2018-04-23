package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.SystemMsgAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.MsgBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivitySystemMsgBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：系统消息
 * @Version:v1.0.0
 *****************************/

public class SystemMsgActivity extends BaseSwipeBackActivity implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = SystemMsgActivity.class.getSimpleName();
    private ActivitySystemMsgBinding systemMsgBinding;

    protected Handler mHandler = new Handler();
    private SystemMsgAdapter systemMsgAdapter;
    private ArrayList<MsgBean.DataBean.ListBean> msgDatas;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected void initView() {
        systemMsgBinding = DataBindingUtil.setContentView(SystemMsgActivity.this, R.layout.activity_system_msg);

        setTitleCenter("系统消息");

        systemMsgBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        //屏蔽下拉刷新
        systemMsgBinding.refreshloadmore.setCanRefresh(false);

        systemMsgAdapter = new SystemMsgAdapter(SystemMsgActivity.this, R.layout.item_system_msg);
        systemMsgBinding.lvSystemMsg.setAdapter(systemMsgAdapter);
        msgDatas = new ArrayList<>();
    }

    @Override
    protected void initData() {
        getData(pageNum);
    }

    private void getData(final int pageNum) {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", 1);
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);
        HttpUtils.doGet(Api.GET_SYSTEM_MESSAGE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final MsgBean msgBean = GsonUtil.GsonToBean(result, MsgBean.class);

                if (msgBean != null &&
                        msgBean.getData() != null &&
                        msgBean.getData().getList() != null &&
                        msgBean.getData().getList().size() != 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pageNum == 1) {
                                msgDatas = (ArrayList<MsgBean.DataBean.ListBean>) msgBean.getData().getList();
                            } else {
                                msgDatas.addAll(msgBean.getData().getList());
                            }

                            hasNextPage = msgBean.getData().isHasNextPage();

                            systemMsgAdapter.setData(msgDatas);
                            systemMsgAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            getData(pageNum);
        } else {
            Toast.makeText(SystemMsgActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        systemMsgBinding.refreshloadmore.stopLoadMore();
    }
}
