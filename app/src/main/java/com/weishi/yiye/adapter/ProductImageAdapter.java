package com.weishi.yiye.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.weishi.yiye.R;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/29
 * @Description：商品图片GridView适配器
 * @Version:v1.0.0
 *****************************/
public class ProductImageAdapter extends CommonAdapter<String> {

    public ProductImageAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public ProductImageAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<String> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, String imgUrl, int position) {
        RelativeLayout.LayoutParams para = (RelativeLayout.LayoutParams) holder.getView(R.id.sdv_product_img).getLayoutParams();
        para.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40)) * 1 / 3;
        para.height = para.width;
        holder.getView(R.id.sdv_product_img).setLayoutParams(para);
        holder.setDraweeViewImage(R.id.sdv_product_img, imgUrl);
    }
}