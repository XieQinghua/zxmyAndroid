package com.weishi.yiye.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.AddressAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.AddressBean;
import com.weishi.yiye.bean.SelectAddressBean;
import com.weishi.yiye.bean.eventbus.FinishEvents;
import com.weishi.yiye.bean.eventbus.SelectAddressEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
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
 * @Author：yezhouyong
 * @Date：2018/2/5
 * @Description：区域选择区
 * @Version:v1.0.0
 *****************************/
public class AreaActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = AreaActivity.class.getSimpleName();
    private ActivityAddressLayoutBinding addressLayoutBinding;
    private AddressAdapter adapter;
    private List<AddressBean.Address> addressModels = new ArrayList<>();
    private String cityCode, cityName, provinceCode, provinceName;
    private int flags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressLayoutBinding = DataBindingUtil.setContentView(AreaActivity.this, R.layout.activity_address_layout);
        setTitleCenter("请选择区");

        cityCode = getIntent().getExtras().getString(Constants.AREA_CITY_CODE);
        cityName = getIntent().getExtras().getString(Constants.AREA_CITY_NAME);
        provinceCode = getIntent().getExtras().getString("provinceCode");
        provinceName = getIntent().getExtras().getString("provinceName");
        flags = getIntent().getFlags();
        adapter = new AddressAdapter(AreaActivity.this, addressModels);
        addressLayoutBinding.addressList.setAdapter(adapter);
        getArea();

        addressLayoutBinding.addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选择完地区后发送事件
                SelectAddressBean model = new SelectAddressBean();
                model.setProvince(provinceName);
                model.setProvinceCode(provinceCode);
                model.setCity(cityName);
                model.setCityCode(cityCode);
                model.setArea(addressModels.get(position).getName());
                model.setAreaCode(addressModels.get(position).getCode());

                SelectAddressEvent sEvent = new SelectAddressEvent();
                sEvent.setModel(model);
                EventBus.getDefault().post(sEvent);
                EventBus.getDefault().post(new FinishEvents());
                finish();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void getArea() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("parentCode", cityCode);
        HttpUtils.doGet(Api.GET_PARENT_CODE_LIST, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                //验证合法格式json
                final AddressBean addressBean = GsonUtil.GsonToBean(result, AddressBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != addressBean && null != addressBean.getData()) {
                            addressModels = addressBean.getData();
                            adapter.setData(addressModels);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
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
    public void finish() {
        EventBus.getDefault().unregister(this);
        super.finish();
    }

    @Subscribe
    public void onEvent(FinishEvents event) {
        finish();
    }
}
