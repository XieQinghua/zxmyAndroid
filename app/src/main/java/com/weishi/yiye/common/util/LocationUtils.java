package com.weishi.yiye.common.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zym on 2018/1/16.
 * 定位封装
 */

public class LocationUtils {
    /**
     * 定位
     */
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    /**
     * 定位实体类
     */
    private LocationListBean locationListBean;
    Context context;

    /**
     * 定位回调数据接口
     *
     * @param context
     */
    private LocationUtilsinterface locationUtilsinterface;

    public LocationUtils(Context context, LocationUtilsinterface locationUtilsinterface) {
        this.context = context;
        this.locationUtilsinterface = locationUtilsinterface;
        //初始化定位
        initLocation();
        startLocation();
    }

    /**
     * 初始化定位
     *
     * @author zym
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(context.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author zym
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                locationListBean = new LocationListBean();
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    locationListBean.setLongitude(location.getLongitude() + "");
                    locationListBean.setLatitude(location.getLatitude() + "");
                    locationListBean.setName(location.getPoiName());
                    locationListBean.setCity(location.getCity());
                    locationUtilsinterface.onLocationSuccess(locationListBean);

                    YiyeApplication.locationListBean = locationListBean;
                    EventBus.getDefault().post(new LocationStateEvent(LocationStateEvent.SUCCESS, locationListBean));

                    stopLocation();
                    destroyLocation();
                    //定位完成的时间
                    sb.append("定位时间: " + AMapUtil.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                    locationUtilsinterface.onLocationError(location.getLocationDetail());

                    EventBus.getDefault().post(new LocationStateEvent(LocationStateEvent.FAIL, null));

                    stopLocation();
                    destroyLocation();
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
//                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                sb.append("回调时间: " + AMapUtil.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
//                tvResult.setText(result);
            } else {
                stopLocation();
                destroyLocation();
                locationUtilsinterface.onLocationError("定位失败");

                EventBus.getDefault().post(new LocationStateEvent(LocationStateEvent.FAIL, null));
            }
        }
    };

    /**
     * 开始定位
     *
     * @author zym
     * @since 2.8.0
     */
    private void startLocation() {

        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author zym
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    public interface LocationUtilsinterface {
        void onLocationSuccess(LocationListBean locationListBean);

        void onLocationError(String err);
    }
}
