package com.weishi.yiye.activity.order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.GridImageAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.ImageUploadBean;
import com.weishi.yiye.bean.eventbus.OrderActionEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.FullyGridLayoutManager;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityCommentBinding;
import com.weishi.yiye.view.CustomDialog;
import com.weishi.yiye.view.RatingBar;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/29
 * @Description：评价商品
 * @Version:v1.0.0
 *****************************/

public class CommentActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = CommentActivity.class.getSimpleName();
    private ActivityCommentBinding commentBinding;

    private RatingBar rb_star;
    private String content;
    private int commentLv = 5;

    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;

    private int uploadPosition = 0;
    private String commentImg = "";
    private StringBuilder sbImg = new StringBuilder();

    private CustomDialog customDialog;

    @Override
    protected void initView() {
        commentBinding = DataBindingUtil.setContentView(CommentActivity.this, R.layout.activity_comment);
        setTitleCenter("评价商品");

        //解决华为虚拟按键遮挡布局底部按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        rb_star = (RatingBar) findViewById(R.id.rb_star);
        rb_star.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                commentLv = (int) ratingCount;
                switch (commentLv) {
                    case 1:
                        commentBinding.tvGrade.setText("1.0分");
                        commentBinding.tvGradeName.setText("非常不满意");
                        break;
                    case 2:
                        commentBinding.tvGrade.setText("2.0分");
                        commentBinding.tvGradeName.setText("不满意");
                        break;
                    case 3:
                        commentBinding.tvGrade.setText("3.0分");
                        commentBinding.tvGradeName.setText("一般");
                        break;
                    case 4:
                        commentBinding.tvGrade.setText("4.0分");
                        commentBinding.tvGradeName.setText("比较满意");
                        break;
                    case 5:
                        commentBinding.tvGrade.setText("5.0分");
                        commentBinding.tvGradeName.setText("非常满意");
                        break;
                    default:
                        break;
                }
            }
        });
        commentBinding.etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // 隐藏键盘
                    ((InputMethodManager) commentBinding.etContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(CommentActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });

        commentBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                commentBinding.tvSurplus.setText((140 - commentBinding.etContent.getText().toString().trim().length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView = commentBinding.recycler;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(CommentActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(CommentActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
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
                            PictureSelector.create(CommentActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(CommentActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(CommentActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        commentBinding.btnRelease.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        commentBinding.sdvOrdersHead.setImageURI(Uri.parse(getIntent().getStringExtra("showImg")));
        commentBinding.tvGoodsName.setText(getIntent().getStringExtra("productName"));
        commentBinding.tvSoldOut.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(getIntent().getDoubleExtra("orderPrice", 0)));
        commentBinding.tvScore.setText("数量：x" + getIntent().getIntExtra("orderNum", 0));
        commentBinding.tvServiceTime.setText("有效期至：" + TimeUtils.millis2String(getIntent().getLongExtra("validTime", 0),
                new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_release:
                startAnim(null);

                content = commentBinding.etContent.getText().toString().trim();
                if ("".equals(content)) {
                    Toast.makeText(CommentActivity.this, "请输入评价内容！ ", Toast.LENGTH_SHORT).show();
                    stopAnim();
                    return;
                }
//                if (IsIDCard.isConSpeCharacters(content)) {
//                    Toast.makeText(CommentActivity.this, "评论内容不能包含非法字符！", Toast.LENGTH_SHORT).show();
//                    stopAnim();
//                    return;
//                }

                if (selectList != null && selectList.size() != 0) {
                    imageUpload(selectList.get(uploadPosition).getCompressPath(), uploadPosition);
                } else {
                    createComment();
                }
                break;
            default:
                break;
        }
    }

    private void createComment() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("productId", getIntent().getIntExtra("productId", 0));
            jsonParams.put("storeId", getIntent().getIntExtra("storeId", 0));
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("orderId", getIntent().getIntExtra("orderId", 0));
            jsonParams.put("content", content);
            jsonParams.put("commentImg", commentImg);
            jsonParams.put("commentLv", commentLv);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.CREATE_ORDER_COMMENT, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.COMMENT_SUCCESS));

                            finish();
                        }
                    }
                });
            }
        });
    }

    /**
     * 图片上传
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
                if (Api.STATE_SUCCESS.equals(imageUploadBean.getCode())) {
                    Log.e(TAG, "第" + (uploadPosition + 1) + "张图片地址=" + imageUploadBean.getData().get(0));

                    if (uploadPosition == (selectList.size() - 1)) {
                        //全部图片上传完成
                        sbImg.append(imageUploadBean.getData().get(0));
                        commentImg = sbImg.toString();
                        Log.e(TAG, "全部图片上传完成，commentImg=" + commentImg);

                        createComment();
                    } else {
                        sbImg.append(imageUploadBean.getData().get(0) + ",");
                        imageUpload(selectList.get(uploadPosition + 1).getCompressPath(), uploadPosition + 1);
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            new PopupWindows(CommentActivity.this, recyclerView);
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
        PictureSelector.create(CommentActivity.this)
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
        PictureSelector.create(CommentActivity.this)
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
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    for (LocalMedia localMedia : selectList) {
                        Log.i(TAG, "选择图片的本地地址=" + localMedia.getCompressPath());
                    }
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
        CustomDialog.Builder builder = new CustomDialog.Builder(CommentActivity.this);
        builder.setMessage("是否退出本次编辑，已编辑内容将会被清除！");
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
