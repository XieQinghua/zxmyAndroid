package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/3/3
 * @Description：商品图片展示adapter
 * @Version:v1.0.0
 *****************************/

public class GoodsImagesAdapter extends CommonAdapter<String> {

    public GoodsImagesAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public GoodsImagesAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<String> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, String dataBean, int position) {
        holder.setImageViewLocalImage(R.id.iv_img, dataBean);
    }
}
