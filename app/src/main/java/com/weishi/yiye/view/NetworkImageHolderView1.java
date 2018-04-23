package com.weishi.yiye.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weishi.yiye.R;
import com.weishi.yiye.bean.SliderBean;


/**
 * 网络图片加载例子
 */
public class NetworkImageHolderView1 implements Holder<SliderBean> {

    private Activity activity;
    private SimpleDraweeView sdv;

    /**
     * @param activity
     */
    public NetworkImageHolderView1(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public View createView(Context context) {
        // 你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        View tempView = activity.getLayoutInflater().inflate(R.layout.include_banner_image, null);
        sdv = (SimpleDraweeView) tempView.findViewById(R.id.sdv);
        return tempView;
    }

    @Override
    public void UpdateUI(Context context, final int position, SliderBean data) {
        try {
            if (data != null && !TextUtils.isEmpty(data.getAdImg())) {
                Uri uri = Uri.parse(data.getAdImg());
                sdv.setImageURI(uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
