package com.weishi.yiye.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.ProductImageDetailActivity;
import com.weishi.yiye.adapter.ProductImageAdapter;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.BusiTypeImageInfoBean;
import com.weishi.yiye.bean.eventbus.ChangeProductImageEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/29
 * @Description：商品图片
 * @Version:v1.0.0
 *****************************/
@SuppressLint("ValidFragment")
public class ProductImageFragment extends BaseFragment implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = ProductImageFragment.class.getSimpleName();
    protected RefreshLoadMoreLayout mRefreshloadmore;
    protected GridView gvProductImage;
    protected TextView orderEmpty;
    protected Handler mHandler = new Handler();
    private int storeId, photoClassifyId, imgNumber;
    protected ArrayList<String> imgList = new ArrayList<>();

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;
    private ProductImageAdapter adapter;

    public ProductImageFragment() {

    }

    public ProductImageFragment(int storeId, int photoClassifyId, int imgNumber) {
        this.storeId = storeId;
        this.photoClassifyId = photoClassifyId;
        this.imgNumber = imgNumber;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_image;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        gvProductImage = findView(R.id.gv_product_image);
        orderEmpty = findView(R.id.tv_order_empty);

        mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        //设置不能下拉，只能上拉加载
        mRefreshloadmore.setCanRefresh(false);

        adapter = new ProductImageAdapter(getActivity(), R.layout.item_product_image);
        gvProductImage.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getBusiTypeImageInfo(pageNum);
    }

    private void getBusiTypeImageInfo(final int pageNum) {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("storeId", storeId);
        mapParams.put("photoClassifyId", photoClassifyId);
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);

        HttpUtils.doGet(Api.GET_BUSI_TYPE_IMAGE_INFO, mapParams, new Callback() {
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
                final BusiTypeImageInfoBean busiTypeImageInfoBean = GsonUtil.GsonToBean(result, BusiTypeImageInfoBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(busiTypeImageInfoBean.getCode()) &&
                                busiTypeImageInfoBean.getData().getList() != null &&
                                busiTypeImageInfoBean.getData().getList().size() != 0) {

                            for (BusiTypeImageInfoBean.DataBean.ListBean bean : busiTypeImageInfoBean.getData().getList()) {
                                imgList.add(bean.getUrl());
                            }

                            hasNextPage = busiTypeImageInfoBean.getData().isHasNextPage();

                            adapter.setData(imgList);
                            adapter.notifyDataSetChanged();

                            EventBus.getDefault().post(new ChangeProductImageEvent(ChangeProductImageEvent.LOAD_COMPLETE, imgList));

                            gvProductImage.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(getActivity(), ProductImageDetailActivity.class);
                                            intent.putExtra(Constants.IMAGE_LIST, imgList);
                                            intent.putExtra(Constants.CURRENT_IMG_POSITION, i);
                                            intent.putExtra("imgNumber", imgNumber);
                                            startActivity(intent);
                                            getActivity().overridePendingTransition(com.luck.picture.lib.R.anim.a5, 0);
                                        }
                                    }
                            );
                        }
                    }
                });
            }
        });
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
    public void onChangeProductImageEvent(ChangeProductImageEvent changeProductImageEvent) {
        if (changeProductImageEvent.getType() == ChangeProductImageEvent.REQUEST_LOADMORE) {
            onLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getBusiTypeImageInfo(pageNum);
                mRefreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            getBusiTypeImageInfo(pageNum);
        }
        mRefreshloadmore.stopLoadMore();
    }
}
