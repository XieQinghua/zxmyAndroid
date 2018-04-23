package com.weishi.yiye.fragment.mine;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.CollectShopsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.CollectShopsBean;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * @Date：2018/2/8
 * @Description：收藏店铺
 * @Version:v1.0.0
 *****************************/

public class CollectShopsFragment extends BaseFragment implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = CollectShopsFragment.class.getSimpleName();

    protected RefreshLoadMoreLayout mRefreshloadmore;
    protected ListView mListview;
    protected TextView orderEmpty;

    protected CollectShopsAdapter mAdapter;
    protected Handler mHandler = new Handler();
    private List<CollectShopsBean.DataBean.ListBean> datas;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_shops;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mListview = findView(R.id.lv_collect_shops);
        orderEmpty = findView(R.id.tv_no_shops);

        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        datas = new ArrayList<>();
        mAdapter = new CollectShopsAdapter(getActivity(), R.layout.item_collect_shops);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        getData(pageNum);
    }

    private void getData(final int pageNum) {
        EventBus.getDefault().post(new LoadingStateEvent(LoadingStateEvent.START_ANIM));

        Map<String, Object> mapParams = new HashMap<>();

        mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);
        if (YiyeApplication.locationListBean != null) {
            mapParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
            mapParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
        } else {
            mapParams.put("userLat", 0);
            mapParams.put("userLng", 0);
        }

        HttpUtils.doGet(Api.GET_STORE_COLLECT_INFOS, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                EventBus.getDefault().post(new LoadingStateEvent(LoadingStateEvent.STOP_ANIM));

                final String result = response.body().string();
                Log.e(TAG, result);

                if (getActivity() == null) {
                    return;
                }
                final CollectShopsBean collectShopsBean = GsonUtil.GsonToBean(result, CollectShopsBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(collectShopsBean.getCode()) && collectShopsBean.getData() != null) {
                            if (collectShopsBean != null &&
                                    collectShopsBean.getData() != null &&
                                    collectShopsBean.getData().getList() != null &&
                                    collectShopsBean.getData().getList().size() != 0) {

                                if (pageNum == 1) {
                                    datas = collectShopsBean.getData().getList();
                                } else {
                                    datas.addAll(collectShopsBean.getData().getList());
                                }

                                hasNextPage = collectShopsBean.getData().isHasNextPage();

                                mAdapter.setData(datas);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mRefreshloadmore.setVisibility(View.GONE);
                                orderEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (datas != null) {
                    datas.clear();
                    mAdapter.notifyDataSetChanged();
                    mRefreshloadmore.stopRefresh();
                } else {
                    datas = new ArrayList<>();
                }
                getData(1);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            getData(pageNum);
        } else {
            Toast.makeText(getActivity(), getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mRefreshloadmore.stopLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationState(LocationStateEvent locationStateEvent) {
        switch (locationStateEvent.getState()) {
            case LocationStateEvent.SUCCESS:
                getData(pageNum);
                break;
            default:
                break;
        }
    }
}
