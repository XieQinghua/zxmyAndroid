package com.weishi.yiye.activity.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.CommonWebViewActivity;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.databinding.ActivityAboutUsBinding;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/19
 * @Description：意见反馈
 * @Version:v1.0.0
 *****************************/

public class AboutUsActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private ActivityAboutUsBinding activityAboutUsBinding;

    @Override
    protected void initView() {
        activityAboutUsBinding = DataBindingUtil.setContentView(AboutUsActivity.this, R.layout.activity_about_us);

        setTitleCenter("关于我们");

        activityAboutUsBinding.tvVersion.setText("版本号：V" + AppUtils.getAppVersionName());
        activityAboutUsBinding.tvLicenseAgreement.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_license_agreement:
                //跳转服务协议页面
                Intent intent = new Intent(AboutUsActivity.this, CommonWebViewActivity.class);
                intent.putExtra(Constants.INTENT_COMMON_ADV_URL, mSp.getString(ConfigConstants.USER_AGREEMENT, ""));
                intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, getString(R.string.license_agreement));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
