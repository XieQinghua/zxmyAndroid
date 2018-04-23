package com.weishi.yiye.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.home.ThreeHomeActivity;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.common.AdapterInterface;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shejun on 2018/3/29.
 */

public class SHomeShopTypeAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<ThreeShopTypeBean.DataBean.TwoProperty> data = new ArrayList<>();
    private GradientDrawable gd;
    private AdapterInterface.AdapterCallBack adapterCallBack;

    public SHomeShopTypeAdapter(Activity activity, List<ThreeShopTypeBean.DataBean.TwoProperty> data, AdapterInterface.AdapterCallBack adapterCallBack) {
        this.activity = activity;
        this.data = data;
        this.adapterCallBack = adapterCallBack;
        mInflater = LayoutInflater.from(activity);
    }

    public void setData(List<ThreeShopTypeBean.DataBean.TwoProperty> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data == null || data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (data != null) {
            return data.get(position);
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
            convertView = mInflater.inflate(R.layout.item_shop_type, null);
            holder = new ViewHolder(convertView);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.sdv_shopType1 = (SimpleDraweeView) convertView.findViewById(R.id.sdv_shopType1);
            holder.v_line = convertView.findViewById(R.id.v_line);
            holder.ll_shop_type = (LinearLayout) convertView.findViewById(R.id.ll_shop_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            ThreeShopTypeBean.DataBean.TwoProperty model = data.get(position);
            if (model.property != null && model.property.size() != 0) {
                holder.adapter.setData(model.property);
                holder.adapter.notifyDataSetChanged();
                holder.ll_shop_type.setVisibility(View.VISIBLE);
                holder.v_line.setVisibility(View.VISIBLE);
                holder.gridView.setNumColumns(2);
                holder.v_line.setBackgroundColor(Color.parseColor("#" + model.lineColors));
                holder.tv_title.setText(model.typeName);
                holder.sdv_shopType1.setImageURI(model.icon);
            } else {
                holder.gridView.setNumColumns(4);
                holder.adapter.setDataTwo(data);
                holder.adapter.notifyDataSetChanged();
                holder.ll_shop_type.setVisibility(View.GONE);
                holder.v_line.setVisibility(View.GONE);
            }
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent tempIntent = new Intent(activity, ThreeHomeActivity.class);
                    tempIntent.putExtra(ShopConstants.KEY_SHOP_TYPE_PARENT_ID, data.get(position).property.get(i).sortId);
                    activity.startActivity(tempIntent);
                }
            });
            holder.ll_shop_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapterCallBack != null) {
                        adapterCallBack.callBack(position, "");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        View v_line;
        LinearLayout ll_shop_type;
        TextView tv_title;
        SimpleDraweeView sdv_shopType1;
        MyGridView gridView;
        GvShomeShopTypeAdapter adapter;

        public ViewHolder(View view) {

            gridView = (MyGridView) view.findViewById(R.id.gv_shop_type);
            //将adapter定义在此，优化滑动效果(核心)
            adapter = new GvShomeShopTypeAdapter(activity);
            //在此设置适配器，数据源在getView中添加(核心)
            gridView.setAdapter(adapter);

            view.setTag(this);
        }
    }
}
