package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weishi.yiye.R;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.ShopsJoinInfoBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityShopsJoinInfoBinding;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/5/8
 * @Description：商家入驻资料信息展示
 * @Version:v1.0.0
 *****************************/

public class ShopsJoinInfoActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ShopsJoinInfoActivity.class.getSimpleName();

    private ActivityShopsJoinInfoBinding shopsJoinInfoBinding;

    private List<String> imgList = new ArrayList<>();
    private MyPagerAdapter pagerAdapter;
    private MyPageChangeListener pageChangeListener;

    private ViewGroup.LayoutParams para;

    @Override
    protected void initView() {
        shopsJoinInfoBinding = DataBindingUtil.setContentView(ShopsJoinInfoActivity.this, R.layout.activity_shops_join_info);
        setTitleCenter("店铺信息");

        //解决华为虚拟按键遮挡布局底部按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        para = shopsJoinInfoBinding.rlImgInfo.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 848 / 1080;
        shopsJoinInfoBinding.rlImgInfo.setLayoutParams(para);

        pagerAdapter = new MyPagerAdapter();
        pageChangeListener = new MyPageChangeListener();
        shopsJoinInfoBinding.vpBanner.setAdapter(pagerAdapter);
        shopsJoinInfoBinding.vpBanner.setOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected void initData() {
        getJoinInfo();
    }

    private void getJoinInfo() {
        HttpUtils.doGet(Api.FIND_BUSI_APPLY_INFO + "/" + mSp.getInt(Constants.USER_ID, 0), null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, result);

                final ShopsJoinInfoBean shopsJoinInfoBean = GsonUtil.GsonToBean(result, ShopsJoinInfoBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(shopsJoinInfoBean.getCode())) {
                            for (int i = 0; i < shopsJoinInfoBean.getData().getAlternateImg().split(";").length; i++) {
                                Log.e(TAG, shopsJoinInfoBean.getData().getAlternateImg().split(";")[i]);
                                imgList.add(shopsJoinInfoBean.getData().getAlternateImg().split(";")[i]);
                            }
                            shopsJoinInfoBinding.tvVpIndicator.setText("1/" + imgList.size());
                            pagerAdapter.notifyDataSetChanged();

                            shopsJoinInfoBinding.sdvLogo.setImageURI(Uri.parse(shopsJoinInfoBean.getData().getStoreLogo()));
                            shopsJoinInfoBinding.etShopsName.setText(shopsJoinInfoBean.getData().getStoreName() + "");
                            shopsJoinInfoBinding.etShopkeeperName.setText(shopsJoinInfoBean.getData().getShopkeeperName() + "");
                            shopsJoinInfoBinding.etShopkeeperIdentity.setText(shopsJoinInfoBean.getData().getIdCard() + "");
                            if (shopsJoinInfoBean.getData().getBusiParentType() != -1) {
                                if (shopsJoinInfoBean.getData().getBusinessSortType() != -1) {
                                    shopsJoinInfoBinding.tvChooseShopsResult.setText(shopsJoinInfoBean.getData().getBusiFatherTypeName() + "-" + shopsJoinInfoBean.getData().getBusiParentTypeName() + "-" + shopsJoinInfoBean.getData().getBusinessSortTypeName());
                                } else {
                                    shopsJoinInfoBinding.tvChooseShopsResult.setText(shopsJoinInfoBean.getData().getBusiFatherTypeName() + "-" + shopsJoinInfoBean.getData().getBusiParentTypeName());
                                }
                            } else {
                                shopsJoinInfoBinding.tvChooseShopsResult.setText(shopsJoinInfoBean.getData().getBusiFatherTypeName());
                            }
                            shopsJoinInfoBinding.tvShopArea.setText(shopsJoinInfoBean.getData().getProvinceName() +
                                    shopsJoinInfoBean.getData().getCityName() +
                                    shopsJoinInfoBean.getData().getAreaName());
                            shopsJoinInfoBinding.etShopsAddress.setText(shopsJoinInfoBean.getData().getAddress() + "");
                            shopsJoinInfoBinding.tvChooseLocation.setText("经度：" + new DecimalFormat("#0.0000").format(shopsJoinInfoBean.getData().getLng()) +
                                    "；纬度：" + new DecimalFormat("#0.0000").format(shopsJoinInfoBean.getData().getLat()));
                            shopsJoinInfoBinding.etShopkeeperMobile.setText(shopsJoinInfoBean.getData().getMobile() + "");
                            shopsJoinInfoBinding.etShopsRecNo.setText(shopsJoinInfoBean.getData().getInviteCode() + "");
                        } else {
                            Toast.makeText(ShopsJoinInfoActivity.this, shopsJoinInfoBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    /**
     * 自定义pageradapter  适配viewpager
     */
    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            SimpleDraweeView sdv = new SimpleDraweeView(container.getContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            sdv.setLayoutParams(lp);

            sdv.setImageURI(Uri.parse(imgList.get(position)));
            sdv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            (container).addView(sdv);
            return sdv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author xieqinghua
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            shopsJoinInfoBinding.tvVpIndicator.setText((position + 1) + "/" + imgList.size());
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }
}
