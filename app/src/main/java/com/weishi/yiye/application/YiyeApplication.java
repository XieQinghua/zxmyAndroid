package com.weishi.yiye.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.MD5Util;


/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/9
 * @Description：
 * @Version:v1.0.0
 *****************************/

public class YiyeApplication extends Application {
    private final static String TAG = YiyeApplication.class.getSimpleName();
    public static Context mContext;

    public static LocationListBean locationListBean;

    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        /**初始化bugly*/
        CrashReport.initCrashReport(this, Constants.BUGLY_ID, false);
        //CrashReport.testJavaCrash();
        /**初始化工具类类库**/
        Utils.init(this);
        /**初始化图片加载库**/
        ImagePipelineConfig frescoConfig = ImagePipelineConfig.newBuilder(getApplicationContext()).setDownsampleEnabled(true).build();
        Fresco.initialize(this, frescoConfig);
        /**初始化分享插件*/
        MobSDK.init(this);
        /**注册微信*/
        registerToWX();

        Log.i(TAG, "MD5 = " + MD5Util.getSign(this, this.getPackageName()));
        Log.i(TAG, "SHA1 = " + AppUtils.getAppSignatureSHA1());
        Log.i(TAG, "ScreenWidth=" + ScreenUtils.getScreenWidth() + "px；ScreenHeight=" + ScreenUtils.getScreenHeight() + "px");
    }

    public static Context getContext() {
        return mContext;
    }

    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, false);
        //将该app注册到微信
        mWxApi.registerApp(Constants.WEIXIN_APP_ID);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(context);
    }
}
