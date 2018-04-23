package com.weishi.yiye.activity.home;

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
import com.weishi.yiye.activity.SearchActivity;
import com.weishi.yiye.adapter.ConditionAdapter;
import com.weishi.yiye.adapter.RecNearbyShopsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.ShopQueryBean;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.LocationUtils;
import com.weishi.yiye.databinding.ActivityThreeHomeBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by shejun on 2018/3/29.
 */

public class ThreeHomeActivity extends BaseSwipeBackActivity implements View.OnClickListener,
        LocationUtils.LocationUtilsinterface,
        RefreshLoadMoreLayout.CallBack {
    private static final String TAG = ThreeHomeActivity.class.getSimpleName();
    private ActivityThreeHomeBinding threeHomeBinding;

    private View mHeader;

    private RelativeLayout rl_address;
    private TextView address;
    private ImageView refresh;

    private RelativeLayout rl_all, rl_nearby, rl_smart;
    private TextView tv_all, tv_nearby, tv_smart;
    private boolean flagDoubleAllClick = false;
    private boolean flagDoubleNearbyClick = false;
    private boolean flagDoubleSmartClick = false;

    private RecNearbyShopsAdapter mRecShopsAdapter;
    private List<QueryShopBean> shopsDatas;
    protected Handler mHandler = new Handler();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;
    private int busiType;
    private String orderBy;
    private Integer searchDistance;

    private ConditionAdapter conditionAdapter;
    private ArrayList<ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty> shopTypeList = new ArrayList<>();

    @Override
    protected void initView() {
        //当前界面的父分类ID
        busiType = getIntent().getIntExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, 0);
        if (getIntent().getExtras().containsKey(ShopConstants.KEY_SHOP_TYPE_LIST)) {
            shopTypeList = (ArrayList<ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty>) getIntent().getExtras().getSerializable(ShopConstants.KEY_SHOP_TYPE_LIST);
        }
        threeHomeBinding = DataBindingUtil.setContentView(ThreeHomeActivity.this, R.layout.activity_three_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        threeHomeBinding.tvSearch.setOnClickListener(this);

        threeHomeBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        mHeader = LayoutInflater.from(ThreeHomeActivity.this).inflate(R.layout.activity_search_result_header, null);

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
        threeHomeBinding.ivFitRefresh.setOnClickListener(this);
        threeHomeBinding.rlFitAll.setOnClickListener(this);
        threeHomeBinding.rlFitNearby.setOnClickListener(this);
        threeHomeBinding.rlFitSmart.setOnClickListener(this);

        if (YiyeApplication.locationListBean != null) {
            address.setText("当前位置：" + YiyeApplication.locationListBean.getName());
            threeHomeBinding.tvFitAddress.setText("当前位置：" + YiyeApplication.locationListBean.getName());
        } else {
            address.setText("当前位置：定位中..");
            threeHomeBinding.tvFitAddress.setText("当前位置：定位中..");
        }

        threeHomeBinding.lvRecShops.addHeaderView(mHeader);

        /**附近商家*/
        shopsDatas = new ArrayList<>();
        mRecShopsAdapter = new RecNearbyShopsAdapter(this, R.layout.item_rec_nearby_shops);
        mRecShopsAdapter.setData(shopsDatas);
        threeHomeBinding.lvRecShops.setAdapter(mRecShopsAdapter);

        conditionAdapter = new ConditionAdapter(this, R.layout.item_condition);
        threeHomeBinding.lvCondition.setAdapter(conditionAdapter);
    }

    @Override
    protected void initData() {
        //初始化附近商家列表
        initShopList(1, busiType, orderBy, searchDistance);
    }

    /**
     * 初始化附近商家列表
     */
    private void initShopList(final int pageNum, Integer busiType, String orderBy, Integer searchDistance) {
        startAnim(null);

        JSONObject jsonParams = new JSONObject();
        try {
            if (YiyeApplication.locationListBean != null) {
                jsonParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
                jsonParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
            } else {
                jsonParams.put("userLat", 0);
                jsonParams.put("userLng", 0);
            }
            jsonParams.put("pageNum", pageNum);
            jsonParams.put("pageSize", pageSize);
            jsonParams.put("busiType", busiType);
            jsonParams.put("status", 1);
            if (orderBy != null) {
                jsonParams.put("orderBy", orderBy);
            }
            if (searchDistance != null) {
                jsonParams.put("searchDistance", searchDistance);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.GET_STORE_LIST_BY_STORE, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();
                String result = response.body().string();
                Log.e(TAG, result);

                final ShopQueryBean homeNearShopBean = GsonUtil.GsonToBean(result, ShopQueryBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(homeNearShopBean.getCode())) {
                            if (pageNum == 1) {
                                if (homeNearShopBean != null &&
                                        homeNearShopBean.getData() != null &&
                                        homeNearShopBean.getData().getList() != null &&
                                        homeNearShopBean.getData().getList().size() != 0) {
                                    shopsDatas = homeNearShopBean.getData().getList();
                                } else {
                                    shopsDatas.clear();
                                    Toast.makeText(ThreeHomeActivity.this, getString(R.string.no_datas), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (homeNearShopBean != null && homeNearShopBean.getData() != null && homeNearShopBean.getData().getList() != null) {
                                    shopsDatas.addAll(homeNearShopBean.getData().getList());
                                }
                            }

                            hasNextPage = homeNearShopBean.getData().isHasNextPage();

                            mRecShopsAdapter.setData(shopsDatas);
                            mRecShopsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ThreeHomeActivity.this, getString(R.string.no_datas), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(ThreeHomeActivity.this, SearchActivity.class));
                break;
            case R.id.rl_all:
            case R.id.rl_fit_all:
                if (!flagDoubleAllClick) {
                    conditionList = new ArrayList<>();
                    conditionList.add("全部");
                    for (ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty shopType : shopTypeList) {
                        conditionList.add(shopType.typeName);
                    }
                    conditionFiltrate(0, conditionList);
                    flagDoubleAllClick = true;
                } else {
                    threeHomeBinding.llFiltrate.setVisibility(View.GONE);
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
                    threeHomeBinding.llFiltrate.setVisibility(View.GONE);
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
                    threeHomeBinding.llFiltrate.setVisibility(View.GONE);
                    flagDoubleSmartClick = false;
                }
                break;
            case R.id.ll_filtrate:
                threeHomeBinding.llFiltrate.setVisibility(View.GONE);
                break;
            case R.id.iv_refresh:
                refreshAddress();
                break;

            case R.id.iv_fit_refresh:
                refreshAddress();
                break;
            default:
                break;
        }
    }

    public void conditionFiltrate(final int type, final ArrayList<String> conditionList) {
        //threeHomeBinding.lvRecShops.smoothScrollToPositionFromTop(1, rl_address.getHeight());
        threeHomeBinding.llFiltrate.setVisibility(View.VISIBLE);
        threeHomeBinding.llFiltrate.setOnClickListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conditionAdapter.setData(conditionList);
                conditionAdapter.notifyDataSetChanged();
                threeHomeBinding.lvCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(ThreeHomeActivity.this, conditionList.get(i), Toast.LENGTH_SHORT).show();
                        threeHomeBinding.llFiltrate.setVisibility(View.GONE);
                        //调用商家列表
                        switch (type) {
                            case 0:
                                tv_all.setText(conditionList.get(i));
                                threeHomeBinding.tvFitAll.setText(conditionList.get(i));

                                if (shopTypeList.size() != 0) {
                                    if (i == 0) {
                                        busiType = getIntent().getIntExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, 0);
                                        initShopList(1, busiType, orderBy, searchDistance);
                                    } else {
                                        busiType = shopTypeList.get(i - 1).sortId;
                                        initShopList(1, busiType, orderBy, searchDistance);
                                    }
                                } else {
                                    busiType = getIntent().getIntExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, 0);
                                    initShopList(1, busiType, orderBy, searchDistance);
                                }
                                break;
                            case 1:
                                tv_nearby.setText(conditionList.get(i));
                                threeHomeBinding.tvFitNearby.setText(conditionList.get(i));

                                if ("附近".equals(conditionList.get(i))) {
                                    searchDistance = null;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("1km".equals(conditionList.get(i))) {
                                    searchDistance = 1;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("3km".equals(conditionList.get(i))) {
                                    searchDistance = 3;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("5km".equals(conditionList.get(i))) {
                                    searchDistance = 5;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("10km".equals(conditionList.get(i))) {
                                    searchDistance = 10;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("不限".equals(conditionList.get(i))) {
                                    searchDistance = null;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                }
                                break;
                            case 2:
                                tv_smart.setText(conditionList.get(i));
                                threeHomeBinding.tvFitSmart.setText(conditionList.get(i));

                                if ("智能排序".equals(conditionList.get(i))) {
                                    orderBy = null;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("离我最近".equals(conditionList.get(i))) {
                                    orderBy = null;
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("好评优先".equals(conditionList.get(i))) {
                                    orderBy = "star_level DESC";
                                    initShopList(1, busiType, orderBy, searchDistance);
                                } else if ("人气最高".equals(conditionList.get(i))) {
                                    orderBy = "is_hot DESC";
                                    initShopList(1, busiType, orderBy, searchDistance);
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
        threeHomeBinding.tvFitAddress.setText("当前位置：定位中..");
        new LocationUtils(ThreeHomeActivity.this, this);
    }

    @Override
    public void onLocationSuccess(LocationListBean locationListBean) {
        stopAnim();
        address.setText("当前位置：" + locationListBean.getName());
        threeHomeBinding.tvFitAddress.setText("当前位置：" + locationListBean.getName());
    }

    @Override
    public void onLocationError(String err) {
        stopAnim();
        Toast.makeText(ThreeHomeActivity.this, err, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationState(LocationStateEvent locationStateEvent) {
        switch (locationStateEvent.getState()) {
            case LocationStateEvent.SUCCESS:
                address.setText("当前位置：" + locationStateEvent.getLocationListBean().getName());
                break;
            case LocationStateEvent.FAIL:
                address.setText("当前位置：定位中..");
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
                initShopList(1, busiType, orderBy, searchDistance);
                threeHomeBinding.refreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initShopList(pageNum, busiType, orderBy, searchDistance);
        } else {
            Toast.makeText(ThreeHomeActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                threeHomeBinding.refreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
