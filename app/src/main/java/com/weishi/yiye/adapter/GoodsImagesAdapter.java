package com.weishi.yiye.adapter;

import android.content.Context;
import android.view.View;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.ImageTextBean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/3/3
 * @Description：商品图文详情混排展示adapter
 * @Version:v1.0.0
 *****************************/

public class GoodsImagesAdapter extends CommonAdapter<ImageTextBean> {

    public GoodsImagesAdapter(Context context, List<ImageTextBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public GoodsImagesAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<ImageTextBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, ImageTextBean dataBean, int position) {
        if (dataBean.getContentType() == 0) {
            holder.getView(R.id.tv_word).setVisibility(View.GONE);
            holder.getView(R.id.iv_img).setVisibility(View.VISIBLE);
            holder.setImageViewLocalImage(R.id.iv_img, dataBean.getContent());
        } else {
            holder.getView(R.id.tv_word).setVisibility(View.VISIBLE);
            holder.getView(R.id.iv_img).setVisibility(View.GONE);
            holder.setText(R.id.tv_word, dataBean.getContent());
        }
    }
}
