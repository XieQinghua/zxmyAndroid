package com.weishi.yiye.activity.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.ScreenUtils;
import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.SearchActivity;
import com.weishi.yiye.adapter.ConditionAdapter;
import com.weishi.yiye.adapter.RecNearbyShopsAdapter;
import com.weishi.yiye.adapter.SHomeShopTypeAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.BannerBean;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.ShopQueryBean;
import com.weishi.yiye.bean.SliderBean;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.AdapterInterface;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.LocationUtils;
import com.weishi.yiye.databinding.ActivitySecondHomeBinding;
import com.weishi.yiye.view.CusConvenientBanner;
import com.weishi.yiye.view.MyListView;
import com.weishi.yiye.view.NetworkImageHolderView1;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
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
 * @Date：2018/1/18
 * @Description：首页二级页面
 * @Version:v1.0.0
 *****************************/

public class SecondHomeActivity extends BaseSwipeBackActivity implements View.OnClickListener,
        LocationUtils.LocationUtilsinterface,
        RefreshLoadMoreLayout.CallBack {
    private static final String TAG = SecondHomeActivity.class.getSimpleName();
    private ActivitySecondHomeBinding secondHomeBinding;

    private View mHeader;
    // 顶部广告栏控件
    private CusConvenientBanner convenientBanner;

    private RelativeLayout rl_address;
    private TextView address;
    private ImageView refresh;

    private RelativeLayout rl_all, rl_nearby, rl_smart;
    private TextView tv_all, tv_nearby, tv_smart;
    private boolean flagDoubleAllClick = false;
    private boolean flagDoubleNearbyClick = false;
    private boolean flagDoubleSmartClick = false;

    private MyListView lv_shop_type;
    private SHomeShopTypeAdapter sHomeShopTypeAdapter;

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
    private ArrayList<ThreeShopTypeBean.DataBean.TwoProperty> shopTypeList = new ArrayList<>();

    @Override
    protected void initView() {
        //当前界面的父分类ID
        busiType = getIntent().getIntExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, 0);
        //if (getIntent().getStringExtra(ShopConstants.KEY_SHOP_TYPE_LIST) != null) {
        if (getIntent().getExtras().containsKey(ShopConstants.KEY_SHOP_TYPE_LIST)) {
            shopTypeList = (ArrayList<ThreeShopTypeBean.DataBean.TwoProperty>) getIntent().getExtras().getSerializable(ShopConstants.KEY_SHOP_TYPE_LIST);
        }
        secondHomeBinding = DataBindingUtil.setContentView(SecondHomeActivity.this, R.layout.activity_second_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        secondHomeBinding.tvSearch.setOnClickListener(this);

        secondHomeBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        mHeader = LayoutInflater.from(SecondHomeActivity.this).inflate(R.layout.activity_second_home_header, null);
        //轮播图
        convenientBanner = (CusConvenientBanner) mHeader.findViewById(R.id.convenientBanner);

        ViewGroup.LayoutParams para = convenientBanner.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 326 / 750;
        convenientBanner.setLayoutParams(para);

        //新分类
        lv_shop_type = (MyListView) mHeader.findViewById(R.id.lv_shop_type);
        sHomeShopTypeAdapter = new SHomeShopTypeAdapter(this, shopTypeList, new AdapterInterface.AdapterCallBack() {
            @Override
            public void callBack(int position, String opertype) {
                Intent tempIntent = new Intent(SecondHomeActivity.this, ThreeHomeActivity.class);
                tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, shopTypeList.get(position).sortId);
                tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_LIST, (Serializable) shopTypeList.get(position).property);
                startActivity(tempIntent);
            }
        });
        lv_shop_type.setAdapter(sHomeShopTypeAdapter);

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
        secondHomeBinding.rlFitAll.setOnClickListener(this);
        secondHomeBinding.rlFitNearby.setOnClickListener(this);
        secondHomeBinding.rlFitSmart.setOnClickListener(this);

        if (YiyeApplication.locationListBean != null) {
            address.setText("当前位置：" + YiyeApplication.locationListBean.getName());
        } else {
            address.setText("当前位置：定位中..");
        }

        secondHomeBinding.lvRecShops.addHeaderView(mHeader);

        convenientBanner.setParentView(secondHomeBinding.refreshloadmore);

        /**附近商家*/
        shopsDatas = new ArrayList<>();
        mRecShopsAdapter = new RecNearbyShopsAdapter(this, R.layout.item_rec_nearby_shops);
        mRecShopsAdapter.setData(shopsDatas);
        secondHomeBinding.lvRecShops.setAdapter(mRecShopsAdapter);

        conditionAdapter = new ConditionAdapter(this, R.layout.item_condition);
        secondHomeBinding.lvCondition.setAdapter(conditionAdapter);
    }

    @Override
    protected void initData() {
        //初始化轮播图数据
        initBannerData();
        //初始化商品分类视图
        //initShopTypeData();
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
                                    Toast.makeText(SecondHomeActivity.this, getString(R.string.no_datas), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SecondHomeActivity.this, getString(R.string.no_datas), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化轮播图界面
     */
    private void initBannerData() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("adType", 3);
        mapParams.put("belong", 1);
        mapParams.put("status", 1);
        mapParams.put("busiType", busiType);
        HttpUtils.doGet(Api.GET_HOME_PAGE_BANNERS, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "BannerData=" + result);

                final BannerBean bannerBean = GsonUtil.GsonToBean(result, BannerBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bannerBean != null && bannerBean.getData() != null) {
                            initBannerView(bannerBean.getData());
                        }
                    }
                });
            }
        });
    }

    private void initBannerView(final List<SliderBean> bannerBeans) {
        if (null != bannerBeans && bannerBeans.size() > 0) {
            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView1>() {
                @Override
                public NetworkImageHolderView1 createHolder() {
                    return new NetworkImageHolderView1(SecondHomeActivity.this);
                }
            }, bannerBeans)
                    // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focused})
                    // 设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    //点击banner图，跳转到对应的URL界面
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//                            if (bannerBeans.size() > 0) {
//                                Intent intent = new Intent(SecondHomeActivity.this, CommonWebViewActivity.class);
//                                intent.putExtra(Constants.INTENT_COMMON_ADV_URL, bannerBeans.get(position).getUrl());
//                                intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, bannerBeans.get(position).getTitle());
//                                startActivity(intent);
//                            }
                        }
                    });
            if (convenientBanner != null) {
                convenientBanner.startTurning(4000);
            }
        }
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> conditionList;
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(SecondHomeActivity.this, SearchActivity.class));
                break;
            case R.id.rl_all:
            case R.id.rl_fit_all:
                if (!flagDoubleAllClick) {
                    conditionList = new ArrayList<>();
                    conditionList.add("全部");
                    for (ThreeShopTypeBean.DataBean.TwoProperty shopType : shopTypeList) {
                        conditionList.add(shopType.typeName);
                    }
                    conditionFiltrate(0, conditionList);
                    flagDoubleAllClick = true;
                } else {
                    secondHomeBinding.llFiltrate.setVisibility(View.GONE);
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
                    secondHomeBinding.llFiltrate.setVisibility(View.GONE);
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
                    secondHomeBinding.llFiltrate.setVisibility(View.GONE);
                    flagDoubleSmartClick = false;
                }
                break;
            case R.id.ll_filtrate:
                secondHomeBinding.llFiltrate.setVisibility(View.GONE);
                break;
            case R.id.iv_refresh:
                refreshAddress();
                break;
            default:
                break;
        }
    }

    public void conditionFiltrate(final int type, final ArrayList<String> conditionList) {
        secondHomeBinding.lvRecShops.smoothScrollToPositionFromTop(1, rl_address.getHeight());
        secondHomeBinding.llFiltrate.setVisibility(View.VISIBLE);
        secondHomeBinding.llFiltrate.setOnClickListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conditionAdapter.setData(conditionList);
                conditionAdapter.notifyDataSetChanged();
                secondHomeBinding.lvCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(SecondHomeActivity.this, conditionList.get(i), Toast.LENGTH_SHORT).show();
                        secondHomeBinding.llFiltrate.setVisibility(View.GONE);
                        //调用商家列表
                        switch (type) {
                            case 0:
                                tv_all.setText(conditionList.get(i));
                                secondHomeBinding.tvFitAll.setText(conditionList.get(i));

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
                                secondHomeBinding.tvFitNearby.setText(conditionList.get(i));

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
                                secondHomeBinding.tvFitSmart.setText(conditionList.get(i));

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
        new LocationUtils(SecondHomeActivity.this, this);
    }

    @Override
    public void onLocationSuccess(LocationListBean locationListBean) {
        stopAnim();
        address.setText("当前位置：" + locationListBean.getName());
    }

    @Override
    public void onLocationError(String err) {
        stopAnim();
        Toast.makeText(SecondHomeActivity.this, err, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationState(LocationStateEvent locationStateEvent) {
        switch (locationStateEvent.getState()) {
            case LocationStateEvent.SUCCESS:
                address.setText("当前位置：" + locationStateEvent.getLocationListBean().getName());
                initShopList(1, busiType, orderBy, searchDistance);
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
                initBannerData();
                //initShopTypeData();
                initShopList(1, busiType, orderBy, searchDistance);
                secondHomeBinding.refreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initShopList(pageNum, busiType, orderBy, searchDistance);
        } else {
            Toast.makeText(SecondHomeActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                secondHomeBinding.refreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
