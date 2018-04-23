package com.weishi.yiye.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.ShopTypeBean;

import java.util.List;

/**
 * Created by zym on 2018/1/11.
 */

public class LabelAdapter extends CommonAdapter<ShopTypeBean.ShopType> {

    public LabelAdapter(Context context, List<ShopTypeBean.ShopType> shopTypes, int layoutId) {
        super(context, shopTypes, layoutId);
    }

    public LabelAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void convert(ViewHolder holder, ShopTypeBean.ShopType nearbyTopBean, int position) {
        holder.setDraweeViewImage(R.id.nearby_top_img, nearbyTopBean.getIcon());
        holder.setText(R.id.nearby_top_text, nearbyTopBean.getTypeName());
    }
}
