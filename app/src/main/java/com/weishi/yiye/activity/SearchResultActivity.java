package com.weishi.yiye.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.ConditionAdapter;
import com.weishi.yiye.adapter.RecNearbyShopsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.SeachBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.LocationUtils;
import com.weishi.yiye.databinding.ActivitySearchResultBinding;

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
 * @Date：2018/1/18
 * @Description：商家列表界面
 * @Version:v1.0.0
 *****************************/

public class SearchResultActivity extends BaseSwipeBackActivity implements View.OnClickListener,
        LocationUtils.LocationUtilsinterface,
        RefreshLoadMoreLayout.CallBack {
    private static final String TAG = SearchResultActivity.class.getSimpleName();
    private ActivitySearchResultBinding searchResultBinding;
    private RecNearbyShopsAdapter mRecShopsAdapter;
    private List<QueryShopBean> shopsDatas;
    protected Handler mHandler = new Handler();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;
    private String keyWord;

    private View mHeader;
    private RelativeLayout rl_address;
    private TextView address;
    private ImageView refresh;

    private RelativeLayout rl_all, rl_nearby, rl_smart;
    private TextView tv_all, tv_nearby, tv_smart;
    private boolean flagDoubleAllClick = false;
    private boolean flagDoubleNearbyClick = false;
    private boolean flagDoubleSmartClick = false;

    private ConditionAdapter conditionAdapter;

    private String orderBy;
    private Integer searchDistance;

    @Override
    protected void initView() {
        keyWord = getIntent().getStringExtra(ShopConstants.KEY_SHOP_KEY_WORDS);
        searchResultBinding = DataBindingUtil.setContentView(SearchResultActivity.this, R.layout.activity_search_result);
        searchResultBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        searchResultBinding.tvSearch.setText(keyWord);
        searchResultBinding.tvSearch.setOnClickListener(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        mHeader = LayoutInflater.from(SearchResultActivity.this).inflate(R.layout.activity_search_result_header, null);
        rl_address = (RelativeLayout) mHeader.findViewById(R.id.rl_address);
        address = (TextView) mHeader.findViewById(R.id.tv_address);
        refresh = (ImageView) mHeader.findViewById(R.id.iv_refresh);
        rl_all = (RelativeLayout) mHeader.findViewById(R.id.rl_all);
        rl_nearby = (RelativeLayout) mHeader.findViewById(R.id.rl_nearby);
        rl_smart = (RelativeLayout) mHeader.findViewById(R.id.rl_smart);
        tv_all = (TextView) mHeader.findViewById(R.id.tv_all);
        tv_nearby = (TextView) mHeader.findViewById(R.id.tv_nearby);
        tv_smart = (TextView) mHeader.findViewById(R.id.tv_smart);
        refresh.setOnClickListener(this);
        rl_all.setOnClickListener(this);
        rl_nearby.setOnClickListener(this);
        rl_smart.setOnClickListener(this);
        searchResultBinding.ivFitRefresh.setOnClickListener(this);
        searchResultBinding.rlFitAll.setOnClickListener(this);
        searchResultBinding.rlFitNearby.setOnClickListener(this);
        searchResultBinding.rlFitSmart.setOnClickListener(this);

        if (YiyeApplication.locationListBean != null) {
            address.setText("当前位置：" + YiyeApplication.locationListBean.getName());
            searchResultBinding.tvFitAddress.setText("当前位置：" + YiyeApplication.locationListBean.getName());
        } else {
            address.setText("当前位置：定位中..");
            searchResultBinding.tvFitAddress.setText("当前位置：定位中..");
        }

        searchResultBinding.lvRecShops.addHeaderView(mHeader);

        /**商家列表*/
        shopsDatas = new ArrayList<>();
        mRecShopsAdapter = new RecNearbyShopsAdapter(this, R.layout.item_rec_nearby_shops);
        searchResultBinding.lvRecShops.setAdapter(mRecShopsAdapter);

        conditionAdapter = new ConditionAdapter(this, R.layout.item_condition);
        searchResultBinding.lvCondition.setAdapter(conditionAdapter);
    }

    @Override
    protected void initData() {
        initShopList(pageNum, orderBy, searchDistance);
    }

    private void initShopList(final int pageNum, String orderBy, Integer searchDistance) {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        //mapParams.put("status", 1);
        mapParams.put("keyword", keyWord);
        if (YiyeApplication.locationListBean != null) {
            mapParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
            mapParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
        } else {
            mapParams.put("userLat", 0);
            mapParams.put("userLng", 0);
        }
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);
        if (orderBy != null) {
            mapParams.put("orderBy", orderBy);
        }
        if (searchDistance != null) {
            mapParams.put("searchDistance", searchDistance);
        }

        HttpUtils.doGet(Api.GET_LIST_BY_KEYWORD, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();
                final String result = response.body().string();
                Log.e(TAG, result);

                final SeachBean seachBean = GsonUtil.GsonToBean(result, SeachBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(seachBean.getCode())) {
                            if (pageNum == 1) {
                                if (seachBean != null &&
                                        seachBean.getData() != null &&
                                        seachBean.getData().getList() != null &&
                                        seachBean.getData().getList().size() != 0) {
                                    shopsDatas = seachBean.getData().getList();
                                } else {
                                    shopsDatas.clear();
                                    Toast.makeText(SearchResultActivity.this, getString(R.string.no_datas), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (seachBean != null &&
                                        seachBean.getData() != null &&
                                        seachBean.getData().getList() != null &&
                                        seachBean.getData().getList().size() != 0) {
                                    shopsDatas.addAll(seachBean.getData().getList());
                                }
                            }

                            hasNextPage = seachBean.getData().isHasNextPage();

                            mRecShopsAdapter.setData(shopsDatas);
                            mRecShopsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SearchResultActivity.this, getString(R.string.no_datas), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> conditionList;
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(SearchResultActivity.this, SearchActivity.class));
                break;
            case R.id.rl_all:
            case R.id.rl_fit_all:
                if (!flagDoubleAllClick) {
                    conditionList = new ArrayList<>();
                    conditionList.add("全部");
                    conditionFiltrate(0, conditionList);
                    flagDoubleAllClick = true;
                } else {
                    searchResultBinding.llFiltrate.setVisibility(View.GONE);
                    flagDoubleAllClick = false;
                }
                break;
            case R.id.rl_nearby:
            case R.id.rl_fit_nearby:
                if (!flagDoubleNearbyClick) {
                    conditionList = new ArrayList<>();
                    conditionList.add("附近");
                    conditionList.add("1km");
                    conditionList.add("3km");
                    conditionList.add("5km");
                    conditionList.add("10km");
                    conditionList.add("不限");
                    conditionFiltrate(1, conditionList);
                    flagDoubleNearbyClick = true;
                } else {
                    searchResultBinding.llFiltrate.setVisibility(View.GONE);
                    flagDoubleNearbyClick = false;
                }
                break;
            case R.id.rl_smart:
            case R.id.rl_fit_smart:
                if (!flagDoubleSmartClick) {
                    conditionList = new ArrayList<>();
                    conditionList.add("智能排序");
                    conditionList.add("离我最近");
                    conditionList.add("好评优先");
                    conditionList.add("人气最高");
                    conditionFiltrate(2, conditionList);
                    flagDoubleSmartClick = true;
                } else {
                    searchResultBinding.llFiltrate.setVisibility(View.GONE);
                    flagDoubleSmartClick = false;
                }
                break;
            case R.id.ll_filtrate:
                searchResultBinding.llFiltrate.setVisibility(View.GONE);
                break;
            case R.id.iv_refresh:
            case R.id.iv_fit_refresh:
                refreshAddress();
                break;
            default:
                break;
        }
    }

    public void conditionFiltrate(final int type, final ArrayList<String> conditionList) {
        //searchResultBinding.lvRecShops.smoothScrollToPositionFromTop(1, rl_address.getHeight());
        searchResultBinding.llFiltrate.setVisibility(View.VISIBLE);
        searchResultBinding.llFiltrate.setOnClickListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conditionAdapter.setData(conditionList);
                conditionAdapter.notifyDataSetChanged();
                searchResultBinding.lvCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(SecondHomeActivity.this, conditionList.get(i), Toast.LENGTH_SHORT).show();
                        searchResultBinding.llFiltrate.setVisibility(View.GONE);
                        //调用商家列表
                        switch (type) {
                            case 0:
                                tv_all.setText(conditionList.get(i));
                                searchResultBinding.tvFitAll.setText(conditionList.get(i));

                                initShopList(1, orderBy, searchDistance);
                                break;
                            case 1:
                                tv_nearby.setText(conditionList.get(i));
                                searchResultBinding.tvFitNearby.setText(conditionList.get(i));

                                if ("附近".equals(conditionList.get(i))) {
                                    searchDistance = null;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("1km".equals(conditionList.get(i))) {
                                    searchDistance = 1;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("3km".equals(conditionList.get(i))) {
                                    searchDistance = 3;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("5km".equals(conditionList.get(i))) {
                                    searchDistance = 5;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("10km".equals(conditionList.get(i))) {
                                    searchDistance = 10;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("不限".equals(conditionList.get(i))) {
                                    searchDistance = null;
                                    initShopList(1, orderBy, searchDistance);
                                }
                                break;
                            case 2:
                                tv_smart.setText(conditionList.get(i));
                                searchResultBinding.tvFitSmart.setText(conditionList.get(i));

                                if ("智能排序".equals(conditionList.get(i))) {
                                    orderBy = null;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("离我最近".equals(conditionList.get(i))) {
                                    orderBy = null;
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("好评优先".equals(conditionList.get(i))) {
                                    orderBy = "star_level DESC";
                                    initShopList(1, orderBy, searchDistance);
                                } else if ("人气最高".equals(conditionList.get(i))) {
                                    orderBy = "is_hot DESC";
                                    initShopList(1, orderBy, searchDistance);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
    }

    private void refreshAddress() {
        startAnim(null);
        address.setText("当前位置：定位中..");
        searchResultBinding.tvFitAddress.setText("当前位置：定位中..");
        new LocationUtils(SearchResultActivity.this, this);
    }

    @Override
    public void onLocationSuccess(LocationListBean locationListBean) {
        stopAnim();
        address.setText("当前位置：" + locationListBean.getName());
        searchResultBinding.tvFitAddress.setText("当前位置：" + locationListBean.getName());
        initShopList(pageNum, orderBy, searchDistance);
    }

    @Override
    public void onLocationError(String err) {
        stopAnim();
        Toast.makeText(SearchResultActivity.this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNum = 1;
                initShopList(pageNum, orderBy, searchDistance);
                searchResultBinding.refreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initShopList(pageNum, orderBy, searchDistance);
        } else {
            Toast.makeText(SearchResultActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchResultBinding.refreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
