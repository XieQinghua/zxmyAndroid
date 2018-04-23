package com.weishi.yiye.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.ScreenUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.CommonWebViewActivity;
import com.weishi.yiye.activity.RegisterActivity;
import com.weishi.yiye.activity.ScanPayActivity;
import com.weishi.yiye.activity.SearchActivity;
import com.weishi.yiye.activity.ShopDetailActivity;
import com.weishi.yiye.activity.home.HeadlineActivity;
import com.weishi.yiye.activity.home.SecondHomeActivity;
import com.weishi.yiye.activity.mine.ShopsJoinDataActivity;
import com.weishi.yiye.activity.nearby.LocationListActivity;
import com.weishi.yiye.adapter.RecNearbyShopsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.AdvDatasBean;
import com.weishi.yiye.bean.HeadlineBean;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.RecShopsAdvBean;
import com.weishi.yiye.bean.ShopDetailBean;
import com.weishi.yiye.bean.ShopQueryBean;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.common.util.ValidatorUtils;
import com.weishi.yiye.view.CusConvenientBanner;
import com.weishi.yiye.view.CusRefreshLoadMoreLayout;
import com.weishi.yiye.view.NetworkImageHolderView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
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

import static android.app.Activity.RESULT_OK;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/9
 * @Description：首页
 * @Version:v1.0.0
 *****************************/
public class HomeFragment extends BaseFragment implements View.OnClickListener, RefreshLoadMoreLayout.CallBack {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int REQUEST_CODE_SCAN = 1;

    private static final int REQUEST_LOCATION = 2;

    /**
     * 权限检测器
     */
    private CheckPermission checkPermission;

    protected CusRefreshLoadMoreLayout mRefreshloadmore;
    protected Handler mHandler = new Handler();

    private TextView tv_address;
    private TextView tv_search;
    private ImageView iv_qr_code;

    private View mHeader;
    private ListView lv_rec_shops;
    private RecNearbyShopsAdapter mRecShopsAdapter;

    private ListView lv_explosive_store;
    private RecNearbyShopsAdapter mExplosiveStoreAdapter;
    private List<QueryShopBean> explosiveStoreDatas = new ArrayList<>();

    private LinearLayout ll_third_platform, ll_shop_type;
    private TextView tv_thirdPlatform0, tv_thirdPlatform1, tv_thirdPlatform2, tv_thirdPlatform3;
    private Map<String, TextView> thirdPlatformTvMaps = new HashMap<String, TextView>();
    private SimpleDraweeView sdv_thirdPlatform0, sdv_thirdPlatform1, sdv_thirdPlatform2, sdv_thirdPlatform3;
    private Map<String, SimpleDraweeView> thirdPlatformSdvMaps = new HashMap<String, SimpleDraweeView>();

    private TextView tv_shopType0, tv_shopType1, tv_shopType2, tv_shopType3;
    private Map<String, TextView> shopTypeTvMaps = new HashMap<String, TextView>();
    private SimpleDraweeView sdv_shopType0, sdv_shopType1, sdv_shopType2, sdv_shopType3;
    private Map<String, SimpleDraweeView> shopTypeSdvMaps = new HashMap<String, SimpleDraweeView>();

    private SimpleDraweeView sdv_adv1, sdv_adv2, sdv_adv3, sdv_adv4, sdv_adv5;
    private ArrayList<SimpleDraweeView> sdvList = new ArrayList<>();

    private List<QueryShopBean> shopsDatas = new ArrayList<>();

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;
    private CusConvenientBanner convenientBanner;

