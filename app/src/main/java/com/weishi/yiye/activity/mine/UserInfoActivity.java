package com.weishi.yiye.activity.mine;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.LogUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.ImageUploadBean;
import com.weishi.yiye.bean.UserInfoBean;
import com.weishi.yiye.bean.eventbus.ChangeUserInfoEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.CropImageUtils;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityUserInfoBinding;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/10
 * @Description：个人信息页面
 * @Version:v1.0.0
 *****************************/
public class UserInfoActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = UserInfoActivity.class.getSimpleName();

    private static final int GET_NICKNAME = 0;

    private ActivityUserInfoBinding userInfoBinding;

    private CheckPermission checkPermission;

    @Override
    protected void initView() {
        userInfoBinding = DataBindingUtil.setContentView(UserInfoActivity.this, R.layout.activity_user_info);

        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess(int requestCode) {
                switch (requestCode) {
                    case CheckPermission.REQUEST_CODE_PERMISSION_CAMERA:
                        CropImageUtils.getInstance().takePhoto(UserInfoActivity.this);
                        break;
                    case CheckPermission.REQUEST_CODE_PERMISSION_STORAGE:
                        CropImageUtils.getInstance().openAlbum(UserInfoActivity.this);
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
        setTitleCenter("个人信息");
        userInfoBinding.sdvUserHead.setOnClickListener(this);
        userInfoBinding.tvNickname.setOnClickListener(this);
        userInfoBinding.tvSex.setOnClickListener(this);
        userInfoBinding.tvDateBirth.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        if (mSp.getInt(Constants.USER_ID, 0) != 0) {
            mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            mapParams.put("uid", mSp.getInt(Constants.USER_ID, 0));
        }
        HttpUtils.doGet(Api.GET_USER_INFO, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                String result = response.body().string();
                Log.e(TAG, result);

                final UserInfoBean userInfoBean = GsonUtil.GsonToBean(result, UserInfoBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //userInfoBinding.sdvUserHead.setImageURI(Uri.parse("http://static.happiyi.com/headpic/3WKhKPK35SHM"));
                        if (userInfoBean != null &&
                                userInfoBean.getData() != null) {
                            if (userInfoBean.getData().getNickName() != null) {
                                userInfoBinding.tvNickname.setText(userInfoBean.getData().getNickName().toString());
                            }
                            if (userInfoBean.getData().getAvatar() != null) {
                                userInfoBinding.sdvUserHead.setImageURI(Uri.parse(userInfoBean.getData().getAvatar()));
                            }
                        } else {
                            userInfoBinding.tvNickname.setText("");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sdv_user_head:
                new PopupWindows(UserInfoActivity.this, view);
                break;
            case R.id.tv_nickname:
                startActivityForResult(new Intent(UserInfoActivity.this, ChangeNicknameActivity.class)
                        .putExtra("nickname", userInfoBinding.tvNickname.getText().toString().trim()), GET_NICKNAME);
                break;
            case R.id.tv_sex:
                showDialog(userInfoBinding.tvSex);
                break;
            case R.id.tv_date_birth:
                showDialog(userInfoBinding.tvDateBirth);
                break;
            default:
                break;
        }
    }

    /**
     * 弹出对话框，弹出性别选择框和日期选择对话框
     *
     * @param view
     */
    String[] items = {"男", "女"};
    int index = 0;

    public void showDialog(final View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        switch (view.getId()) {
            case R.id.tv_sex:
                //选择性别对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setSingleChoiceItems(items, index, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index = which;
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userInfoBinding.tvSex.setText(items[index]);
                    }
                });
                builder.show();
                break;
            case R.id.tv_date_birth:
                //日期选择对话框
                DatePickerDialog dpd = new DatePickerDialog(UserInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String mouth = String.valueOf(monthOfYear + 1);
                                if (monthOfYear + 1 < 10) {
                                    mouth = "0" + mouth;
                                }
                                String day = String.valueOf(dayOfMonth);
                                if (dayOfMonth < 10) {
                                    day = "0" + day;
                                }
                                String birthday = year + "-" + mouth + "-" + day;
                                userInfoBinding.tvDateBirth.setText(birthday);
                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;
            default:
                break;
        }
    }

    private class PopupWindows extends PopupWindow {

        private PopupWindows(final Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.popup_choose_pic, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
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
                        CropImageUtils.getInstance().takePhoto(UserInfoActivity.this);
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
                        CropImageUtils.getInstance().openAlbum(UserInfoActivity.this);
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
                CropImageUtils.getInstance().cropPicture(UserInfoActivity.this, path, CropImageUtils.ASPECT_RATIO1);
            }

            @Override
            public void selectPictureFinish(String path) {
                LogUtils.i(TAG, "打开图片：" + path);
                //相册回调，去裁剪
                CropImageUtils.getInstance().cropPicture(UserInfoActivity.this, path, CropImageUtils.ASPECT_RATIO1);
            }

            @Override
            public void cropPictureFinish(String path) {
                LogUtils.i(TAG, "裁剪保存在：" + path);
                //裁剪回调
                userInfoBinding.sdvUserHead.setImageURI(Uri.parse("file://" + path));
                //Glide.with(MainActivity.this).load(path).into((ImageView) findViewById(R.id.image_result));
                //上传图片
                imageUpload(path);
            }
        });

        switch (requestCode) {
            case GET_NICKNAME:
                if (data != null) {
                    userInfoBinding.tvNickname.setText(data.getStringExtra("nickname"));
                }
                break;
            default:
                break;
        }
    }

    //头像图片上传
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
                    //修改用户头像
                    updateUserAvatar(imageUploadBean.getData().get(0));
                    Log.e(TAG, "图片地址 =" + imageUploadBean.getData().get(0));
                }
            }
        });
    }


    //修改用户头像
    public void updateUserAvatar(final String avatarUrl) {
        JSONObject jsonParams = new JSONObject();
        try {
            if (!(mSp.getInt(Constants.USER_ID, 0) == 0)) {
                jsonParams.put("id", mSp.getInt(Constants.USER_ID, 0));
            }
            jsonParams.put("avatar", avatarUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.MODIFY_USER_INFO + "?uid=" + mSp.getInt(Constants.USER_ID, 0), jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            mSp.putString(Constants.AVATAR, avatarUrl);
                            //发送EventBus修改个人信息事件
                            EventBus.getDefault().post(new ChangeUserInfoEvent("", avatarUrl));
                        }
                    }
                });
            }
        });
    }
}
