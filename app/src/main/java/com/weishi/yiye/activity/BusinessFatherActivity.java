package com.weishi.yiye.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.ShopTypeAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.ShopTypeBean;
import com.weishi.yiye.bean.eventbus.FinishEvents;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityAddressLayoutBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
 * @Date：2018/2/5
 * @Description：第一级分类选择
 * @Version:v1.0.0
 *****************************/
public class BusinessFatherActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ShopDetailActivity.class.getSimpleName();
    private ActivityAddressLayoutBinding addressLayoutBinding;
    private ShopTypeAdapter adapter;
    private List<ShopTypeBean.ShopType> shopTypes = new ArrayList<ShopTypeBean.ShopType>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        addressLayoutBinding = DataBindingUtil.setContentView(BusinessFatherActivity.this, R.layout.activity_address_layout);
        setTitleCenter("请选择商家分类");
        adapter = new ShopTypeAdapter(this, shopTypes);
        addressLayoutBinding.addressList.setAdapter(adapter);
        addressLayoutBinding.addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent shopClassIntent = new Intent(BusinessFatherActivity.this, BusinessParentActivity.class);
                shopClassIntent.putExtra(ShopConstants.BUSINESS_FATHER_TYPE, shopTypes.get(position).getSortId());
                shopClassIntent.putExtra(ShopConstants.BUSINESS_FATHER_TYPE_NAME, shopTypes.get(position).getTypeName());
                startActivity(shopClassIntent);
            }
        });

        getShopType();
    }

    @Override
    protected void initData() {

    }

    private void getShopType() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("sortId", 0);
        HttpUtils.doGet(Api.GET_PROPERTY_SORT, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final ShopTypeBean shopTypeBean = GsonUtil.GsonToBean(result, ShopTypeBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (shopTypeBean != null && shopTypeBean.getData() != null && shopTypeBean.getData().size() > 0) {
                            shopTypes = shopTypeBean.getData();
                            adapter.setData(shopTypes);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void finish() {
        EventBus.getDefault().unregister(this);
        super.finish();
    }

    @Subscribe
    public void onEvent(FinishEvents event) {
        finish();
    }
}
