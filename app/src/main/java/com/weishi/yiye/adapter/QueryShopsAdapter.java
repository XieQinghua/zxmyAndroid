package com.weishi.yiye.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weishi.yiye.R;
import com.weishi.yiye.activity.GoodsDetailActivity;
import com.weishi.yiye.activity.ShopDetailActivity;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.common.GoodsConstants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.view.MapPopupWindow;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/24
 * @Description：推荐商家Adapter
 * @Version:v1.0.0
 *****************************/

public class QueryShopsAdapter extends CommonAdapter<QueryShopBean> {

    public QueryShopsAdapter(Context context, List<QueryShopBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public QueryShopsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<QueryShopBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(final ViewHolder holder, final QueryShopBean recShopsBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_shops_head, recShopsBean.getStoreLogo());
        holder.setText(R.id.tv_shops_name, recShopsBean.getStoreName());
        holder.setText(R.id.tv_address, recShopsBean.getAddress());
        holder.setText(R.id.tv_distance, new DecimalFormat("#0.0").format(recShopsBean.getDistance() / 1000) + "km");
        holder.setStar(R.id.rb, recShopsBean.getStarLevel());
        holder.setText(R.id.tv_grade, new DecimalFormat("#0.0").format(recShopsBean.getStarLevel()) + "分");

        holder.getView(R.id.btn_enter_shops).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shopDetailIntent = new Intent(mContext, ShopDetailActivity.class);
                shopDetailIntent.putExtra(ShopConstants.KEY_SHOP_ID, recShopsBean.getId());
                mContext.startActivity(shopDetailIntent);
            }
        });

        holder.getView(R.id.tv_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转地图导航
                new MapPopupWindow(mContext,
                        recShopsBean.getLat() + "",
                        recShopsBean.getLng() + "",
                        recShopsBean.getAddress(),
                        holder.getView(R.id.tv_address));
            }
        });

        ListView lv_rec_goods = holder.getView(R.id.lv_rec_goods);
        QueryGoodsAdapter mRecGoodsAdapter = new QueryGoodsAdapter(mContext, R.layout.item_rec_goods);
        mRecGoodsAdapter.setData(recShopsBean.getProductList());
        lv_rec_goods.setAdapter(mRecGoodsAdapter);
        lv_rec_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra(GoodsConstants.KEY_GOODS_ID, recShopsBean.getProductList().get(i).getId());
                mContext.startActivity(intent);
            }
        });
    }
}
