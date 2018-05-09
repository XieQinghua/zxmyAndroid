package com.weishi.yiye.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
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
            SimpleDraweeView sdv = holder.getView(R.id.iv_img);
            setControllerListener(sdv, "file:///"+dataBean.getContent(), ScreenUtils.getScreenWidth());
        } else {
            holder.getView(R.id.tv_word).setVisibility(View.VISIBLE);
            holder.getView(R.id.iv_img).setVisibility(View.GONE);
            holder.setText(R.id.tv_word, dataBean.getContent());
        }
    }

    /**
     * 通过imageWidth的宽度，自动适应高度
     *
     * @param simpleDraweeView view
     * @param imagePath        Uri
     * @param imageWidth       width
     */
    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath, final int imageWidth) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int height = imageInfo.getHeight();
                int width = imageInfo.getWidth();
                layoutParams.width = imageWidth;
                layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
                simpleDraweeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setUri(Uri.parse(imagePath)).build();
        simpleDraweeView.setController(controller);
    }
}
