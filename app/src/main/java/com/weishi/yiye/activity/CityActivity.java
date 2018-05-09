package com.weishi.yiye.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.activity.nearby.LocationListActivity;
import com.weishi.yiye.adapter.AddressAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.AddressBean;
import com.weishi.yiye.bean.eventbus.FinishEvents;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.IntentUtil;
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
 * @Description：区域选择市
 * @Version:v1.0.0
 *****************************/
public class CityActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = CityActivity.class.getSimpleName();
    private ActivityAddressLayoutBinding addressLayoutBinding;
    private AddressAdapter adapter;
    private List<AddressBean.Address> addressModels = new ArrayList<>();
    private int flags;
    private String provinceCode, provinceName;
    private int RESULT_CODE = 10;

    @Override
    protected void initView() {
        addressLayoutBinding = DataBindingUtil.setContentView(CityActivity.this, R.layout.activity_address_layout);
        setTitleCenter("请选择市");

        flags = getIntent().getFlags();
        provinceCode = getIntent().getExtras().getString(Constants.AREA_PROVINCE_CODE);
        provinceName = getIntent().getExtras().getString(Constants.AREA_PROVINCE_NAME);
        adapter = new AddressAdapter(CityActivity.this, addressModels);
        addressLayoutBinding.addressList.setAdapter(adapter);
        getCity();

        addressLayoutBinding.addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flags == LocationListActivity.CHOOSE_CITY) {
                    Intent intent = new Intent();
                    intent.putExtra("city", addressModels.get(position).getName());
                    setResult(RESULT_CODE, intent);
                    finish();
                } else {
                    IntentUtil.startActivity(CityActivity.this, AreaActivity.class, flags, Constants.AREA_PROVINCE_CODE, provinceCode, Constants.AREA_PROVINCE_NAME, provinceName, Constants.AREA_CITY_CODE, addressModels.get(position).getCode(), Constants.AREA_CITY_NAME, addressModels.get(position).getName());
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void getCity() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("parentCode", provinceCode);
        HttpUtils.doGet(Api.GET_PARENT_CODE_LIST, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

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
