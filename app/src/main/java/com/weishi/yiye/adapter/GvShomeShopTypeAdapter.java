package com.weishi.yiye.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.common.util.UIUtil;
import com.weishi.yiye.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shejun on 2018/3/29.
 * 首页次级页面第三季列表
 */

public class GvShomeShopTypeAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty> data = new ArrayList<>();
    private List<ThreeShopTypeBean.DataBean.TwoProperty> dataTwo = new ArrayList<>();
    private GradientDrawable gd;
    private int type = 0;//0三级数据，1，二级数据

    public GvShomeShopTypeAdapter(Activity activity) {
        this.activity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    public void setData(List<ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty> data) {
        this.data = data;
        this.type = 0;
    }

    public void setDataTwo(List<ThreeShopTypeBean.DataBean.TwoProperty> data) {
        this.dataTwo = data;
        this.type = 1;
    }

    @Override
    public int getCount() {
        if (type == 0) {
            if (data == null || data.size() == 0) {
                return 0;
            } else {
                return data.size();
            }
        } else {
            if (dataTwo == null || dataTwo.size() == 0) {
                return 0;
            } else {
                return dataTwo.size();
            }
        }
    }

    @Override
    public Object getItem(int position) {
        if (type == 0) {
            if (data != null) {
                return data.get(position);
            }
        } else {
            if (dataTwo != null) {
                return dataTwo.get(position);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gv_shop_type, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            int startColor;
            int endColor;
            if(type==0){
                ThreeShopTypeBean.DataBean.TwoProperty.ThreeProperty model = data.get(position);
                holder.tv_title.setText(model.typeName);
                holder.tv_title.setTextColor(Color.parseColor("#"+model.lineColors));
                String spStr[] = model.ombre.split(",");
                startColor= Color.parseColor("#80"+spStr[0]);
                endColor= Color.parseColor("#80"+spStr[1]);
               // Log.i("sgewfwegweg",startColor+"---"+endColor);
            }else{
                ThreeShopTypeBean.DataBean.TwoProperty model = dataTwo.get(position);
                holder.tv_title.setTextColor(Color.parseColor("#"+model.lineColors));
                holder.tv_title.setText(model.typeName);
                String spStr[] = model.ombre.split(",");
                startColor= Color.parseColor("#80"+spStr[0]);
                endColor= Color.parseColor("#80"+spStr[1]);
            }
// 创建渐变的shape drawable

            int colors[] = {startColor, endColor};//分别为开始颜色，中间夜色，结束颜色
            gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            gd.setCornerRadius(UIUtil.dip2px(activity, 20));
            //gd.setStroke(strokeWidth, strokeColor);
            gd.setShape(GradientDrawable.RECTANGLE);
            //gd.setGradientType(GradientDrawable.RECTANGLE);
            holder.tv_title.setBackgroundDrawable(gd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_title;
    }
}
