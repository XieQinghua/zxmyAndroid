package com.weishi.yiye.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.ErrorJavaScript;
import com.weishi.yiye.common.util.NetUtils;

import cn.sharesdk.onekeyshare.OnekeyShare;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/11
 * @Description：公共WebView加载界面(H5页面)
 * @Version:v1.0.0
 *****************************/

public class CommonWebViewActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    private String advTitle;
    private String advUrl;
    private ImageView iv_right;
    private WebView web_shop_details;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    loadingUrl(advUrl);
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        setContentView(R.layout.activity_common_webview);

        advUrl = getIntent().getStringExtra(Constants.INTENT_COMMON_ADV_URL);
        advTitle = getIntent().getStringExtra(Constants.INTENT_COMMON_ADV_TITLE);
        setTitleCenter(advTitle);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
        iv_right.setImageResource(R.drawable.icon_dark_share);
        iv_right.setOnClickListener(this);

        web_shop_details = (WebView) findViewById(R.id.web_view_details);
        loadingUrl(advUrl);
    }

    @Override
    protected void initData() {

    }

    /**
     * 加载链接
     */
    private void loadingUrl(String url) {
        //设置字符集编码
        web_shop_details.getSettings().setDefaultTextEncodingName("UTF-8");

        web_shop_details.getSettings().setAllowFileAccess(true);
        web_shop_details.getSettings().setUseWideViewPort(true);
        web_shop_details.getSettings().setLoadWithOverviewMode(true);

        //开启JavaScript支持
        web_shop_details.getSettings().setJavaScriptEnabled(true);
        String userAgent = web_shop_details.getSettings().getUserAgentString();
        web_shop_details.getSettings().setUserAgentString(userAgent);
        web_shop_details.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web_shop_details.getSettings().setAllowFileAccess(true);
        web_shop_details.getSettings().setAppCacheEnabled(true);
        web_shop_details.getSettings().setDomStorageEnabled(true);
        web_shop_details.getSettings().setDatabaseEnabled(true);

        //判断网络状态
        if (NetUtils.isNetworkConnected(CommonWebViewActivity.this)) {

            web_shop_details.loadUrl(url);
            //监听webwiew的下载进度
            web_shop_details.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            });

        } else {
            //进行无网络或错误处理
            Toast.makeText(CommonWebViewActivity.this, "网络连接异常，请检查网络", Toast.LENGTH_SHORT).show();
            web_shop_details.loadUrl("file:///android_asset/error.html");
            web_shop_details.addJavascriptInterface(new ErrorJavaScript(CommonWebViewActivity.this, handler), "android");
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("众享美业");
        // titleUrl QQ和QQ空间跳转链接
        //oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://my.lianqumall.com/img/zc/logo.png");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(advUrl);
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                showShare();
                break;
            default:
                break;
        }
    }
}
