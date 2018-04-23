package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/16
 * @Description：搜索条件
 * @Version:v1.0.0
 *****************************/

public class ConditionAdapter extends CommonAdapter<String> {
    public ConditionAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public ConditionAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<String> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, String condition, int position) {
        holder.setText(R.id.tv_condition, condition);
    }
}

