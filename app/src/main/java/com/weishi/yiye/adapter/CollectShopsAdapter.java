package com.weishi.yiye.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.weishi.yiye.R;
import com.weishi.yiye.activity.ShopDetailActivity;
import com.weishi.yiye.bean.CollectShopsBean;
import com.weishi.yiye.common.ShopConstants;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/10
 * @Description：收藏店铺Adapter
 * @Version:v1.0.0
 *****************************/

public class CollectShopsAdapter extends CommonAdapter<CollectShopsBean.DataBean.ListBean> {

    public CollectShopsAdapter(Context context, List<CollectShopsBean.DataBean.ListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public CollectShopsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<CollectShopsBean.DataBean.ListBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, final CollectShopsBean.DataBean.ListBean dataBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_shops_head, dataBean.getStoreLogo());
        holder.setText(R.id.tv_shops_name, dataBean.getStoreName());
        holder.setText(R.id.tv_address, dataBean.getAddress());
        holder.setText(R.id.tv_distance, dataBean.getDistance());
        holder.setStar(R.id.rb, dataBean.getStarLevel());
        holder.setText(R.id.tv_grade, dataBean.getStarLevel() + "分");

        holder.getView(R.id.btn_enter_shops).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShopDetailActivity.class);
                intent.putExtra(ShopConstants.KEY_SHOP_ID, dataBean.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
