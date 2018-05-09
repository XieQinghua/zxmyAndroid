package com.weishi.yiye.activity.nearby;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.ProvinceActivity;
import com.weishi.yiye.adapter.LocationListAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.util.AMapUtil;
import com.weishi.yiye.common.util.LocationUtils;
import com.weishi.yiye.databinding.ActivityLocationBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/9
 * @Description：修改当前位置
 * @Version:v1.0.0
 *****************************/
public class LocationListActivity extends BaseSwipeBackActivity implements OnMarkerClickListener,
        TextWatcher,
        Inputtips.InputtipsListener,
        View.OnClickListener,
        LocationUtils.LocationUtilsinterface, PoiSearch.OnPoiSearchListener {
    private static final String TAG = LocationListActivity.class.getSimpleName();
    public static final int CHOOSE_CITY = 5;
    private ActivityLocationBinding locationBinding;
    private LocationListAdapter adapter;
    private List<LocationListBean> listdata;
    private LocationListBean locationListBean;
    private int resultCode = 0;

    private View mHeader;
    private EditText et_location;
    private LinearLayout ll_location;
    private TextView tv_location, tv_city;

    @Override
    protected void initView() {
        locationListBean = YiyeApplication.locationListBean;

        locationBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        setTitleCenter("修改当前位置");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        mHeader = LayoutInflater.from(LocationListActivity.this).inflate(R.layout.activity_location_header, null);
        et_location = (EditText) mHeader.findViewById(R.id.et_location);
        ll_location = (LinearLayout) mHeader.findViewById(R.id.ll_location);
        tv_city = (TextView) mHeader.findViewById(R.id.tv_city);
        tv_location = (TextView) mHeader.findViewById(R.id.tv_location);
        locationBinding.lvLocationList.addHeaderView(mHeader);

        if (YiyeApplication.locationListBean != null) {
            tv_city.setText(YiyeApplication.locationListBean.getCity());
        } else {
            tv_city.setText("定位中..");
        }
        tv_city.setOnClickListener(this);

        et_location.addTextChangedListener(this);
        adapter = new LocationListAdapter(this, R.layout.item_location);
        locationBinding.lvLocationList.setAdapter(adapter);
        ll_location.setOnClickListener(this);
        locationBinding.lvLocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent();
                //mHeader也算一条，所以下标要-1
                mIntent.putExtra("LocationListBean", listdata.get(i - 1));
                // 设置结果，并进行传送
                setResult(resultCode, mIntent);

                YiyeApplication.locationListBean = listdata.get(i - 1);
                EventBus.getDefault().post(new LocationStateEvent(LocationStateEvent.SUCCESS, listdata.get(i - 1)));

                finish();
            }
        });

        //监听键盘搜索按键
        et_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_location.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(LocationListActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!et_location.getText().toString().trim().equals("")) {
                        doPoiSearchByKeyWords(et_location.getText().toString().trim(), YiyeApplication.locationListBean);
                    } else {
                        Toast.makeText(LocationListActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        listdata = new ArrayList<>();
        if (locationListBean != null) {
            doPoiSearchByLatLon(locationListBean);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String newText = charSequence.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, locationListBean.getCity());
            Inputtips inputTips = new Inputtips(LocationListActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    public void getAmapUtil(String value) {
        if (!AMapUtil.IsEmptyOrNullString(value)) {
            InputtipsQuery inputquery = new InputtipsQuery(value, locationListBean.getCity());
            Inputtips inputTips = new Inputtips(LocationListActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    /**
     * 根据关键词查询附近地址
     *
     * @param keyWords         输入关键词
     * @param locationListBean 定位数据实体
     */
    public void doPoiSearchByKeyWords(String keyWords, LocationListBean locationListBean) {
        startAnim(null);
        PoiSearch.Query query = new PoiSearch.Query(keyWords, "餐饮服务|购物服务|住宿服务|风景名胜|交通设施服务|地名地址信息", locationListBean.getCity());
        query.setPageSize(20);
        PoiSearch search = new PoiSearch(this, query);
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();
    }

    /**
     * 根据经纬度查询附近地址
     *
     * @param locationListBean
     */
    public void doPoiSearchByLatLon(LocationListBean locationListBean) {
        startAnim(null);

        PoiSearch.Query query = new PoiSearch.Query("", "餐饮服务|购物服务|住宿服务|风景名胜|交通设施服务|地名地址信息", "");
        query.setPageSize(20);
        PoiSearch search = new PoiSearch(this, query);
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(Double.valueOf(locationListBean.getLatitude()), Double.valueOf(locationListBean.getLongitude())), 10000));
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        // 正确返回
//        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
//            listdata = new ArrayList<>();
//            for (int i = 0; i < tipList.size(); i++) {
//                LocationListBean bean = new LocationListBean();
//                bean.setName(tipList.get(i).getName());
//                bean.setLatitude(tipList.get(i).getPoint().getLatitude() + "");
//                bean.setLongitude(tipList.get(i).getPoint().getLongitude() + "");
//                listdata.add(bean);
//                Log.e(TAG, "Address=" + tipList.get(i).getAddress());
//            }
//
//            adapter.setData(listdata);
//            adapter.notifyDataSetChanged();
//        } else {
//            ToastUtil.showerror(this, rCode);
//        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                tv_location.setText("定位中..");
                new LocationUtils(this, this);
                break;
            case R.id.tv_city:
                startActivityForResult(new Intent(LocationListActivity.this, ProvinceActivity.class).setFlags(CHOOSE_CITY), CHOOSE_CITY);
                break;
            default:
                break;
        }

    }

    @Override
    public void onLocationSuccess(LocationListBean locationListBean) {
        tv_location.setText(locationListBean.getName());
        Intent mIntent = new Intent();
        mIntent.putExtra("LocationListBean", locationListBean);
        // 设置结果，并进行传送
        this.setResult(resultCode, mIntent);

        finish();
    }

    @Override
    public void onLocationError(String err) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (listdata != null && listdata.size() != 0) {
            listdata.clear();
        }
        if (poiResult.getPois().size() != 0) {
            for (int j = 0; j < poiResult.getPois().size(); j++) {
                Log.e(TAG, "Address=" + poiResult.getPois().get(j).getTitle());
                LocationListBean bean = new LocationListBean();
                bean.setName(poiResult.getPois().get(j).getTitle());
                bean.setLatitude(poiResult.getPois().get(j).getLatLonPoint().getLatitude() + "");
                bean.setLongitude(poiResult.getPois().get(j).getLatLonPoint().getLongitude() + "");
                bean.setCity(poiResult.getPois().get(j).getCityName());
                listdata.add(bean);
            }
            adapter.setData(listdata);
            adapter.notifyDataSetChanged();
        }
        stopAnim();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_CITY:
                if (data != null) {
                    tv_city.setText(data.getStringExtra("city"));
                    //根据城市名发编译经纬度
                    getLatlon(data.getStringExtra("city"));
                }
                break;
            default:
                break;
        }
    }

    private void getLatlon(String cityName) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(LocationListActivity.this);

        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName.trim(), cityName.trim());
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);

        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longitude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode = geocodeAddress.getAdcode();//区域编码

                        Log.e("地理编码", geocodeAddress.getAdcode() + "");
                        Log.e("纬度latitude", latitude + "");
                        Log.e("经度longititude", longitude + "");

                        startAnim(null);

                        PoiSearch.Query query = new PoiSearch.Query("", "餐饮服务|购物服务|住宿服务|风景名胜|交通设施服务|地名地址信息", "");
                        query.setPageSize(20);
                        PoiSearch search = new PoiSearch(LocationListActivity.this, query);
                        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
                        search.setOnPoiSearchListener(LocationListActivity.this);
                        search.searchPOIAsyn();
                    } else {
                        Log.e(TAG, "地址名出错");
                    }
                }
            }
        });
    }
}
