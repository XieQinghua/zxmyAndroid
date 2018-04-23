package com.weishi.yiye.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：重新加载交互类
 * @Version:v1.0.0
 *****************************/
public class ErrorJavaScript {
    private Handler handler = null;
    private Context context;

    public ErrorJavaScript(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
    }

    /**
     * 重新加载
     */
    @JavascriptInterface
    public void Reload() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
