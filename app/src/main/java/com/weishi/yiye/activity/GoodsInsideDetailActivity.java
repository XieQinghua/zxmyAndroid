package com.weishi.yiye.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.GoodsImagesAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.CropImageUtils;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.ValidatorUtils;
import com.weishi.yiye.databinding.ActivityGoodsInsideDetailBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private String productDetail = StringConstants.STR_EMPTY;
    private ArrayList<String> goodDetailImages = null;
    private GoodsImagesAdapter goodsImagesAdapter;

    private int downPosition = 0;
    private ArrayList<String> imgPathList = new ArrayList<>();

    private CheckPermission checkPermission;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        goodsInsideDetailBinding = DataBindingUtil.setContentView(GoodsInsideDetailActivity.this, R.layout.activity_goods_inside_detail);
        productDetail = getIntent().getStringExtra("productDetail");
        goodDetailImages = getIntent().getStringArrayListExtra("detailImgList");
        setTitleCenter("图文详情");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if (ValidatorUtils.isNotEmptyString(productDetail)) {
            goodsInsideDetailBinding.tvGoodsDetail.setText(productDetail);
        }

        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess(int requestCode) {
                if (goodDetailImages.size() != 0) {
                    downFile(goodDetailImages.get(downPosition), downPosition);
                }
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finish
                super.negativeButton();
            }
        };

        goodsImagesAdapter = new GoodsImagesAdapter(GoodsInsideDetailActivity.this, R.layout.item_goods_image);
        goodsInsideDetailBinding.lvGoodsImages.setAdapter(goodsImagesAdapter);
        goodsImagesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initData() {
//        for (int i = 0; i < goodDetailImages.size(); i++) {
//            Log.i(TAG, "第" + (i + 1) + "张图片路径=" + goodDetailImages.get(i));
//        }
        startAnim(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_STORAGE);
        } else {
            if (goodDetailImages.size() != 0) {
                downFile(goodDetailImages.get(downPosition), downPosition);
            }
        }
    }

    private void downFile(String url, final int downPosition) {
        final String fileName = url.substring(url.lastIndexOf("/") + 1);
        final String imgPath = CropImageUtils.createImagePath(fileName);
        //判断内存中是否存在改图片
        if (isExists(imgPath)) {
            imgPathList.add(imgPath);
            if (downPosition == (goodDetailImages.size() - 1)) {
                //全部图片下载完成
                Log.e(TAG, "全部图片均下载过");
                stopAnim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goodsImagesAdapter.setData(imgPathList);
                        goodsImagesAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                downFile(goodDetailImages.get(downPosition + 1), downPosition + 1);
            }
        } else {
            //下载图片
            HttpUtils.downFile(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        File file = new File(imgPath);
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        fos.flush();
                        //Log.e(TAG, "保存第" + (downPosition + 1) + "张图片到" + imgPath);
                        imgPathList.add(imgPath);

                        if (downPosition == (goodDetailImages.size() - 1)) {
                            //全部图片下载完成
                            Log.e(TAG, "全部图片下载完成");

                            stopAnim();
                            //开始渲染
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goodsImagesAdapter.setData(imgPathList);
                                    goodsImagesAdapter.notifyDataSetChanged();
                                }
                            });

                        } else {
                            downFile(goodDetailImages.get(downPosition + 1), downPosition + 1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    }
                }
            });
        }
    }

    private boolean isExists(String imgPath) {
        try {
            File f = new File(imgPath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
