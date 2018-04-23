package com.weishi.yiye.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.ProvinceActivity;
import com.weishi.yiye.activity.ShopClassActivity;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.ImageUploadBean;
import com.weishi.yiye.bean.PayResultBean;
import com.weishi.yiye.bean.RechargeBean;
import com.weishi.yiye.bean.SelectAddressBean;
import com.weishi.yiye.bean.SelectShopTypeBean;
import com.weishi.yiye.bean.ShopsJoinBean;
import com.weishi.yiye.bean.eventbus.SelectAddressEvent;
import com.weishi.yiye.bean.eventbus.SelectShopTypeEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.CropImageUtils;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.IntentUtil;
import com.weishi.yiye.common.util.IsIDCard;
import com.weishi.yiye.common.util.ValidatorUtils;
import com.weishi.yiye.databinding.ActivityShopsJoinDataBinding;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    private ActivityShopsJoinDataBinding shopsJoinDataBinding;

    private CheckPermission checkPermission;

    private int presentView;
    //营业执照图片路径
    private String businessLicensePath;
    //身份证图片路径
    private String identityCardPath;
    private String provinceCode, provinceName, cityCode, cityName, areaCode, areaName;
    private int shopTypeFirstParentId;
    private String shopTypeFirstParentName;
    private int shopTypeSecondParentId;
    private String shopTypeSecondParentName;

    private Integer shopId;

    @Override
    protected void initView() {
        shopsJoinDataBinding = DataBindingUtil.setContentView(ShopsJoinDataActivity.this, R.layout.activity_shops_join_data);
        setTitleCenter("资料填写");

        //解决华为虚拟按键遮挡布局底部按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

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

        WindowManager wm = (WindowManager) ShopsJoinDataActivity.this.getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para1 = shopsJoinDataBinding.rlIdentityPhoto.getLayoutParams();
        para1.width = wm.getDefaultDisplay().getWidth();
        para1.height = wm.getDefaultDisplay().getWidth() * 1 / 2;
        shopsJoinDataBinding.rlIdentityPhoto.setLayoutParams(para1);
        ViewGroup.LayoutParams para2 = shopsJoinDataBinding.rlBusinessLicense.getLayoutParams();
        para2.width = wm.getDefaultDisplay().getWidth();
        para2.height = wm.getDefaultDisplay().getWidth() * 1 / 2;
        shopsJoinDataBinding.rlBusinessLicense.setLayoutParams(para2);

        shopsJoinDataBinding.tvIdentityPhoto.setOnClickListener(this);
        shopsJoinDataBinding.tvBusinessLicense.setOnClickListener(this);

        shopsJoinDataBinding.btnSubmitApply.setOnClickListener(this);
        shopsJoinDataBinding.rlChooseAddress.setOnClickListener(this);
        shopsJoinDataBinding.tvChooseShopsClass.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_identity_photo:
                //记录当前点击view
                presentView = R.id.tv_identity_photo;
                new PopupWindows(ShopsJoinDataActivity.this, view);
                break;
            case R.id.tv_business_license:
                //记录当前点击view
                presentView = R.id.tv_business_license;
                new PopupWindows(ShopsJoinDataActivity.this, view);
                break;
            case R.id.tv_choose_shops_class:
                //选择商家分类
                Intent shopClassIntent = new Intent(this, ShopClassActivity.class);
                shopClassIntent.putExtra(ShopConstants.TYPE_SHOP_PARENT_ID, ShopConstants.DEFAULT_VALUE_SHOP_ID);
                startActivity(shopClassIntent);
                break;
            case R.id.rl_choose_address:
                //选择地址区域
                IntentUtil.startActivity(ShopsJoinDataActivity.this, ProvinceActivity.class, 1);
                break;
            case R.id.iv_del_identity_photo:
                shopsJoinDataBinding.sdvIdentityPhoto.setVisibility(View.GONE);
                shopsJoinDataBinding.ivDelIdentityPhoto.setVisibility(View.GONE);
                break;
            case R.id.iv_del_business_license:
                shopsJoinDataBinding.sdvBusinessLicense.setVisibility(View.GONE);
                shopsJoinDataBinding.ivDelBusinessLicense.setVisibility(View.GONE);
                break;
            case R.id.btn_submit_apply:
                if (shopId != null) {
                    new PayPopupWindows(ShopsJoinDataActivity.this, shopsJoinDataBinding.btnSubmitApply, shopId + "");
                } else {
                    submitApply();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提交申请
     */
    private void submitApply() {
//        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsName.getText().toString())) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请填写商家名称", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsAddress.getText().toString())) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请填写店铺详细地址", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsRecNo.getText().toString())) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请填写店铺邀请码", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopkeeperName.getText().toString())) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请填写店主姓名", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopkeeperMobile.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写商家手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!IsIDCard.isValidMobileNo(shopsJoinDataBinding.etShopkeeperMobile.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopkeeperIdentity.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请填写店主身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!IsIDCard.isIDCard(shopsJoinDataBinding.etShopkeeperIdentity.getText().toString())) {
            Toast.makeText(ShopsJoinDataActivity.this, "请输入有效身份证号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ValidatorUtils.isEmptyString(provinceCode)) {
            Toast.makeText(ShopsJoinDataActivity.this, "请选择商家区域", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopLicenceNo.getText().toString())) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请填写店铺营业执照号码", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (ValidatorUtils.isEmptyString(identityCardPath)) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请上传身份证照片", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (ValidatorUtils.isEmptyString(businessLicensePath)) {
//            Toast.makeText(ShopsJoinDataActivity.this, "请上传营业执照", Toast.LENGTH_SHORT).show();
//            return;
//        }

        JSONObject jsonParams = new JSONObject();
        try {
//            jsonParams.put("businessName", shopsJoinDataBinding.etShopsName.getText().toString());
//            jsonParams.put("corporationName", shopsJoinDataBinding.etShopkeeperName.getText().toString());
            jsonParams.put("mobile", shopsJoinDataBinding.etShopkeeperMobile.getText().toString());
//            jsonParams.put("busiFatherType", shopTypeFirstParentId);
//            jsonParams.put("busiFatherTypeName", shopTypeFirstParentName);
//            jsonParams.put("busiParentType", shopTypeSecondParentId);
//            jsonParams.put("busiParentTypeName", shopTypeSecondParentName);
//            jsonParams.put("licenceNo", shopsJoinDataBinding.etShopLicenceNo.getText().toString());
//            jsonParams.put("licenceImg", businessLicensePath);
            jsonParams.put("idCard", shopsJoinDataBinding.etShopkeeperIdentity.getText().toString());
//            jsonParams.put("idCardImg", identityCardPath);
//            jsonParams.put("address", shopsJoinDataBinding.etShopsAddress.getText().toString());
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
//            jsonParams.put("id", mSp.getInt(Constants.USER_ID, 0));
            if (!ValidatorUtils.isEmptyString(shopsJoinDataBinding.etShopsRecNo.getText().toString())) {
                jsonParams.put("inviteCode", shopsJoinDataBinding.etShopsRecNo.getText().toString());
            }
            jsonParams.put("provinceCode", provinceCode);
            jsonParams.put("cityCode", cityCode);
            jsonParams.put("areaCode", areaCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.SAVE_BUSI_APPLY, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final ShopsJoinBean shopsJoinBean = GsonUtil.GsonToBean(result, ShopsJoinBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(shopsJoinBean.getCode())) {
                            if (shopsJoinBean.getData().getOnlinePay() == 1) {
                                shopId = shopsJoinBean.getData().getId();
                                new PayPopupWindows(ShopsJoinDataActivity.this, shopsJoinDataBinding.btnSubmitApply, shopId + "");
                            }
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
                CropImageUtils.getInstance().cropPicture(ShopsJoinDataActivity.this, path, CropImageUtils.ASPECT_RATIO2);
            }

            @Override
            public void selectPictureFinish(String path) {
                LogUtils.i(TAG, "打开图片：" + path);
                //相册回调，去裁剪
                CropImageUtils.getInstance().cropPicture(ShopsJoinDataActivity.this, path, CropImageUtils.ASPECT_RATIO2);
            }

            @Override
            public void cropPictureFinish(String path) {
                LogUtils.i(TAG, "裁剪保存在：" + path);
                //裁剪回调
                if (presentView == R.id.tv_identity_photo) {
                    //上传图片
                    imageUpload(path, presentView);
                    shopsJoinDataBinding.sdvIdentityPhoto.setImageURI(Uri.parse("file://" + path));
                    shopsJoinDataBinding.sdvIdentityPhoto.setVisibility(View.VISIBLE);
                    shopsJoinDataBinding.ivDelIdentityPhoto.setVisibility(View.VISIBLE);
                    shopsJoinDataBinding.ivDelIdentityPhoto.setOnClickListener(ShopsJoinDataActivity.this);
                } else if (presentView == R.id.tv_business_license) {
                    //上传图片
                    imageUpload(path, presentView);
                    shopsJoinDataBinding.sdvBusinessLicense.setImageURI(Uri.parse("file://" + path));
                    shopsJoinDataBinding.sdvBusinessLicense.setVisibility(View.VISIBLE);
                    shopsJoinDataBinding.ivDelBusinessLicense.setVisibility(View.VISIBLE);
                    shopsJoinDataBinding.ivDelBusinessLicense.setOnClickListener(ShopsJoinDataActivity.this);
                }
            }
        });
    }

    /**
     * 上传图片
     *
     * @param url         图片路径
     * @param presentView 当前视图
     */

    public void imageUpload(String url, final int presentView) {
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
                    //营业执照
                    if (presentView == R.id.tv_business_license) {
                        businessLicensePath = imageUploadBean.getData().get(0);
                    }
                    //身份证照片
                    else if (presentView == R.id.tv_identity_photo) {
                        identityCardPath = imageUploadBean.getData().get(0);
                    }
                }
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
        shopTypeFirstParentId = selectShopTypeBean.getShopTypeFirstParentId();
        shopTypeFirstParentName = selectShopTypeBean.getShopTypeFirstParentName();
        shopTypeSecondParentId = selectShopTypeBean.getShopTypeSecondParentId();
        shopTypeSecondParentName = selectShopTypeBean.getShopTypeSecondParentName();
        shopsJoinDataBinding.tvChooseShopsResult.setText(shopTypeFirstParentName);
    }

    /**
     * 选择支付方式弹窗
     */
    private class PayPopupWindows extends PopupWindow {

        private PayPopupWindows(final Context mContext, View parent, final String orderNum) {
            View view = View.inflate(mContext, R.layout.popup_payment_mode, null);
            RelativeLayout rl_popup = (RelativeLayout) view.findViewById(R.id.rl_popup);
            rl_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new ColorDrawable(0xb0000000));
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int height = view.findViewById(R.id.rl_popup).getTop();
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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            update();

            ImageView del = (ImageView) view.findViewById(R.id.iv_del_pop);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            final RadioButton rbAlipay = (RadioButton) view.findViewById(R.id.rb_alipay);
            final RadioButton rbWeChatPay = (RadioButton) view.findViewById(R.id.rb_wechat_pay);
            final RadioButton rbUnionPay = (RadioButton) view.findViewById(R.id.rb_unionpay);
            Button btnPayment = (Button) view.findViewById(R.id.btn_payment);
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rbAlipay.isChecked()) {
                        recharge(orderNum, Constants.ALIPAY_APP);
                        dismiss();
                    } else if (rbWeChatPay.isChecked()) {
                        recharge(orderNum, Constants.TENGPAY_APP);
                        dismiss();
                    } else if (rbUnionPay.isChecked()) {
                        recharge(orderNum, Constants.UNIONPAY_APP);
                        dismiss();
                    }
                }
            });
        }
    }

    /**
     * 积分充值
     *
     * @param orderNum
     * @param paymentMode AlipayApp 支付宝APP支付 TengPayApp 微信APP支付
     */
    private void recharge(String orderNum, String paymentMode) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("payMethod", paymentMode);
            jsonParams.put("payType", "ApplyMerchantPay");
            //jsonParams.put("amount", scoreNumebr);
            jsonParams.put("objId", orderNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (paymentMode) {
            case Constants.ALIPAY_APP:
                HttpUtils.doPost(Api.USER_SCORE_RECHARGE, jsonParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.e(TAG, result);

                        final RechargeBean rechargeBean = GsonUtil.GsonToBean(result, RechargeBean.class);
                        //支付宝支付
                        aliPay(rechargeBean);
                    }
                });
                break;
            case Constants.TENGPAY_APP:
                HttpUtils.doPost(Api.USER_SCORE_RECHARGE, jsonParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.e(TAG, result);

                        final RechargeBean rechargeBean = GsonUtil.GsonToBean(result, RechargeBean.class);
                        //微信支付
                        wechatPay(rechargeBean);
                    }
                });
                break;
            case Constants.UNIONPAY_APP:
                Toast.makeText(ShopsJoinDataActivity.this, "银联支付暂未开通", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 充值-微信支付处理
     *
     * @param rechargeBean 充值参数实体类
     */
    private void wechatPay(RechargeBean rechargeBean) {
        // 通过WXAPIFactory工厂，获取IWXAPI实例
        IWXAPI api = WXAPIFactory.createWXAPI(ShopsJoinDataActivity.this, Constants.WETCHAT_APP_ID, true);
        api.registerApp(Constants.WETCHAT_APP_ID);
        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            // 将应用的appId注册到微信
            req.appId = Constants.WETCHAT_APP_ID;
            req.partnerId = rechargeBean.getData().wxdata.partnerid;
            req.prepayId = rechargeBean.getData().wxdata.prepayid;
            req.packageValue = "Sign=WXPay";
            req.nonceStr = rechargeBean.getData().wxdata.noncestr;
            req.timeStamp = rechargeBean.getData().wxdata.timestamp;
            req.sign = rechargeBean.getData().wxdata.sign;
            api.sendReq(req);
        } else {
            Toast.makeText(ShopsJoinDataActivity.this, "您的手机不支持微信支付！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 支付宝成功返回码
     */
    public static final String SDK_PAY_FLAG_SUCCESS = "9000";
    /**
     * 支付宝消息通知
     */
    private static final int SDK_PAY_FLAG = 10000;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResultBean payResult = new PayResultBean((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, SDK_PAY_FLAG_SUCCESS)) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ShopsJoinDataActivity.this, "商家入驻申请提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ShopsJoinDataActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 充值-支付宝支付处理
     *
     * @param rechargeBean 充值参数实体类
     */
    private void aliPay(final RechargeBean rechargeBean) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ShopsJoinDataActivity.this);
                Map<String, String> result = alipay.payV2(rechargeBean.getData().orderStr, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
