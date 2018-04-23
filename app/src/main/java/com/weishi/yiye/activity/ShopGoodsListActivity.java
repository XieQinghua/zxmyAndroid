package com.weishi.yiye.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.QueryGoodsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseActivity;
import com.weishi.yiye.bean.QueryProductBean;
import com.weishi.yiye.bean.ShopGoodsQueryBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.GoodsConstants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityShopGoodsListBinding;

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

public class ShopGoodsListActivity extends BaseActivity implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = ShopGoodsListActivity.class.getSimpleName();
    private ActivityShopGoodsListBinding shopGoodsListBinding;
    private QueryGoodsAdapter goodsAdapter;
    private List<QueryProductBean> productList = new ArrayList<QueryProductBean>();
    protected Handler mHandler = new Handler();
    private int shopId;
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected void initView() {
        //当前界面的店铺
        shopId = getIntent().getIntExtra(ShopConstants.KEY_SHOP_ID, 0);
        shopGoodsListBinding = DataBindingUtil.setContentView(ShopGoodsListActivity.this, R.layout.activity_shop_goods_list);
        shopGoodsListBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        setTitleCenter("全部商品");

        /**商品列表*/
        goodsAdapter = new QueryGoodsAdapter(this, R.layout.item_rec_goods);
        shopGoodsListBinding.lvHotGoods.setAdapter(goodsAdapter);
        shopGoodsListBinding.lvHotGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShopGoodsListActivity.this, GoodsDetailActivity.class);
                intent.putExtra(GoodsConstants.KEY_GOODS_ID, productList.get(i).getId());
                ShopGoodsListActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        initGoodsData(1);
    }

    private void initGoodsData(final int pageNum) {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<String, Object>();
        if (YiyeApplication.locationListBean != null) {
            mapParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
            mapParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
        } else {
            mapParams.put("userLat", 0);
            mapParams.put("userLng", 0);
        }
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);
        mapParams.put("storeId", shopId);

        HttpUtils.doGet(Api.GET_PRODUCTINFO_BY_STORE_ID, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();
                String result = response.body().string();
                Log.e(TAG, result);

                final ShopGoodsQueryBean shopGoodsQueryBean = GsonUtil.GsonToBean(result, ShopGoodsQueryBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(shopGoodsQueryBean.getCode()) && shopGoodsQueryBean.getData() != null) {
                            if (pageNum == 1) {
                                if (shopGoodsQueryBean != null &&
                                        shopGoodsQueryBean.getData() != null &&
                                        shopGoodsQueryBean.getData().getList() != null &&
                                        shopGoodsQueryBean.getData().getList().size() != 0) {
                                    productList = shopGoodsQueryBean.getData().getList();
                                }
                            } else {
                                if (shopGoodsQueryBean != null &&
                                        shopGoodsQueryBean.getData() != null &&
                                        shopGoodsQueryBean.getData().getList() != null &&
                                        shopGoodsQueryBean.getData().getList().size() != 0) {
                                    productList.addAll(shopGoodsQueryBean.getData().getList());
                                }
                            }

                            hasNextPage = shopGoodsQueryBean.getData().isHasNextPage();

                            goodsAdapter.setData(productList);
                            goodsAdapter.notifyDataSetChanged();
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
                shopGoodsListBinding.refreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initGoodsData(pageNum);
        } else {
            Toast.makeText(ShopGoodsListActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shopGoodsListBinding.refreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
