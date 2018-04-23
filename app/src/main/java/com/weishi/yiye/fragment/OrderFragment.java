package com.weishi.yiye.fragment;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.LoginActivity;
import com.weishi.yiye.activity.order.AllOrderActivity;
import com.weishi.yiye.activity.order.OrderDetailActivity;
import com.weishi.yiye.adapter.OrderAdapter;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.OrderNumBean;
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
 * @Author：zym
 * @Date：2018/1/9
 * @Description：订单
 * @Version:v1.0.0
 *****************************/
public class OrderFragment extends BaseFragment implements View.OnClickListener, RefreshLoadMoreLayout.CallBack {
    private static final String TAG = OrderFragment.class.getSimpleName();
    protected LinearLayout orderInfo, loginInfo;
    private Button btn_login;
    protected ListView mListview;
    protected TextView orderEmpty;
    protected RefreshLoadMoreLayout mRefreshloadmore;
    protected OrderAdapter mAdapter;
    protected Handler mHandler = new Handler();
    private List<RecentOrderBean.DataBean.ListBean> datas;
    private View mHeader;
    private RelativeLayout rl_all_order, rl_dfk_order, rl_dsy_order, rl_dpj_order, rl_tk_order;
    private TextView dfkNum, dsyNum, dpjNum, tkNum;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        ((TextView) findView(R.id.fragment_top_title)).setText("订单");

        orderInfo = findView(R.id.ll_order_info);
        loginInfo = findView(R.id.ll_login_info);
        btn_login = findView(R.id.btn_login);

        btn_login.setOnClickListener(this);

