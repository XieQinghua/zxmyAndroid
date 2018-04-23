package com.weishi.yiye.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.databinding.ActivityOpinionBinding;
import com.weishi.yiye.view.CustomDialog;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/19
 * @Description：意见反馈
 * @Version:v1.0.0
 *****************************/

public class OpinionActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private ActivityOpinionBinding opinionBinding;

    private CheckPermission checkPermission;

    @Override
    protected void initView() {
        opinionBinding = DataBindingUtil.setContentView(OpinionActivity.this, R.layout.activity_opinion);
        setTitleCenter("意见反馈");
        opinionBinding.tvServiceTel.setOnClickListener(this);
        opinionBinding.tvWechatOfficial.setOnClickListener(this);
        opinionBinding.tvUserId.setText(SPUtils.getInstance().getInt(Constants.USER_ID, 0) + "");

        checkPermission = new CheckPermission(OpinionActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                callPhone();
            }

            @Override
            public void negativeButton() {
                Toast.makeText(OpinionActivity.this, "没有权限无法拨打电话", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_service_tel:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_PHONE);
                } else {
                    callPhone();
                }
                break;
            case R.id.tv_wechat_official:
                //TODO 跳转微信服务号
                break;
            default:
                break;
        }
    }

    /**
     * 拨打客服电话
     */
    public void callPhone() {
        CustomDialog.Builder builder = new CustomDialog.Builder(OpinionActivity.this);
        builder.setMessage(getResources().getString(R.string.service_tel_number));
        builder.setTitleVisibility(View.GONE);
        builder.setPositiveButton("呼叫", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getResources().getString(R.string.service_tel_number)));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
