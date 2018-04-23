/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.weishi.yiye.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.bean.LoginBean;
import com.weishi.yiye.bean.WeiXinBindBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 备注：这个类用于微信回调，位置不可更改，包名不可更改，类名不可更改
 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {
    private static final String TAG = WXEntryActivity.class.getSimpleName();

    private Context mContext;
    private static boolean myBindWX;
    private String code;
    public static WXLoginStateListener myWXLoginStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //这句没有写,是不能执行回调的方法的
        YiyeApplication.mWxApi.handleIntent(getIntent(), this);
    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p/>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    @Override
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p/>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p/>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    @Override
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 微信组件注册初始化
     *
     * @param context 上下文
     * @param api     微信服务api
     * @return 微信组件api对象
     * <p>
     * /
     * public static IWXAPI initWeiXin(Context context, @NonNull String weixin_app_id) {
     * if (TextUtils.isEmpty(weixin_app_id)) {
     * Toast.makeText(context.getApplicationContext(), "app_id 不能为空", Toast.LENGTH_SHORT).show();
     * }
     * IWXAPI api = WXAPIFactory.createWXAPI(context, weixin_app_id, true);
     * api.registerApp(weixin_app_id);
     * return api;
     * }
     * <p>
     * /**
     * 登录微信
     */
    public static void wxLogin(Context context, IWXAPI api, boolean isBindWX, WXLoginStateListener wxLoginStateListener) {
        myBindWX = isBindWX;
        myWXLoginStateListener = wxLoginStateListener;
        // 判断是否安装了微信客户端
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context.getApplicationContext(), "您还未安装微信客户端！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 发送授权登录信息，来获取code
        SendAuth.Req req = new SendAuth.Req();
        // 应用的作用域，获取个人信息
        req.scope = "snsapi_userinfo";
        /**
         * 用于保持请求和回调的状态，授权请求后原样带回给第三方
         * 为了防止csrf攻击（跨站请求伪造攻击），后期改为随机数加session来校验
         */
        req.state = "app_wechat";
        api.sendReq(req);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            // 发送成功
            case BaseResp.ErrCode.ERR_OK:
                // 获取code
                code = ((SendAuth.Resp) baseResp).code;
                Log.e(TAG, "code=" + code);
                SendAuth.Resp response = (SendAuth.Resp) baseResp;
                if (response.state == null) {
                    return;// 判断请求是否是我的应用的请求
                }
                if (myBindWX) {
                    bindWeixin(code);
                } else {
                    login(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.e(TAG, "=====================取消================================");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.e(TAG, "=====================拒绝================================");
                break;
            default:
                break;
        }
    }

    /**
     * 登陆
     */
    private void login(String code) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("loginName", "weixin");
            jsonParams.put("loginPasswd", code);
            jsonParams.put("loginType", "LOGIN_BY_WX");
            jsonParams.put("inviteCode", "SYSTEM");
            jsonParams.put("operType", 2);
            jsonParams.put("channel", "APP");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.doPost(Api.USER_LOGIN, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Log.e(TAG, result);

                final LoginBean loginBean = GsonUtil.GsonToBean(result, LoginBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(loginBean.getCode())) {
                            //保存数据
                            SPUtils.getInstance().putBoolean(Constants.IS_LOGIN, true);
                            if (!"weixin".equals(loginBean.getData().getUserInfo().getPhone())) {
                                SPUtils.getInstance().putString(Constants.PHONE, loginBean.getData().getUserInfo().getPhone());
                            }
                            SPUtils.getInstance().putInt(Constants.SEX, loginBean.getData().getUserInfo().getSex());
                            SPUtils.getInstance().putString(Constants.NICKNAME, loginBean.getData().getUserInfo().getNickname());
                            SPUtils.getInstance().putInt(Constants.USER_TYPE, loginBean.getData().getUserInfo().getUserType());
                            SPUtils.getInstance().putString(Constants.AVATAR, loginBean.getData().getUserInfo().getAvatar());
                            SPUtils.getInstance().putInt(Constants.USER_ID, loginBean.getData().getUserInfo().getUserId());
                            SPUtils.getInstance().putString(Constants.TOKEN, loginBean.getData().getToken());
                            SPUtils.getInstance().putBoolean(Constants.IS_EXISTS, loginBean.getData().isIsExists());
                            //SPUtils.getInstance().putBoolean(Constants.IS_BIND, loginBean.getData().isIsBind());

                            SPUtils.getInstance().putString(Constants.LOGIN_TYPE, "LOGIN_BY_WX");
                            //登录成功
                            myWXLoginStateListener.onSuccess();
                        } else {
                            myWXLoginStateListener.onFail(loginBean.getMessage());
                        }
                    }
                });
            }
        });
    }

    /**
     * 绑定微信
     *
     * @param code
     */
    private void bindWeixin(String code) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        mapParams.put("wxCode", code);
        mapParams.put("channel", "APP");
        HttpUtils.doGet(Api.WEIXIN_BIND, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Log.e(TAG, result);

                final WeiXinBindBean weiXinBindBean = GsonUtil.GsonToBean(result, WeiXinBindBean.class);
                if (Api.STATE_SUCCESS.equals(weiXinBindBean.getCode())) {
                    if (weiXinBindBean.getData() != null) {
                        SPUtils.getInstance().putInt(Constants.USER_ID, weiXinBindBean.getData().getUid());
                        SPUtils.getInstance().putString(Constants.TOKEN, weiXinBindBean.getData().getToken());
                    }
                    myWXLoginStateListener.onSuccess();
                } else {
                    myWXLoginStateListener.onFail(weiXinBindBean.getMessage());
                }
            }
        });
    }

    public interface WXLoginStateListener {

        void onSuccess();

        void onFail(String errorMsg);
    }

}
