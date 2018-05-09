package com.weishi.yiye.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.BusinessFatherActivity;
import com.weishi.yiye.activity.ProvinceActivity;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.ImageUploadBean;
import com.weishi.yiye.bean.SelectAddressBean;
import com.weishi.yiye.bean.SelectShopTypeBean;
import com.weishi.yiye.bean.ShopsJoinBean;
import com.weishi.yiye.bean.eventbus.SelectAddressEvent;
import com.weishi.yiye.bean.eventbus.SelectShopTypeEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.CropImageUtils;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.IsIDCard;
import com.weishi.yiye.common.util.ValidatorUtils;
import com.weishi.yiye.databinding.ActivityShopsJoinDataBinding;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/19
 * @Description：商家入驻资料填写页面
 * @Version:v1.0.0
 *****************************/

public class ShopsJoinDataActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ShopsJoinDataActivity.class.getSimpleName();

    private static final int EDIT_IMAGE = 1;
    private static final int CHOOSE_LOCATION = 2;
    private ActivityShopsJoinDataBinding shopsJoinDataBinding;

    private CheckPermission checkPermission;

    private String provinceCode, provinceName, cityCode, cityName, areaCode, areaName;
    private int businessFatherType;
    private String businessFatherTypeName;
    private int businessParentType;
    private String businessParentTypeName;
    private int businessSortType;
    private String businessSortTypeName;

    private Double lng, lat;

    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;
    private int uploadPosition = 0;
    private StringBuilder sbImg = new StringBuilder();
    private String alternateImg;

    private MyPagerAdapter pagerAdapter;
    private MyPageChangeListener pageChangeListener;

    private String logoPath;
    private String storeLogo;

    private ViewGroup.LayoutParams para;

    @Override
    protected void initView() {
        shopsJoinDataBinding = DataBindingUtil.setContentView(ShopsJoinDataActivity.this, R.layout.activity_shops_join_data);
        setTitleCenter("资料填写");

        //解决华为虚拟按键遮挡布局底部按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess(int requestCode) {
                switch (requestCode) {
                    case CheckPermission.REQUEST_CODE_PERMISSION_CAMERA:
                        CropImageUtils.getInstance().takePhoto(ShopsJoinDataActivity.this);
                        break;
                    case CheckPermission.REQUEST_CODE_PERMISSION_STORAGE:
                        CropImageUtils.getInstance().openAlbum(ShopsJoinDataActivity.this);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finish
                //super.negativeButton();
                LogUtils.e(TAG, "权限申请失败！");
            }
        };

        para = shopsJoinDataBinding.rlImgInfo.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 848 / 1080;
        shopsJoinDataBinding.rlImgInfo.setLayoutParams(para);

        shopsJoinDataBinding.btnSubmitApply.setOnClickListener(this);
        shopsJoinDataBinding.rlChooseAddress.setOnClickListener(this);
        shopsJoinDataBinding.tvChooseShopsClass.setOnClickListener(this);

        pagerAdapter = new MyPagerAdapter();
        pageChangeListener = new MyPageChangeListener();
        shopsJoinDataBinding.vpBanner.setAdapter(pagerAdapter);
        shopsJoinDataBinding.vpBanner.setOnPageChangeListener(pageChangeListener);
        shopsJoinDataBinding.tvAddBanner.setOnClickListener(this);
        shopsJoinDataBinding.tvAddLogo.setOnClickListener(this);
        shopsJoinDataBinding.tvChooseLocation.setOnClickListener(this);

        //默认登录用户手机号
        shopsJoinDataBinding.etShopkeeperMobile.setText(mSp.getString(Constants.PHONE, ""));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_banner:
                openAlbum();
                break;
            case R.id.tv_choose_shops_class:
                //选择商家分类
                startActivity(new Intent(ShopsJoinDataActivity.this, BusinessFatherActivity.class));
                break;
            case R.id.rl_choose_address:
                //选择地址区域
                startActivity(new Intent(ShopsJoinDataActivity.this, ProvinceActivity.class));
                break;
            case R.id.btn_submit_apply:
