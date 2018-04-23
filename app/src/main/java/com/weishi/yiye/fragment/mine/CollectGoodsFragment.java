package com.weishi.yiye.fragment.mine;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.GoodsDetailActivity;
import com.weishi.yiye.adapter.CollectGoodsAdapter;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.CollectGoodsBean;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.GoodsConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

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
 * @Description：收藏商品
 * @Version:v1.0.0
 *****************************/

public class CollectGoodsFragment extends BaseFragment implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = CollectGoodsFragment.class.getSimpleName();

    protected RefreshLoadMoreLayout mRefreshloadmore;
    protected ListView mListview;
    protected TextView orderEmpty;

    protected CollectGoodsAdapter mAdapter;
    protected Handler mHandler = new Handler();
    private List<CollectGoodsBean.DataBean.ListBean> datas;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_goods;
    }

    @Override
    protected void initView() {
        mListview = findView(R.id.lv_collect_goods);
        orderEmpty = findView(R.id.tv_no_goods);

        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        datas = new ArrayList<>();
        mAdapter = new CollectGoodsAdapter(getActivity(), R.layout.item_collect_goods);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (datas.get(i).getStatus() == 0) {
                    Toast.makeText(getActivity(), "该商品已下架", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra(GoodsConstants.KEY_GOODS_ID, datas.get(i).getId());
                    startActivity(intent);
                }
            }
        });
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

        HttpUtils.doGet(Api.GET_FAVORITE_PRODUCT, mapParams, new Callback() {
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
                final CollectGoodsBean collectGoodsBean = GsonUtil.GsonToBean(result, CollectGoodsBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(collectGoodsBean.getCode()) && collectGoodsBean.getData() != null) {
                            if (collectGoodsBean != null &&
                                    collectGoodsBean.getData() != null &&
                                    collectGoodsBean.getData().getList() != null &&
                                    collectGoodsBean.getData().getList().size() != 0) {

                                if (pageNum == 1) {
                                    datas = collectGoodsBean.getData().getList();
                                } else {
                                    datas.addAll(collectGoodsBean.getData().getList());
                                }

                                hasNextPage = collectGoodsBean.getData().isHasNextPage();

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
}
