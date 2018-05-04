package com.weishi.yiye.activity.mine;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.luck.picture.lib.tools.LightStatusBarUtils;
import com.luck.picture.lib.tools.ToolbarUtil;
import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.InviteFriendBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.common.util.UIUtil;
import com.weishi.yiye.databinding.ActivityInviteFriendBinding;
import com.yzq.zxinglibrary.common.QRCodeUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：邀请好友（美业新需求改成我的团队页面）
 * @Version:v1.0.0
 *****************************/

public class InviteFriendActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = InviteFriendActivity.class.getSimpleName();
    private ActivityInviteFriendBinding inviteFriendBinding;
    protected Handler handler = new Handler();
    private String invitationCode = "W7SK";
    private String inviteFriendUrl;

    @Override
    protected void initView() {
        inviteFriendBinding = DataBindingUtil.setContentView(InviteFriendActivity.this, R.layout.activity_invite_friend);

        LightStatusBarUtils.setLightStatusBar(this, false);
        ToolbarUtil.setColor(InviteFriendActivity.this, getResources().getColor(R.color.main_text_color));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        inviteFriendBinding.sdvUserHead.setImageURI(Uri.parse(SPUtils.getInstance().getString(Constants.AVATAR, "")));
        inviteFriendBinding.tvUserName.setText(SPUtils.getInstance().getString(Constants.NICKNAME, ""));
        inviteFriendBinding.tvRoleName.setText(SPUtils.getInstance().getString(Constants.ROLE_NAME, ""));
        inviteFriendBinding.tvCommission.setOnClickListener(this);
        inviteFriendBinding.tvFirstAgency.setOnClickListener(this);
        inviteFriendBinding.tvSecondAgency.setOnClickListener(this);
        inviteFriendBinding.tvCopy.setOnClickListener(this);
        inviteFriendBinding.btnInviteFriend.setOnClickListener(this);

        getUserInviteInfo();
    }

    private void getUserInviteInfo() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));

        HttpUtils.doGet(Api.USER_INVITING_FRIENDS, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final InviteFriendBean inviteFriendBean = GsonUtil.GsonToBean(result, InviteFriendBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != inviteFriendBean && null != inviteFriendBean.getData()) {
                            invitationCode = inviteFriendBean.getData().getInvitationCode();
                            inviteFriendBinding.tvInvitationCode.setText(invitationCode);
                            inviteFriendBinding.tvCommissionMoney.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(inviteFriendBean.getData().getProfitCount() * 1d / 10000));
                            createQRCode();
                        }
                    }
                });
            }
        });
    }

    private void createQRCode() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, mSp.getString(ConfigConstants.INVITED_REGISTER, "") + "?nickName=" + mSp.getString(Constants.NICKNAME, "") + "&inviteCode=" + invitationCode);
                inviteFriendUrl = mSp.getString(ConfigConstants.INVITED_REGISTER, "") + "?nickName=" + mSp.getString(Constants.NICKNAME, "") + "&inviteCode=" + invitationCode;
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(inviteFriendUrl, BGAQRCodeUtil.dp2px(InviteFriendActivity.this, 150));
                inviteFriendBinding.sdvQrcode.setImageBitmap(QRCodeUtil.addLogo(bitmap, UIUtil.readBitMap(InviteFriendActivity.this, R.mipmap.ic_launcher)));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commission:
                startActivity(new Intent(InviteFriendActivity.this, CommissionActivity.class));
                break;
            case R.id.tv_first_agency:
                startActivity(new Intent(InviteFriendActivity.this, AgentDataActivity.class)
                        .putExtra("userLeveType", 1));
                break;
            case R.id.tv_second_agency:
                startActivity(new Intent(InviteFriendActivity.this, AgentDataActivity.class)
                        .putExtra("userLeveType", 2));
                break;
            case R.id.tv_copy:
                copyInvitationCode();
                break;
            case R.id.btn_invite_friend:
                showShare();
                break;
            default:
                break;
        }
    }

    private void copyInvitationCode() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(inviteFriendBinding.tvInvitationCode.getText());
        Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
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
        oks.setText(mSp.getString(Constants.NICKNAME, "") + "邀请您加入众享美业");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://zhongxiang.oss-cn-shanghai.aliyuncs.com/0141325b-48f0-4da4-99b8-7dd9e0697565");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(inviteFriendUrl);
        //oks.setUrl("http://yiye-h5-test.lianqumall.com/yylmh5/html/register.html?inviteCode=" + invitationCode);
        // 启动分享GUI
        oks.show(this);
    }
}
