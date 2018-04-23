package com.weishi.yiye.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.QueryProductBean;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/24
 * @Description：附近模块推荐商品Adapter
 * @Version:v1.0.0
 *****************************/

public class RecNearbyGoodsAdapter extends CommonAdapter<QueryProductBean> {

    public RecNearbyGoodsAdapter(Context context, List<QueryProductBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public RecNearbyGoodsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<QueryProductBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, QueryProductBean recGoodsBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_goods_head, recGoodsBean.getShowImg());
        holder.setText(R.id.tv_goods_name, recGoodsBean.getProductName());
        holder.setText(R.id.tv_score, mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(recGoodsBean.getPrice()));
        holder.setText(R.id.tv_market_price, "市场价" + new DecimalFormat("#0.00").format(recGoodsBean.getMarketPrice()));
        TextView tv = holder.getView(R.id.tv_market_price);
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