        //判断登录状态
        if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            orderInfo.setVisibility(View.VISIBLE);
            loginInfo.setVisibility(View.GONE);
        } else {
            orderInfo.setVisibility(View.GONE);
            loginInfo.setVisibility(View.VISIBLE);
        }

        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        mListview = findView(R.id.lv_order);
        orderEmpty = findView(R.id.tv_order_empty);

        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_header, null);
        rl_all_order = (RelativeLayout) mHeader.findViewById(R.id.rl_all_order);
        rl_dfk_order = (RelativeLayout) mHeader.findViewById(R.id.rl_dfk_order);
        rl_dsy_order = (RelativeLayout) mHeader.findViewById(R.id.rl_dsy_order);
        rl_dpj_order = (RelativeLayout) mHeader.findViewById(R.id.rl_dpj_order);
        rl_tk_order = (RelativeLayout) mHeader.findViewById(R.id.rl_tk_order);

        dfkNum = (TextView) mHeader.findViewById(R.id.tv_dfk_order_num);
        dsyNum = (TextView) mHeader.findViewById(R.id.tv_dsy_order_num);
        dpjNum = (TextView) mHeader.findViewById(R.id.tv_dpj_order_num);
        tkNum = (TextView) mHeader.findViewById(R.id.tv_tk_order_num);

        mListview.addHeaderView(mHeader);

        datas = new ArrayList<>();
        mAdapter = new OrderAdapter(getActivity(), R.layout.item_order);
        mAdapter.setData(datas);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    return;
                }
                if (i > 0 && datas.get(i - 1).getShowStatus() != null) {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("orderId", datas.get(i - 1).getId());
                    intent.putExtra("orderNum", datas.get(i - 1).getOrderNum());
                    intent.putExtra("useStatus", datas.get(i - 1).getUseStatus());
                    intent.putExtra("orderStatus", datas.get(i - 1).getOrderStatus() + "");
                    intent.putExtra("showStatus", datas.get(i - 1).getShowStatus());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.goods_have_deleted), Toast.LENGTH_SHORT).show();
                }
            }
        });

        rl_all_order.setOnClickListener(this);
        rl_dfk_order.setOnClickListener(this);
        rl_dsy_order.setOnClickListener(this);
        rl_dpj_order.setOnClickListener(this);
        rl_tk_order.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断登录状态
        if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            orderInfo.setVisibility(View.VISIBLE);
            loginInfo.setVisibility(View.GONE);
            getOrderNum();
            getData(pageNum);
        } else {
            orderInfo.setVisibility(View.GONE);
            loginInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void initData() {
        //未登录状态不加载数据
        if (!SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            return;
        }
        getOrderNum();
        getData(pageNum);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderActionEvent(OrderActionEvent orderActionEvent) {
        getOrderNum();
        getData(1);
    }

    private void getOrderNum() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));

        HttpUtils.doGet(Api.COUNT_ORDER_NUM, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e(TAG, "OrderNum = " + result);

                final OrderNumBean orderNumBean = GsonUtil.GsonToBean(result, OrderNumBean.class);
                if (orderNumBean != null &&
                        orderNumBean.getData() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (orderNumBean.getData().getPendPayNum() != 0) {
                                dfkNum.setVisibility(View.VISIBLE);
                                dfkNum.setText(orderNumBean.getData().getPendPayNum() + "");
                            } else {
                                dfkNum.setVisibility(View.INVISIBLE);
                            }

                            if (orderNumBean.getData().getPendUseNum() != 0) {
                                dsyNum.setVisibility(View.VISIBLE);
                                dsyNum.setText(orderNumBean.getData().getPendUseNum() + "");
                            } else {
                                dsyNum.setVisibility(View.INVISIBLE);
                            }

                            if (orderNumBean.getData().getPendCommentNum() != 0) {
                                dpjNum.setVisibility(View.VISIBLE);
                                dpjNum.setText(orderNumBean.getData().getPendCommentNum() + "");
                            } else {
                                dpjNum.setVisibility(View.INVISIBLE);
                            }

                            if (orderNumBean.getData().getRefundNum() != 0) {
                                tkNum.setVisibility(View.VISIBLE);
                                tkNum.setText(orderNumBean.getData().getRefundNum() + "");
                            } else {
                                tkNum.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    private void getData(final int pageNum) {
        EventBus.getDefault().post(new LoadingStateEvent(LoadingStateEvent.START_ANIM));

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        mapParams.put("tagType", 2);
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);

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
                                if (datas != null) {
                                    datas.clear();
                                }
                                datas = recentOrderBean.getData().getList();
                            } else {
                                datas.addAll(recentOrderBean.getData().getList());
                            }

                            hasNextPage = recentOrderBean.getData().isHasNextPage();

                            mAdapter.setData(datas);
                            mAdapter.notifyDataSetChanged();

                            mRefreshloadmore.setVisibility(View.VISIBLE);
                            orderEmpty.setVisibility(View.GONE);
                        } else {
                            mRefreshloadmore.setVisibility(View.GONE);
                            orderEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.rl_all_order:
                intent = new Intent(getActivity(), AllOrderActivity.class);
                intent.putExtra("type", Constants.ORDER_TYPE_WHOLE);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_dfk_order:
                intent = new Intent(getActivity(), AllOrderActivity.class);
                intent.putExtra("type", Constants.ORDER_TYPE_TAKEPAYMENT);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_dsy_order:
                intent = new Intent(getActivity(), AllOrderActivity.class);
                intent.putExtra("type", Constants.ORDER_TYPE_BANDUSE);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_dpj_order:
                intent = new Intent(getActivity(), AllOrderActivity.class);
                intent.putExtra("type", Constants.ORDER_TYPE_EVALUATION);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_tk_order:
                intent = new Intent(getActivity(), AllOrderActivity.class);
                intent.putExtra("type", Constants.ORDER_TYPE_REFUND);
                getActivity().startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderNum();
                getData(1);
                mRefreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            getData(pageNum);
        } else {
            Toast.makeText(getActivity(), "没有更多订单", Toast.LENGTH_SHORT).show();
        }
        mRefreshloadmore.stopLoadMore();
    }
}
