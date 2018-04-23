package com.weishi.yiye.adapter;

import android.app.Activity;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.HeadlineBean;
import com.weishi.yiye.common.util.DateUtils;

import java.util.List;

/**
 * Created by shejun on 2018/3/28.
 */

public class HeadlineAdapter extends CommonAdapter<HeadlineBean.DataBean.ListBean> {
    private Activity context;

    public HeadlineAdapter(Activity context, List<HeadlineBean.DataBean.ListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public HeadlineAdapter(Activity context, int layoutId) {
        super(context, layoutId);
        this.context = context;
    }

    @Override
    public void setData(List<HeadlineBean.DataBean.ListBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, final HeadlineBean.DataBean.ListBean dataBean, int position) {
        holder.setDraweeViewImage(R.id.sdv, dataBean.getShowImg());
        holder.setText(R.id.tv_title, dataBean.getTitle());
        holder.setText(R.id.tv_content, dataBean.getContent());
        holder.setText(R.id.tv_time, DateUtils.getTimeStr(dataBean.getCreatetime()));
    }
}
