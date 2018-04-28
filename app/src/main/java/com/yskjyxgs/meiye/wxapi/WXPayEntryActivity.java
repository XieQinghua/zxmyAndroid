package com.yskjyxgs.meiye.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weishi.yiye.bean.eventbus.ChangeScoreEvent;
import com.weishi.yiye.common.Constants;

import org.greenrobot.eventbus.EventBus;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/2
 * @Description：微信支付回调
 * @Version:v1.0.0
 *****************************/

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.recharge_succeed);

        api = WXAPIFactory.createWXAPI(this, Constants.WETCHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        int code = resp.errCode;

        if (code == 0) {
            //显示充值成功的页面和需要的操作
            Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

            EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.SCORE_BUY));

            finish();
        }
        if (code == -1) {
            Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            //错误
            finish();
        }
        if (code == -2) {
            //用户取消
            Toast.makeText(WXPayEntryActivity.this, "取消支付", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
