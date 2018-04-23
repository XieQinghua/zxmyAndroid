package com.weishi.yiye.fragment.nearby;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.home.ThreeHomeActivity;
import com.weishi.yiye.adapter.RecNearbyShopsAdapter;
import com.weishi.yiye.adapter.SHomeShopTypeAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.ShopQueryBean;
import com.weishi.yiye.bean.ShopTypeBean;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.AdapterInterface;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/24
 * @Description：附近分类
 * @Version:v1.0.0
 *****************************/
@SuppressLint("ValidFragment")
public class NearbyClassFragment extends BaseFragment implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = NearbyClassFragment.class.getSimpleName();

    protected int type;
    protected ArrayList<ThreeShopTypeBean.DataBean.TwoProperty> shopTypeList = new ArrayList<>();

    private SHomeShopTypeAdapter sHomeShopTypeAdapter;
    private MyListView lv_shop_type;
    private List<ShopTypeBean.ShopType> shopTypes = new ArrayList<ShopTypeBean.ShopType>();

    private View mHeader;
    private ListView lv_rec_shops;
    private RecNearbyShopsAdapter mRecNearbyShopsAdapter;
    private List<QueryShopBean> shopsDatas = new ArrayList<QueryShopBean>();

    protected Handler mHandler = new Handler();

    private RefreshLoadMoreLayout mRefreshloadmore;
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    public NearbyClassFragment() {

    }

    public NearbyClassFragment(int type, ArrayList<ThreeShopTypeBean.DataBean.TwoProperty> shopTypeList) {
        this.type = type;
        this.shopTypeList = shopTypeList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        mRefreshloadmore.setCanRefresh(false);

        lv_rec_shops = findView(R.id.lv_rec_shops);

        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nearby_main_header, null);

        //新分类
        lv_shop_type = (MyListView) mHeader.findViewById(R.id.lv_shop_type);
        sHomeShopTypeAdapter = new SHomeShopTypeAdapter(getActivity(), shopTypeList, new AdapterInterface.AdapterCallBack() {
            @Override
            public void callBack(int position, String opertype) {
                Intent tempIntent = new Intent(getActivity(), ThreeHomeActivity.class);
                tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, shopTypeList.get(position).sortId);
                tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_LIST, (Serializable) shopTypeList.get(position).property);
                startActivity(tempIntent);
            }
        });
        lv_shop_type.setAdapter(sHomeShopTypeAdapter);

        lv_rec_shops.addHeaderView(mHeader);

        /**附近商家*/
        shopsDatas = new ArrayList<>();
        mRecNearbyShopsAdapter = new RecNearbyShopsAdapter(getActivity(), R.layout.item_rec_nearby_shops);
        lv_rec_shops.setAdapter(mRecNearbyShopsAdapter);
    }

    @Override
    protected void initData() {
        //初始化店铺类型的数据
//        initShopTypeData();
        initShopData(1);
    }

//    private void initShopTypeData() {
//        Map<String, Object> mapParams = new HashMap<>();
//        mapParams.put("parentId", type);
//        HttpUtils.doGet(Api.GET_BUSI_TYPE, mapParams, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                Log.e(TAG, result);
//
//                final ShopTypeBean shopTypeBean = GsonUtil.GsonToBean(result, ShopTypeBean.class);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        shopTypes.clear();
//                        if (shopTypeBean != null && shopTypeBean.getData() != null && shopTypeBean.getData().size() > 0) {
//                            shopTypes = shopTypeBean.getData();
//                        }
//
//                        adapter.setData(shopTypes);
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//    }

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
            jsonParams.put("busiType", type);
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
                        if (Api.STATE_SUCCESS.equals(homeNearShopBean.getCode()) && homeNearShopBean.getData() != null) {
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

                            mRecNearbyShopsAdapter.setData(shopsDatas);
                            mRecNearbyShopsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationState(LocationStateEvent locationStateEvent) {
        switch (locationStateEvent.getState()) {
            case LocationStateEvent.SUCCESS:
                initShopData(1);
                break;
            case LocationStateEvent.FAIL:
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

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
}