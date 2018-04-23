package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.QueryProductBean;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：推荐商品Adapter
 * @Version:v1.0.0
 *****************************/

public class QueryGoodsAdapter extends CommonAdapter<QueryProductBean> {

    public QueryGoodsAdapter(Context context, List<QueryProductBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public QueryGoodsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<QueryProductBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, QueryProductBean queryProductBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_goods_head, queryProductBean.getShowImg());
        holder.setText(R.id.tv_goods_name, queryProductBean.getProductName());
        holder.setText(R.id.tv_service_time, "使用时间：" + queryProductBean.getUseTime());
        holder.setText(R.id.tv_score, mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(queryProductBean.getPrice()));
        holder.setText(R.id.tv_sold_out, "|  已售" + queryProductBean.getSellNum() + "份");
    }
}
