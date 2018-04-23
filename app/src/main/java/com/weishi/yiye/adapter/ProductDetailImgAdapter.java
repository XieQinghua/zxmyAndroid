package com.weishi.yiye.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weishi.yiye.R;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/3
 * @Description：商品详情图片展示adapter
 * @Version:v1.0.0
 *****************************/

public class ProductDetailImgAdapter extends CommonAdapter<String> {

    public ProductDetailImgAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public ProductDetailImgAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<String> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, String dataBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_img, dataBean);
        SimpleDraweeView sdv = holder.getView(R.id.sdv_img);
        ViewGroup.LayoutParams para = sdv.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20);
        para.height = para.width * 4 / 3;
        sdv.setLayoutParams(para);
    }
}
