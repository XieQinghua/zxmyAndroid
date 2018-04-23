package com.weishi.yiye.bean.eventbus;

import com.weishi.yiye.bean.LocationListBean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/16
 * @Description：eventbus定位状态事件
 * @Version:v1.0.0
 *****************************/

public class LocationStateEvent {
    // 定位成功
    public static final int SUCCESS = 0;
    // 定位失败
    public static final int FAIL = 1;

    private int state;

    public LocationStateEvent(int state, LocationListBean locationListBean) {
        this.state = state;
        this.locationListBean = locationListBean;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private LocationListBean locationListBean;

    public LocationListBean getLocationListBean() {
        return locationListBean;
    }

    public void setLocationListBean(LocationListBean locationListBean) {
        this.locationListBean = locationListBean;
    }
}
