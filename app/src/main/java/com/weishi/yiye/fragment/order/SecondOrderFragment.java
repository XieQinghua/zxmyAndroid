package com.weishi.yiye.fragment.order;

import android.annotation.SuppressLint;
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
import com.weishi.yiye.activity.order.OrderDetailActivity;
import com.weishi.yiye.adapter.OrderAdapter;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.RecentOrderBean;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.bean.eventbus.OrderActionEvent;
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
 * @Date：2018/1/31
 * @Description：全部订单子页面Fragment
 * @Version:v1.0.0
 *****************************/
@SuppressLint("ValidFragment")
public class SecondOrderFragment extends BaseFragment implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = SecondOrderFragment.class.getSimpleName();
    protected RefreshLoadMoreLayout mRefreshloadmore;
    protected ListView mListview;
    protected TextView orderEmpty;
    protected OrderAdapter mAdapter;
    protected Handler mHandler = new Handler();
    private List<RecentOrderBean.DataBean.ListBean> datas;
    protected String type;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @SuppressLint("ValidFragment")
    public SecondOrderFragment(String type) {
        if (type != null) {
            this.type = type;
        } else {
            this.type = Constants.ORDER_TYPE_WHOLE;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        //Toast.makeText(getActivity(), type, Toast.LENGTH_LONG).show();
        mListview = findView(R.id.lv_order);
        orderEmpty = findView(R.id.tv_order_empty);

        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        datas = new ArrayList<>();
        mAdapter = new OrderAdapter(getActivity(), R.layout.item_order);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (datas.get(i).getShowStatus() != null) {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("orderId", datas.get(i).getId());
                    intent.putExtra("orderNum", datas.get(i).getOrderNum());
                    intent.putExtra("useStatus", datas.get(i).getUseStatus());
                    intent.putExtra("orderStatus", datas.get(i).getOrderStatus() + "");
                    intent.putExtra("showStatus", datas.get(i).getShowStatus());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.goods_have_deleted), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {
        getData(pageNum, type);
    }

    @Override
    public void onResume() {
        super.onResume();
        //待使用订单刷新操作
        if (Constants.ORDER_TYPE_BANDUSE.equals(type) || Constants.ORDER_TYPE_EVALUATION.equals(type)) {
            getData(1, type);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getData(final int pageNum, String type) {
        EventBus.getDefault().post(new LoadingStateEvent(LoadingStateEvent.START_ANIM));

        Map<String, Object> mapParams = new HashMap<>();
        switch (type) {
            case Constants.ORDER_TYPE_WHOLE:
                mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
                mapParams.put("tagType", 0);
                mapParams.put("pageNum", pageNum);
                mapParams.put("pageSize", pageSize);
                break;
            case Constants.ORDER_TYPE_TAKEPAYMENT:
                mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
                mapParams.put("tagType", 3);
                mapParams.put("orderStatus", "0");
                mapParams.put("pageNum", pageNum);
                mapParams.put("pageSize", pageSize);
                break;
            case Constants.ORDER_TYPE_BANDUSE:
                mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
                mapParams.put("tagType", 1);
                mapParams.put("useStatus", 0);
                mapParams.put("orderStatus", "1");
                mapParams.put("pageNum", pageNum);
                mapParams.put("pageSize", pageSize);
                break;
            case Constants.ORDER_TYPE_EVALUATION:
                mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
                mapParams.put("tagType", 4);
                mapParams.put("commentStatus", 0);
                mapParams.put("useStatus", 1);
                mapParams.put("pageNum", pageNum);
                mapParams.put("pageSize", pageSize);
                break;
            case Constants.ORDER_TYPE_REFUND:
                mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
                mapParams.put("tagType", 5);
                mapParams.put("pageNum", pageNum);
                mapParams.put("orderStatus", "2,3,4");
                mapParams.put("pageSize", pageSize);
                break;
            default:
                break;
        }

        HttpUtils.doGet(Api.GET_ORDER_LIST, mapParams, new Callback() {
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
                final RecentOrderBean recentOrderBean = GsonUtil.GsonToBean(result, RecentOrderBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (recentOrderBean != null &&
                                recentOrderBean.getData() != null &&
                                recentOrderBean.getData().getList() != null &&
                                recentOrderBean.getData().getList().size() != 0) {

                            if (pageNum == 1) {
                                datas = recentOrderBean.getData().getList();
                            } else {
                                datas.addAll(recentOrderBean.getData().getList());
                            }

                            hasNextPage = recentOrderBean.getData().isHasNextPage();

                            mAdapter.setData(datas);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRefreshloadmore.setVisibility(View.GONE);
                            orderEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderActionEvent(OrderActionEvent orderActionEvent) {
        getData(1, type);
    }

    /**
     * 下拉
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (datas != null) {
//                    datas.clear();
//                    mAdapter.notifyDataSetChanged();
//                    mRefreshloadmore.stopRefresh();
//                } else {
//                    datas = new ArrayList<>();
//                }
                getData(1, type);
                mRefreshloadmore.stopRefresh();
            }
        }, 500);
    }

    /**
     * 上拉
     */
    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            getData(pageNum, type);
        } else {
            Toast.makeText(getActivity(), "没有更多订单", Toast.LENGTH_SHORT).show();
        }
        mRefreshloadmore.stopLoadMore();
    }
}
