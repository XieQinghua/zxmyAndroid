package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.LocationListBean;

import java.util.List;

/**
 * Created by zym on 2018/1/15.
 */

public class LocationListAdapter extends CommonAdapter<LocationListBean> {
    public LocationListAdapter(Context context, List<LocationListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public LocationListAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<LocationListBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, LocationListBean locationListBean, int position) {
        holder.setText(R.id.tv_location, locationListBean.getName());
    }
}
