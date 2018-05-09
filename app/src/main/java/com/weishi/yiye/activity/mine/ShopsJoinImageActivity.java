package com.weishi.yiye.activity.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.GridImageAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.eventbus.RemoveImgEvent;
import com.weishi.yiye.common.util.FullyGridLayoutManager;
import com.weishi.yiye.databinding.ActivityShopsJoinImageBinding;
import com.weishi.yiye.view.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/4/27
 * @Description：商家入驻banner编辑页面
 * @Version:v1.0.0
 *****************************/

public class ShopsJoinImageActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ShopsJoinImageActivity.class.getSimpleName();
    private ActivityShopsJoinImageBinding shopsJoinImageBinding;
    private TextView rightTitle;

    private MyPagerAdapter pagerAdapter;
    private MyPageChangeListener pageChangeListener;

    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;

    private int imgPosition;

    private CustomDialog customDialog;

    private ViewGroup.LayoutParams para;

    @Override
    protected void initView() {
        shopsJoinImageBinding = DataBindingUtil.setContentView(ShopsJoinImageActivity.this, R.layout.activity_shops_join_image);
        setTitleCenter("编辑店铺图片");
        rightTitle = (TextView) findViewById(R.id.tv_right_title);
        rightTitle.setVisibility(View.VISIBLE);
        rightTitle.setText("保存");
        rightTitle.setOnClickListener(this);
        selectList = (List<LocalMedia>) getIntent().getSerializableExtra("selectList");
        imgPosition = getIntent().getIntExtra("imgPosition", 0);

        para = shopsJoinImageBinding.rlImgInfo.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 848 / 1080;
        shopsJoinImageBinding.rlImgInfo.setLayoutParams(para);

        pagerAdapter = new MyPagerAdapter();
        pageChangeListener = new MyPageChangeListener();
        shopsJoinImageBinding.vpBanner.setAdapter(pagerAdapter);
        shopsJoinImageBinding.vpBanner.setOnPageChangeListener(pageChangeListener);
        shopsJoinImageBinding.vpBanner.setCurrentItem(imgPosition);
        shopsJoinImageBinding.tvVpIndicator.setText((imgPosition + 1) + "/" + selectList.size());

        FullyGridLayoutManager manager = new FullyGridLayoutManager(ShopsJoinImageActivity.this, 4, GridLayoutManager.VERTICAL, false);
        shopsJoinImageBinding.recycler.setLayoutManager(manager);
        adapter = new GridImageAdapter(ShopsJoinImageActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        shopsJoinImageBinding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            //PictureSelector.create(CommentActivity.this).externalPicturePreview(position, selectList);
                            //切换ViewPager
                            shopsJoinImageBinding.vpBanner.setCurrentItem(position);
                            shopsJoinImageBinding.tvVpIndicator.setText((position + 1) + "/" + selectList.size());
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                Intent intent = getIntent();
                intent.putExtra("selectList", (Serializable) selectList);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRemoveImgEvent(RemoveImgEvent removeImgEvent) {
//        if (selectList.size() != 0) {
//            selectList.clear();
//        }
        selectList = removeImgEvent.getSelectList();
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
        pagerAdapter.notifyDataSetChanged();
        int showIndex;
        Log.e(TAG, "index=" + removeImgEvent.getRemoveIndex());
        if (removeImgEvent.getRemoveIndex() < 1 && selectList.size() == 0) {
            showIndex = 0;
            shopsJoinImageBinding.vpBanner.setCurrentItem(showIndex);
            shopsJoinImageBinding.tvVpIndicator.setText(showIndex + "/" + selectList.size());
        } else if (removeImgEvent.getRemoveIndex() < 1) {
            showIndex = 0;
            shopsJoinImageBinding.vpBanner.setCurrentItem(showIndex);
            shopsJoinImageBinding.tvVpIndicator.setText((showIndex + 1) + "/" + selectList.size());
        } else {
            showIndex = removeImgEvent.getRemoveIndex() - 1;
            shopsJoinImageBinding.vpBanner.setCurrentItem(showIndex);
            shopsJoinImageBinding.tvVpIndicator.setText((showIndex + 1) + "/" + selectList.size());
        }
    }

    /**
     * 自定义pageradapter  适配viewpager
     */
    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return selectList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(container.getContext());
            //imageView.setId(999 + position);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);

            //为imageView加载本地图片
            imageView.setImageURI(Uri.fromFile(new File(selectList.get(position).getCompressPath())));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            (container).addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author xieqinghua
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            shopsJoinImageBinding.tvVpIndicator.setText((position + 1) + "/" + selectList.size());
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            new PopupWindows(ShopsJoinImageActivity.this, shopsJoinImageBinding.recycler);
        }
    };

    private class PopupWindows extends PopupWindow {

        private PopupWindows(final Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.popup_choose_pic, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            //修改高度显示，解决被手机底部虚拟键挡住的问题
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //实例化一个ColorDrawable颜色为半透明
            setBackgroundDrawable(new ColorDrawable(0xb0000000));
            //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int height = view.findViewById(R.id.ll_popup).getTop();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            update();
            //拍照按钮
            Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
            //选取照片按钮
            Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
            //取消按钮
            Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAlbum();
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        // 单独拍照
        PictureSelector.create(ShopsJoinImageActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像 看你传入的类型是图片or视频
                .theme(R.style.picture_white_style)// 主题样式设置 具体参考 values/styles
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false)// 是否预览音频
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        // 进入相册，不需要的api可以不写
        PictureSelector.create(ShopsJoinImageActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_white_style)// 主题样式设置 具体参考 values/styles
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false)// 是否预览音频
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/Chinayie/App")// 自定义拍照保存路径
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频
                //.recordVideoSecond()//录制视频秒数 默认60秒
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (selectList.size() != 0) {
                        selectList.clear();
                    }
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    pagerAdapter.notifyDataSetChanged();
                    shopsJoinImageBinding.vpBanner.setCurrentItem(0);
                    shopsJoinImageBinding.tvVpIndicator.setText("1/" + selectList.size());
                    //for (LocalMedia localMedia : selectList) {
                    //    Log.i(TAG, "选择图片的本地地址=" + localMedia.getCompressPath());
                    //}
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (customDialog != null) {
            customDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void back(View view) {
        CustomDialog.Builder builder = new CustomDialog.Builder(ShopsJoinImageActivity.this);
        builder.setMessage("是否退出本次编辑？");
        builder.setTitleVisibility(View.GONE);
        builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        customDialog = builder.create();
        customDialog.show();
    }
}
