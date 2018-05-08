package com.weishi.yiye.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.view.WindowManager;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.GoodsImagesAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.ImageTextBean;
import com.weishi.yiye.databinding.ActivityGoodsInsideDetailBinding;

import java.util.ArrayList;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：图文详情
 * @Version:v1.0.0
 *****************************/
public class GoodsInsideDetailActivity extends BaseSwipeBackActivity {
    private static final String TAG = GoodsInsideDetailActivity.class.getSimpleName();
    private ActivityGoodsInsideDetailBinding goodsInsideDetailBinding;
    private GoodsImagesAdapter goodsImagesAdapter;

    private ArrayList<ImageTextBean> imgPathList = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        goodsInsideDetailBinding = DataBindingUtil.setContentView(GoodsInsideDetailActivity.this, R.layout.activity_goods_inside_detail);
        imgPathList = (ArrayList<ImageTextBean>) getIntent().getSerializableExtra("imgPathList");
        setTitleCenter("图文详情");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        goodsImagesAdapter = new GoodsImagesAdapter(GoodsInsideDetailActivity.this, R.layout.item_goods_image);
        goodsInsideDetailBinding.lvGoodsImages.setAdapter(goodsImagesAdapter);
        goodsImagesAdapter.setData(imgPathList);
        goodsImagesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initData() {

    }
}