//                if (shopId != null) {
//                    new PayPopupWindows(ShopsJoinDataActivity.this, shopsJoinDataBinding.btnSubmitApply, shopId + "");
//                } else {

                startAnim(null);

                if (selectList != null && selectList.size() != 0) {
                    imageUpload(selectList.get(uploadPosition).getCompressPath(), uploadPosition);
                } else {
                    submitApply();
                }
//                }
                break;
            case R.id.tv_add_logo:
                new PopupWindows(ShopsJoinDataActivity.this, view);
                break;
            case R.id.tv_choose_location:
                startActivityForResult(new Intent(ShopsJoinDataActivity.this, ChooseLocationActivity.class), CHOOSE_LOCATION);
                break;
            default:
                break;
        }
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        // 进入相册，不需要的api可以不写
        PictureSelector.create(ShopsJoinDataActivity.this)
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
                .isCamera(true)// 是否显示拍照按钮
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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShopsJoinDataActivity.this, ShopsJoinImageActivity.class);
                    intent.putExtra("selectList", (Serializable) selectList);
                    intent.putExtra("imgPosition", position);
                    startActivityForResult(intent, EDIT_IMAGE);
                }
            });
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
            shopsJoinDataBinding.tvVpIndicator.setVisibility(View.VISIBLE);
            shopsJoinDataBinding.tvVpIndicator.setText((position + 1) + "/" + selectList.size());
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * 提交申请
     */
    private void submitApply() {
        if (logoPath == null) {
            Toast.makeText(ShopsJoinDataActivity.this, "请上传店铺LOGO", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (selectList.size() == 0) {
            Toast.makeText(ShopsJoinDataActivity.this, "请添加店铺轮播图", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsName.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写店铺名称", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopkeeperName.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写店主姓名", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopkeeperIdentity.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写店主身份证号", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (!IsIDCard.isIDCard(shopsJoinDataBinding.etShopkeeperIdentity.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请输入有效身份证号码", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (ValidatorUtils.isEmptyString(provinceCode)) {
            Toast.makeText(ShopsJoinDataActivity.this, "请选择商家区域", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsAddress.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写店铺详细地址", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (lng == null) {
            Toast.makeText(ShopsJoinDataActivity.this, "请选择坐标", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopkeeperMobile.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写商家手机号码", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
        if (!IsIDCard.isValidMobileNo(shopsJoinDataBinding.etShopkeeperMobile.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            stopAnim();
            return;
        }
//        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsRecNo.getText().toString())) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请填写店铺邀请码", Toast.LENGTH_SHORT).show();
//            stopAnim();
//            return;
//        }
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("alternateImg", alternateImg);
            jsonParams.put("storeLogo", storeLogo);
            jsonParams.put("storeName", shopsJoinDataBinding.etShopsName.getText().toString());
            jsonParams.put("shopkeeperName", shopsJoinDataBinding.etShopkeeperName.getText().toString());
            jsonParams.put("idCard", shopsJoinDataBinding.etShopkeeperIdentity.getText().toString());
            jsonParams.put("mobile", shopsJoinDataBinding.etShopkeeperMobile.getText().toString());
            jsonParams.put("provinceCode", provinceCode);
            jsonParams.put("provinceName", provinceName);
            jsonParams.put("cityCode", cityCode);
            jsonParams.put("cityName", cityName);
            jsonParams.put("areaCode", areaCode);
            jsonParams.put("areaName", areaName);
            jsonParams.put("address", shopsJoinDataBinding.etShopsAddress.getText().toString());

            jsonParams.put("lng", lng);
            jsonParams.put("lat", lat);

            jsonParams.put("businessFatherType", businessFatherType);
            jsonParams.put("businessFatherTypeName", businessFatherTypeName);
            jsonParams.put("businessParentType", businessParentType);
            jsonParams.put("businessParentTypeName", businessParentTypeName);
            jsonParams.put("businessSortType", businessSortType);
            jsonParams.put("businessSortTypeName", businessSortTypeName);
            if (!ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsRecNo.getText().toString())) {
                jsonParams.put("inviteCode", shopsJoinDataBinding.etShopsRecNo.getText().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.SAVE_BUSI_APPLY, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final ShopsJoinBean shopsJoinBean = GsonUtil.GsonToBean(result, ShopsJoinBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(shopsJoinBean.getCode())) {
                            Toast.makeText(ShopsJoinDataActivity.this, "申请成功等待平台审核", Toast.LENGTH_LONG).show();
                            finish();
//                            if (shopsJoinBean.getData().getOnlinePay() == 1) {
//                                shopId = shopsJoinBean.getData().getId();
//                                new PayPopupWindows(ShopsJoinDataActivity.this, shopsJoinDataBinding.btnSubmitApply, shopId + "");
//                            }
                        } else {
                            Toast.makeText(ShopsJoinDataActivity.this, shopsJoinBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_CAMERA);
                    } else {
                        CropImageUtils.getInstance().takePhoto(ShopsJoinDataActivity.this);
                    }
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_STORAGE);
                    } else {
                        CropImageUtils.getInstance().openAlbum(ShopsJoinDataActivity.this);
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
            @Override
            public void takePhotoFinish(String path) {
                LogUtils.i(TAG, "照片存放在：" + path);
                //拍照回调，去裁剪
                CropImageUtils.getInstance().cropPicture(ShopsJoinDataActivity.this, path, CropImageUtils.ASPECT_RATIO1);
            }

            @Override
            public void selectPictureFinish(String path) {
                LogUtils.i(TAG, "打开图片：" + path);
                //相册回调，去裁剪
                CropImageUtils.getInstance().cropPicture(ShopsJoinDataActivity.this, path, CropImageUtils.ASPECT_RATIO1);
            }

            @Override
            public void cropPictureFinish(String path) {
                LogUtils.i(TAG, "裁剪保存在：" + path);
                //上传图片
                //imageUpload(path, presentView);
                //shopsJoinDataBinding.tvAddLogo.setImageURI(Uri.parse("file://" + path));
                shopsJoinDataBinding.tvAddLogo.setImageURI(Uri.fromFile(new File(path)));
                logoPath = path;
                imageUpload(path);
            }
        });

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    for (LocalMedia localMedia : selectList) {
                        Log.i(TAG, "选择图片的本地地址=" + localMedia.getCompressPath());
                    }

                    shopsJoinDataBinding.tvAddBanner.setVisibility(View.GONE);
                    shopsJoinDataBinding.vpBanner.setVisibility(View.VISIBLE);
                    shopsJoinDataBinding.tvVpIndicator.setVisibility(View.VISIBLE);
                    shopsJoinDataBinding.tvVpIndicator.setText("1/" + selectList.size());
                    pagerAdapter.notifyDataSetChanged();
                    break;
                case EDIT_IMAGE:
                    if (data != null) {
                        selectList = (List<LocalMedia>) data.getSerializableExtra("selectList");
                        if (selectList.size() != 0) {
                            shopsJoinDataBinding.tvAddBanner.setVisibility(View.GONE);
                            shopsJoinDataBinding.vpBanner.setVisibility(View.VISIBLE);
                            shopsJoinDataBinding.tvVpIndicator.setVisibility(View.VISIBLE);
                            shopsJoinDataBinding.tvVpIndicator.setText("1/" + selectList.size());
                            pagerAdapter.notifyDataSetChanged();
                        } else {
                            shopsJoinDataBinding.tvAddBanner.setVisibility(View.VISIBLE);
                            shopsJoinDataBinding.vpBanner.setVisibility(View.GONE);
                            shopsJoinDataBinding.tvVpIndicator.setVisibility(View.GONE);
                        }
                    }
                case CHOOSE_LOCATION:
                    if (data != null) {
                        lng = data.getDoubleExtra("lng", 0);
                        lat = data.getDoubleExtra("lat", 0);
                        shopsJoinDataBinding.tvChooseLocation.setText("经度：" + new DecimalFormat("#0.0000").format(lng) +
                                "；纬度：" + new DecimalFormat("#0.0000").format(lat));
                    }
                default:
                    break;
            }
        }
    }

    /**
     * 上传单张图片
     *
     * @param url 图片路径
     */
    public void imageUpload(String url) {
        File file = new File(url);
        HttpUtils.doFile(Api.IMAGE_UPLOAD, url, file.getName(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final ImageUploadBean imageUploadBean = GsonUtil.GsonToBean(result, ImageUploadBean.class);
                if (Api.STATE_SUCCESS.equals(imageUploadBean.getCode())) {
                    Log.e(TAG, "图片地址 =" + imageUploadBean.getData().get(0));
                    storeLogo = imageUploadBean.getData().get(0);
                }
            }
        });
    }

    /**
     * 多张图片上传
     *
     * @param path
     * @param uploadPosition
     */
    public void imageUpload(String path, final int uploadPosition) {
        File file = new File(path);
        HttpUtils.doFile(Api.IMAGE_UPLOAD, path, file.getName(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final ImageUploadBean imageUploadBean = GsonUtil.GsonToBean(result, ImageUploadBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(imageUploadBean.getCode())) {
                            Log.e(TAG, "第" + (uploadPosition + 1) + "张图片地址=" + imageUploadBean.getData().get(0));

                            if (uploadPosition == (selectList.size() - 1)) {
                                //全部图片上传完成
                                sbImg.append(imageUploadBean.getData().get(0));
                                alternateImg = sbImg.toString();
                                Log.e(TAG, "全部图片上传完成，commentImg=" + alternateImg);

                                submitApply();
                            } else {
                                sbImg.append(imageUploadBean.getData().get(0) + ";");
                                imageUpload(selectList.get(uploadPosition + 1).getCompressPath(), uploadPosition + 1);
                            }
                        }
                    }
                });
            }
        });
    }

    @Subscribe
    public void onEvent(SelectAddressEvent event) {
        SelectAddressBean selectAddressBean = event.model;
        provinceCode = selectAddressBean.getProvinceCode();
        provinceName = selectAddressBean.getProvince();
        cityCode = selectAddressBean.getCityCode();
        cityName = selectAddressBean.getCity();
        areaCode = selectAddressBean.getAreaCode();
        areaName = selectAddressBean.getArea();
        shopsJoinDataBinding.tvShopArea.setText(provinceName + cityName + areaName);
    }

    @Subscribe
    public void onEvent(SelectShopTypeEvent event) {
        SelectShopTypeBean selectShopTypeBean = event.model;
        businessFatherType = selectShopTypeBean.getBusinessFatherType();
        businessFatherTypeName = selectShopTypeBean.getBusinessFatherTypeName();
        businessParentType = selectShopTypeBean.getBusinessParentType();
        businessParentTypeName = selectShopTypeBean.getBusinessParentTypeName();
        businessSortType = selectShopTypeBean.getBusinessSortType();
        businessSortTypeName = selectShopTypeBean.getBusinessSortTypeName();
        //Log.e(TAG, "businessFatherTypeName=" + businessFatherTypeName + "businessParentTypeName=" + businessParentTypeName + "businessSortTypeName=" + businessSortTypeName);
        if (businessParentType != -1) {
            if (businessSortType != -1) {
                shopsJoinDataBinding.tvChooseShopsResult.setText(businessFatherTypeName + "-" + businessParentTypeName + "-" + businessSortTypeName);
            } else {
                shopsJoinDataBinding.tvChooseShopsResult.setText(businessFatherTypeName + "-" + businessParentTypeName);
            }
        } else {
            shopsJoinDataBinding.tvChooseShopsResult.setText(businessFatherTypeName);
        }
    }
}
