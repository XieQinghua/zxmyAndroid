package com.weishi.yiye.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
 * @Description：区域选择省
 * @Version:v1.0.0
 *****************************/
public class ProvinceActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ProvinceActivity.class.getSimpleName();
    private ActivityAddressLayoutBinding addressLayoutBinding;
    private AddressAdapter adapter;
    private List<AddressBean.Address> addressModels = new ArrayList<AddressBean.Address>();
    private int flags;
    private int RESULT_CODE = 10;

    @Override
    protected void initView() {
        addressLayoutBinding = DataBindingUtil.setContentView(ProvinceActivity.this, R.layout.activity_address_layout);
        setTitleCenter("请选择省会");

        flags = getIntent().getFlags();
        adapter = new AddressAdapter(this, addressModels);
        addressLayoutBinding.addressList.setAdapter(adapter);
        addressLayoutBinding.addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flags == LocationListActivity.CHOOSE_CITY) {
                    Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                    intent.putExtra(Constants.AREA_PROVINCE_CODE, addressModels.get(position).getCode());
                    intent.putExtra(Constants.AREA_PROVINCE_NAME, addressModels.get(position).getName());
                    intent.setFlags(LocationListActivity.CHOOSE_CITY);
                    startActivityForResult(intent, LocationListActivity.CHOOSE_CITY);
                } else {
                    IntentUtil.startActivity(ProvinceActivity.this, CityActivity.class, flags,
                            Constants.AREA_PROVINCE_CODE, addressModels.get(position).getCode(),
                            Constants.AREA_PROVINCE_NAME, addressModels.get(position).getName());
                }
            }
        });

        getProvince();
    }

    @Override
    protected void initData() {

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

    private void getProvince() {
        Map<String, Object> mapParams = new HashMap<>();
        HttpUtils.doGet(Api.GET_PROVINCE_LIST, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
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
    public void finish() {
        EventBus.getDefault().unregister(this);
        super.finish();
    }

    @Subscribe
    public void onEvent(FinishEvents event) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LocationListActivity.CHOOSE_CITY:
                if (data != null) {
                    Intent intent = new Intent();
                    intent.putExtra("city", data.getStringExtra("city"));
                    setResult(RESULT_CODE, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
