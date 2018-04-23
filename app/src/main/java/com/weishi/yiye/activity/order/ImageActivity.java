package com.weishi.yiye.activity.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseActivity;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.CropImageUtils;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityImageBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/22
 * @Description：评论图片展示
 * @Version:v1.0.0
 *****************************/
public class ImageActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ImageActivity.class.getSimpleName();
    private ActivityImageBinding imageBinding;

    private ArrayList<String> picList;
    private TextView tv_count;
    private ViewPager myViewPager;
    private int currentPosition;
    private ImageView iv_save;
    private String currentImgUrl;
    private CheckPermission checkPermission;

    @Override
    protected void initView() {
        imageBinding = DataBindingUtil.setContentView(ImageActivity.this, R.layout.activity_image);
        picList = getIntent().getStringArrayListExtra(Constants.IMAGE_LIST);
        currentPosition = getIntent().getIntExtra(Constants.CURRENT_IMG_POSITION, 0);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        checkPermission = new CheckPermission(ImageActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                downFile(currentImgUrl);
            }

            @Override
            public void negativeButton() {
                super.negativeButton();
            }
        };

        tv_count = imageBinding.tvCount;
        iv_save = imageBinding.ivSave;
        myViewPager = imageBinding.vpImage;
        iv_save.setOnClickListener(this);
        myViewPager = (ViewPager) this.findViewById(R.id.vp_image);
        if (picList != null && picList.size() != 0) {
            tv_count.setText((currentPosition + 1) + "/" + picList.size());
            myViewPager.setAdapter(new MyPagerAdapter());
            myViewPager.setCurrentItem(currentPosition);
            myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    tv_count.setText((position + 1) + "/" + picList.size());
                    currentPosition = position;
                    currentImgUrl = picList.get(position);
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void back(View view) {
        super.back(view);
        overridePendingTransition(0, com.luck.picture.lib.R.anim.a3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_save:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_STORAGE);
                } else {
                    downFile(currentImgUrl);
                }
                break;
            default:
                break;
        }
    }

    private void downFile(String url) {
        final String fileName = url.substring(url.lastIndexOf("/") + 1);
        final String imgPath = CropImageUtils.createImagePath(fileName);
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ImageActivity.this, "保存图片到" + imgPath, Toast.LENGTH_SHORT).show();
                        }
                    });
                    //这个广播的目的就是更新图库
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    sendBroadcast(intent);
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

    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(480, 800);
            Glide.with(ImageActivity.this)
                    .asBitmap()
                    .load(picList.get(position))
                    .apply(options)
                    .into(photoView);
            (container).addView(photoView);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                    overridePendingTransition(0, com.luck.picture.lib.R.anim.a3);
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //根据指定的下标获得当前移除的view对象 然后在视图组中移除
            (container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
