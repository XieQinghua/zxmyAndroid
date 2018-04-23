package com.weishi.yiye.adapter;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.bean.CollectGoodsBean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/10
 * @Description：收藏商品Adapter
 * @Version:v1.0.0
 *****************************/

public class CollectGoodsAdapter extends CommonAdapter<CollectGoodsBean.DataBean.ListBean> {

    public CollectGoodsAdapter(Context context, List<CollectGoodsBean.DataBean.ListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public CollectGoodsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<CollectGoodsBean.DataBean.ListBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, CollectGoodsBean.DataBean.ListBean dataBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_goods_head, dataBean.getShowImg());
        if (dataBean.getStatus() == 0) {
            holder.getView(R.id.tv_remove).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_remove).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_goods_name, dataBean.getProductName());
        holder.setText(R.id.tv_service_time, "使用时间：" +
                TimeUtils.millis2String(dataBean.getCreatetime(), new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())) + "~" +
                TimeUtils.millis2String(dataBean.getValidTime(), new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())));
        holder.setText(R.id.tv_score, mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(dataBean.getPrice()));
    }
}