    private LinearLayout ll_move;
    private AnimationSet set;
    private TranslateAnimation translate;
    private int position;
    private List<HeadlineBean.DataBean.ListBean> headlineList;
    private List<ThreeShopTypeBean.DataBean> threeShopTypeBeansList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        Log.e(TAG, "initView========");
        EventBus.getDefault().register(this);
        checkPermission = new CheckPermission(getActivity()) {
            @Override
            public void permissionSuccess(int requestCode) {
                goScan();
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finish
                Toast.makeText(getActivity(), "没有权限无法扫描", Toast.LENGTH_LONG).show();
            }
        };

        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        tv_address = findView(R.id.tv_address);
        tv_search = findView(R.id.tv_search);
        iv_qr_code = findView(R.id.iv_qr_code);

        lv_rec_shops = findView(R.id.lv_rec_shops);

        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header, null);

        /**轮播图*/
        convenientBanner = (CusConvenientBanner) mHeader.findViewById(R.id.convenientBanner);

        ViewGroup.LayoutParams para = convenientBanner.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 326 / 750;
        convenientBanner.setLayoutParams(para);

        ll_third_platform = (LinearLayout) mHeader.findViewById(R.id.ll_third_platform);

        tv_thirdPlatform0 = (TextView) mHeader.findViewById(R.id.tv_thirdPlatform0);
        thirdPlatformTvMaps.put("tv_thirdPlatform4", tv_thirdPlatform0);
        tv_thirdPlatform1 = (TextView) mHeader.findViewById(R.id.tv_thirdPlatform1);
        thirdPlatformTvMaps.put("tv_thirdPlatform5", tv_thirdPlatform1);
        tv_thirdPlatform2 = (TextView) mHeader.findViewById(R.id.tv_thirdPlatform2);
        thirdPlatformTvMaps.put("tv_thirdPlatform6", tv_thirdPlatform2);
        tv_thirdPlatform3 = (TextView) mHeader.findViewById(R.id.tv_thirdPlatform3);
        thirdPlatformTvMaps.put("tv_thirdPlatform7", tv_thirdPlatform3);

        sdv_thirdPlatform0 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_thirdPlatform0);
        thirdPlatformSdvMaps.put("sdv_thirdPlatform4", sdv_thirdPlatform0);
        sdv_thirdPlatform1 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_thirdPlatform1);
        thirdPlatformSdvMaps.put("sdv_thirdPlatform5", sdv_thirdPlatform1);
        sdv_thirdPlatform2 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_thirdPlatform2);
        thirdPlatformSdvMaps.put("sdv_thirdPlatform6", sdv_thirdPlatform2);
        sdv_thirdPlatform3 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_thirdPlatform3);
        thirdPlatformSdvMaps.put("sdv_thirdPlatform7", sdv_thirdPlatform3);

        ll_shop_type = (LinearLayout) mHeader.findViewById(R.id.ll_shop_type);
        tv_shopType0 = (TextView) mHeader.findViewById(R.id.tv_shopType0);
        shopTypeTvMaps.put("tv_shopType0", tv_shopType0);
        tv_shopType1 = (TextView) mHeader.findViewById(R.id.tv_shopType1);
        shopTypeTvMaps.put("tv_shopType1", tv_shopType1);
        tv_shopType2 = (TextView) mHeader.findViewById(R.id.tv_shopType2);
        shopTypeTvMaps.put("tv_shopType2", tv_shopType2);
        tv_shopType3 = (TextView) mHeader.findViewById(R.id.tv_shopType3);
        shopTypeTvMaps.put("tv_shopType3", tv_shopType3);

        sdv_shopType0 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_shopType0);
        shopTypeSdvMaps.put("sdv_shopType0", sdv_shopType0);
        sdv_shopType1 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_shopType1);
        shopTypeSdvMaps.put("sdv_shopType1", sdv_shopType1);
        sdv_shopType2 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_shopType2);
        shopTypeSdvMaps.put("sdv_shopType2", sdv_shopType2);
        sdv_shopType3 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_shopType3);
        shopTypeSdvMaps.put("sdv_shopType3", sdv_shopType3);

        /**推荐商家*/
        sdv_adv1 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_adv1);
        sdv_adv2 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_adv2);
        sdv_adv3 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_adv3);
        sdv_adv4 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_adv4);
        sdvList.add(sdv_adv1);
        sdvList.add(sdv_adv2);
        sdvList.add(sdv_adv3);
        sdvList.add(sdv_adv4);

        /**美业介绍*/
        sdv_adv5 = (SimpleDraweeView) mHeader.findViewById(R.id.sdv_adv5);

        /**众享爆品*/
        lv_explosive_store = (ListView) mHeader.findViewById(R.id.lv_explosive_store);
        mExplosiveStoreAdapter = new RecNearbyShopsAdapter(getActivity(), R.layout.item_rec_nearby_shops);
        lv_explosive_store.setAdapter(mExplosiveStoreAdapter);

        lv_rec_shops.addHeaderView(mHeader);

        if (YiyeApplication.locationListBean != null) {
            tv_address.setText(YiyeApplication.locationListBean.getName());
        } else {
            tv_address.setText("定位中..");
        }

        convenientBanner.setParentView(mRefreshloadmore);

        tv_address.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        iv_qr_code.setOnClickListener(this);

        /**附近商家*/
        mRecShopsAdapter = new RecNearbyShopsAdapter(getActivity(), R.layout.item_rec_nearby_shops);
        lv_rec_shops.setAdapter(mRecShopsAdapter);
    }

    @Override
    protected void initData() {
        //banner图
        initAdvData();
        //分类
        initDataType();
        //初始化快报
        initHeadlineData();
        //初始化推荐商家
        initRecShops();
        //美业介绍
        initMeiyeIntroduce();
        //众享爆品
        initExplosiveStoreData();
        //初始化店铺数据
        initShopData(1);
    }

    /**
     * 美业快报数据
     */
    private void initHeadlineData() {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("pageNum", 1);
        mapParams.put("pageSize", 5);

        HttpUtils.doGet(Api.GET_ARTICLE_LIST, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                if (getActivity() == null) {
                    return;
                }
                final HeadlineBean headlineBean = GsonUtil.GsonToBean(result, HeadlineBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(headlineBean.getCode()) && headlineBean.getData() != null) {
                            if (headlineBean != null &&
                                    headlineBean.getData() != null &&
                                    headlineBean.getData().getList() != null &&
                                    headlineBean.getData().getList().size() != 0) {
                                headlineList = headlineBean.getData().getList();
                                moveTranslate();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 一级分类数据
     */
    private void initDataType() {
        HttpUtils.doGet(Api.GET_ALL_SORT, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "type===" + result);

                if (getActivity() == null) {
                    return;
                }
                final ThreeShopTypeBean threeShopTypeBean = GsonUtil.GsonToBean(result, ThreeShopTypeBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(threeShopTypeBean.code) && threeShopTypeBean.data != null) {
                            if (threeShopTypeBean != null &&
                                    threeShopTypeBean.data != null &&
                                    threeShopTypeBean.data.size() != 0) {
                                threeShopTypeBeansList = threeShopTypeBean.data;

                                /**上方四个**/
                                if (threeShopTypeBeansList.size() >= 4) {
                                    ll_shop_type.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < threeShopTypeBeansList.size(); i++) {
                                        if (i > 3) {
                                            break;
                                        }
                                        final ThreeShopTypeBean.DataBean dataBean = threeShopTypeBeansList.get(i);
                                        //获取对应的TextView
                                        TextView tv = shopTypeTvMaps.get("tv_shopType" + i);
                                        tv.setText(dataBean.typeName);
                                        SimpleDraweeView sdv = shopTypeSdvMaps.get("sdv_shopType" + i);
                                        sdv.setImageURI(Uri.parse(dataBean.icon));
                                        sdv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (dataBean.property != null && dataBean.property.size() != 0) {
                                                    Intent tempIntent = new Intent(getActivity(), SecondHomeActivity.class);
                                                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, dataBean.sortId);
                                                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_LIST, (Serializable) dataBean.property);
                                                    startActivity(tempIntent);
                                                } else {
                                                    Intent tempIntent = new Intent(getActivity(), SecondHomeActivity.class);
                                                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, dataBean.sortId);
                                                    startActivity(tempIntent);
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    ll_shop_type.setVisibility(View.GONE);
                                }

                                /**下方四个**/
                                if (threeShopTypeBeansList.size() >= 8) {
                                    ll_third_platform.setVisibility(View.VISIBLE);
                                    for (int i = 4; i < threeShopTypeBeansList.size(); i++) {
                                        if (i > 7) {
                                            break;
                                        }
                                        final ThreeShopTypeBean.DataBean dataBean = threeShopTypeBeansList.get(i);
                                        //获取对应的TextView
                                        TextView tv = thirdPlatformTvMaps.get("tv_thirdPlatform" + i);
                                        tv.setText(dataBean.typeName);
                                        SimpleDraweeView sdv = thirdPlatformSdvMaps.get("sdv_thirdPlatform" + i);
                                        sdv.setImageURI(Uri.parse(dataBean.icon));
                                        sdv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (dataBean.property != null && dataBean.property.size() != 0) {
                                                    Intent tempIntent = new Intent(getActivity(), SecondHomeActivity.class);
                                                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, dataBean.sortId);
                                                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_LIST, (Serializable) dataBean.property);
                                                    startActivity(tempIntent);
                                                } else {
                                                    Intent tempIntent = new Intent(getActivity(), SecondHomeActivity.class);
                                                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, dataBean.sortId);
                                                    startActivity(tempIntent);
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    ll_third_platform.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化推荐商家（新需求）
     */
    private void initRecShops() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("belong", 1);
        mapParams.put("status", 1);
        mapParams.put("adType", 4);
        mapParams.put("skipType", 2);
        HttpUtils.doGet(Api.GET_HOME_PAGE_BANNERS, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "RecShops=" + result);

                if (getActivity() == null) {
                    return;
                }
                final RecShopsAdvBean recShopsAdvBean = GsonUtil.GsonToBean(result, RecShopsAdvBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(recShopsAdvBean.getCode()) &&
                                recShopsAdvBean.getData() != null &&
                                recShopsAdvBean.getData().size() != 0) {
                            for (int i = 0; i < recShopsAdvBean.getData().size(); i++) {
                                if (i > 3) {
                                    break;
                                }
                                sdvList.get(i).setImageURI(Uri.parse(recShopsAdvBean.getData().get(i).getAdImg()));
                                int position = i;
                                sdvList.get(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = null;
                                        switch (recShopsAdvBean.getData().get(position).getSkipType()) {
                                            //H5
                                            case 1:
                                                intent = new Intent(getActivity(), CommonWebViewActivity.class);
                                                intent.putExtra(Constants.INTENT_COMMON_ADV_URL, recShopsAdvBean.getData().get(position).getUrl());
                                                intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, recShopsAdvBean.getData().get(position).getTitle());
                                                startActivity(intent);
                                                break;
                                            //店铺
                                            case 2:
                                                intent = new Intent(getActivity(), ShopDetailActivity.class);
                                                intent.putExtra(ShopConstants.KEY_SHOP_ID, recShopsAdvBean.getData().get(position).getStoreId());
                                                startActivity(intent);
                                                break;
                                            case 3:
                                                Toast.makeText(getActivity(), "系统正在努力上线中", Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化美业介绍
     */
    private void initMeiyeIntroduce() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("belong", 1);
        mapParams.put("status", 1);
        mapParams.put("adType", 5);
        mapParams.put("skipType", 1);
        HttpUtils.doGet(Api.GET_HOME_PAGE_BANNERS, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "MeiyeIntro=" + result);

                if (getActivity() == null) {
                    return;
                }
                final RecShopsAdvBean recShopsAdvBean = GsonUtil.GsonToBean(result, RecShopsAdvBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(recShopsAdvBean.getCode()) &&
                                recShopsAdvBean.getData() != null &&
                                recShopsAdvBean.getData().size() != 0) {
                            sdv_adv5.setImageURI(Uri.parse(recShopsAdvBean.getData().get(0).getAdImg()));

                            sdv_adv5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
                                    intent.putExtra(Constants.INTENT_COMMON_ADV_URL, recShopsAdvBean.getData().get(0).getUrl());
                                    intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, recShopsAdvBean.getData().get(0).getTitle());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化首页广告数据
     */
    private void initAdvData() {
        EventBus.getDefault().post(new LoadingStateEvent(LoadingStateEvent.START_ANIM));
        HttpUtils.doGet(Api.GET_HOME_PAGE_ADV_DATAS, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventBus.getDefault().post(new LoadingStateEvent(LoadingStateEvent.STOP_ANIM));

                String result = response.body().string();
                Log.e(TAG, "adv===" + result);

                if (getActivity() == null) {
                    return;
                }
                final AdvDatasBean advDatasBean = GsonUtil.GsonToBean(result, AdvDatasBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (advDatasBean != null && Api.STATE_SUCCESS.equals(advDatasBean.getCode())) {
                            /**banner数据**/
                            if (advDatasBean.getData() != null && advDatasBean.getData().getBannerData() != null &&
                                    advDatasBean.getData().getBannerData().size() > 0) {
                                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                                    @Override
                                    public NetworkImageHolderView createHolder() {
                                        return new NetworkImageHolderView(getActivity());
                                    }
                                }, advDatasBean.getData().getBannerData())
                                        // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                                        .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focused})
                                        // 设置指示器的方向
                                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                                        //点击banner图，跳转到对应的URL界面
                                        .setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position) {
                                                if (advDatasBean.getData().getBannerData().size() > 0) {
                                                    switch (advDatasBean.getData().getBannerData().get(position).getSkipType()) {
                                                        //H5
                                                        case 1:
                                                            Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
                                                            intent.putExtra(Constants.INTENT_COMMON_ADV_URL, advDatasBean.getData().getBannerData().get(position).getUrl());
                                                            intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, advDatasBean.getData().getBannerData().get(position).getTitle());
                                                            startActivity(intent);
                                                            break;
                                                        //店铺
                                                        case 2:
                                                            Intent intentDetail = new Intent(getActivity(), ShopDetailActivity.class);
                                                            intentDetail.putExtra(ShopConstants.KEY_SHOP_ID, advDatasBean.getData().getBannerData().get(position).getStoreId());
                                                            startActivity(intentDetail);
                                                            break;
                                                        case 3:
                                                            Toast.makeText(getActivity(), "系统正在努力上线中", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                }
                                            }
                                        });
                                if (convenientBanner != null) {
                                    convenientBanner.startTurning(5000);
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), advDatasBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 众享爆品数据
     */
    private void initExplosiveStoreData() {
        JSONObject jsonParams = new JSONObject();
        try {
            if (YiyeApplication.locationListBean != null) {
                jsonParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
                jsonParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
            } else {
                jsonParams.put("userLat", 0);
                jsonParams.put("userLng", 0);
            }
            jsonParams.put("pageNum", 1);
            jsonParams.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.GET_EXPLOSIVE_STORE_LIST, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                if (getActivity() == null) {
                    return;
                }
                final ShopQueryBean explosiveStoreBean = GsonUtil.GsonToBean(result, ShopQueryBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "initExplosiveStoreData========");
                        if (explosiveStoreBean != null && Api.STATE_SUCCESS.equals(explosiveStoreBean.getCode())) {
                            if (explosiveStoreBean != null && explosiveStoreBean.getData() != null && explosiveStoreBean.getData().getList() != null) {
                                explosiveStoreDatas = explosiveStoreBean.getData().getList();
                            }

                            mExplosiveStoreAdapter.setData(explosiveStoreDatas);
                            mExplosiveStoreAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化店铺数据
     */
    private void initShopData(final int pageNum) {
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
            jsonParams.put("isExplosive", 0);
            jsonParams.put("status", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.GET_STORE_LIST_BY_STORE, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                if (getActivity() == null) {
                    return;
                }
                final ShopQueryBean homeNearShopBean = GsonUtil.GsonToBean(result, ShopQueryBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "initShopData========");
                        if (homeNearShopBean != null && Api.STATE_SUCCESS.equals(homeNearShopBean.getCode())) {
                            if (pageNum == 1) {
                                if (homeNearShopBean != null && homeNearShopBean.getData() != null && homeNearShopBean.getData().getList() != null) {
                                    shopsDatas = homeNearShopBean.getData().getList();
                                }
                            } else {
                                if (homeNearShopBean != null && homeNearShopBean.getData() != null && homeNearShopBean.getData().getList() != null) {
                                    shopsDatas.addAll(homeNearShopBean.getData().getList());
                                }
                            }

                            hasNextPage = homeNearShopBean.getData().isHasNextPage();

                            mRecShopsAdapter.setData(shopsDatas);
                            mRecShopsAdapter.notifyDataSetChanged();
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
                initAdvData();
                initExplosiveStoreData();
                initShopData(1);
                if (headlineList == null || headlineList.size() == 0) {
                    initHeadlineData();
                }
                mRefreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initShopData(pageNum);
        } else {
            Toast.makeText(getActivity(), getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshloadmore.stopLoadMore();
            }
        }, 500);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                if (YiyeApplication.locationListBean != null) {
                    startActivityForResult(new Intent(getActivity(), LocationListActivity.class), REQUEST_LOCATION);
                } else {
                    Toast.makeText(getActivity(), "请先打开定位权限", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.iv_qr_code:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_CAMERA);
                } else {
                    goScan();
                }
                break;
            case R.id.ll_move:
                Intent intent = new Intent(getActivity(), HeadlineActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationState(LocationStateEvent locationStateEvent) {
        switch (locationStateEvent.getState()) {
            case LocationStateEvent.SUCCESS:
                tv_address.setText(locationStateEvent.getLocationListBean().getName());
                //定位成功在刷新店铺数据
                initAdvData();
                initExplosiveStoreData();
                initShopData(1);
                break;
            case LocationStateEvent.FAIL:
                tv_address.setText("定位中..");
                break;
            default:
                break;
        }
    }

    /**
     * 进入扫一扫页面
     */
    public void goScan() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);

        //ZxingConfig是配置类 可以设置是否显示底部布局，闪光灯，相册，是否播放提示音 震动等动能
        //也可以不传这个参数
        //不传的话 默认都为默认不震动 其他都为true

        ZxingConfig config = new ZxingConfig();
        config.setPlayBeep(true);
        config.setShake(true);
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);

        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                String inviteCodeStr = StringConstants.STR_EMPTY;
                Log.e(TAG, "扫描结果为：" + content);
                if (ValidatorUtils.isNotEmptyString(content)) {
                    String[] contentArray = content.split(StringConstants.STR_SIGN_EQUAL);
                    if (contentArray != null && contentArray.length > 0) {
                        inviteCodeStr = contentArray[contentArray.length - 1];
                    }
                }
                //若是登录状态下，跳转到扫码支付
                if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                    getShopName(inviteCodeStr);
                }
                //若是未登录状态下，跳转到推荐注册界面
                else {
                    Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
                    registerIntent.putExtra(Constants.INVITE_CODE, inviteCodeStr);
                    startActivity(registerIntent);
                }
            }
        } else if (requestCode == REQUEST_LOCATION && data != null) {
            LocationListBean locationListBean = (LocationListBean) data.getSerializableExtra("LocationListBean");
            tv_address.setText(locationListBean.getName());
            //Toast.makeText(getActivity(), locationListBean.getCity(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 根据邀请码获取商家信息
     */
    private void getShopName(String inviteCode) {
        //邀请码为空，则不进行请求
        if (ValidatorUtils.isEmptyString(inviteCode)) {
            Toast.makeText(getActivity(), "扫码异常，请重新扫描", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("inviteCode", inviteCode);
        mapParams.put("uid", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        HttpUtils.doGet(Api.GET_STORE_BY_INVIDE_CODE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final ShopDetailBean shopDetailBean = GsonUtil.GsonToBean(result, ShopDetailBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //请求成功，设置商家信息
                        if (Api.STATE_SUCCESS.equals(shopDetailBean.getCode()) && shopDetailBean.getData() != null) {
                            if (shopDetailBean.getData().getUserType() != null) {
                                switch (shopDetailBean.getData().getUserType()) {
                                    case "Ordinary":
                                        Toast.makeText(getActivity(), "普通用户不支持扫码", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "Agent":
                                        Intent scanPayIntent = new Intent(getActivity(), ScanPayActivity.class);
                                        scanPayIntent.putExtra(ShopConstants.KEY_SHOP_ID, shopDetailBean.getData().getId());
                                        scanPayIntent.putExtra(ShopConstants.KEY_SHOP_NAME, shopDetailBean.getData().getBusinessName());
                                        startActivity(scanPayIntent);
                                        break;
                                    case "Organization":
                                        if (!shopDetailBean.getData().isCanApplyMerchant()) {
                                            Toast.makeText(getActivity(), "已有商家入驻申请，请勿重复申请", Toast.LENGTH_SHORT).show();
                                        } else {
                                            startActivity(new Intent(getActivity(), ShopsJoinDataActivity.class));
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                Toast.makeText(getActivity(), "扫码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), shopDetailBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private boolean isMove = true;
    private Handler pHandler = new Handler();
    private Runnable runnable;

    private void moveTranslate() {
        set = new AnimationSet(true);
        translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        translate.setDuration(1000);
        set.addAnimation(translate);
        set.setFillAfter(true);
        ll_move = (LinearLayout) mHeader.findViewById(R.id.ll_move);
        ll_move.setOnClickListener(this);

        //相当于定时器，每隔2s执行一次该线程
        if (headlineList != null && headlineList.size() != 0) {
            if (headlineList.size() == 1) {
                addTv();
            } else if (headlineList.size() == 2) {
                addTv();
                addTv();
            } else {
                addTv();
                addTv();
                addTv();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ll_move.getChildAt(0).offsetTopAndBottom(-ll_move.getChildAt(0).getHeight());
                        ll_move.getChildAt(0).startAnimation(set);

                        ll_move.getChildAt(1).offsetTopAndBottom(-ll_move.getChildAt(1).getHeight() * 2);
                        ll_move.getChildAt(1).startAnimation(set);

                        ll_move.getChildAt(2).offsetTopAndBottom(-ll_move.getChildAt(2).getHeight());
                        ll_move.getChildAt(2).startAnimation(set);
                        addTv();
                        ll_move.removeView(ll_move.getChildAt(0));
                        if (isMove) {
                            pHandler.postDelayed(this, 4000);
                        }                            //相当于定时器，每隔2s执行一次该线程
                    }
                };
                pHandler.postDelayed(runnable, 4000);
            }
        }

    }

    private void addTv() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(YiyeApplication.getContext()).inflate(R.layout.move_layout, null);

        ll_move.addView(linearLayout);
        TextView tv_move_title = (TextView) linearLayout.findViewById(R.id.tv_move_title);
        if (position >= headlineList.size()) {
            position = 0;
        }
        tv_move_title.setText(headlineList.get(position).getTitle());
        position++;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isMove = false;
    }
}