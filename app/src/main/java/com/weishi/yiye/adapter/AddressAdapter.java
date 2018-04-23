package com.weishi.yiye.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */

public class AddressAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<AddressBean.Address> data = new ArrayList<>();

    public AddressAdapter(Activity activity, List<AddressBean.Address> data) {
        this.activity = activity;
        this.data = data;
        mInflater = LayoutInflater.from(activity);
    }
    public void setData(List<AddressBean.Address> data) {
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
            convertView = mInflater.inflate(R.layout.item_address, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            AddressBean.Address model = data.get(position);
            holder.name.setText(model.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView name;
    }
}
