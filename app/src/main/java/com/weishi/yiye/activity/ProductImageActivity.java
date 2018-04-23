package com.weishi.yiye.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.FragmentTabAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.BusiTypeImageBean;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityProductImageBinding;
import com.weishi.yiye.fragment.ProductImageFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/29
 * @Description：商品图片页面
 * @Version:v1.0.0
 *****************************/
public class ProductImageActivity extends BaseSwipeBackActivity {
    private static final String TAG = ProductImageActivity.class.getSimpleName();
    private List<Fragment> fragments = new ArrayList<>();
    private ActivityProductImageBinding productImageBinding;
    public static FragmentTabAdapter tabAdapter;

    private int storeId;

    @Override
    protected void initView() {
        productImageBinding = DataBindingUtil.setContentView(ProductImageActivity.this, R.layout.activity_product_image);
        setTitleCenter("商品图片");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        storeId = getIntent().getIntExtra(ShopConstants.KEY_SHOP_ID, 1);
    }

    @Override
    protected void initData() {
        getBusiTypeImage();
    }

    /**
     * 查询店铺图片分类和个数
     */
    private void getBusiTypeImage() {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("storeId", storeId);

        HttpUtils.doGet(Api.GET_BUSI_TYPE_IMAGE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final BusiTypeImageBean busiTypeImageBean = GsonUtil.GsonToBean(result, BusiTypeImageBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(busiTypeImageBean.getCode()) &&
                                busiTypeImageBean.getData() != null &&
                                busiTypeImageBean.getData().size() != 0) {
                            int size = busiTypeImageBean.getData().size();
                            for (int i = 0; i < size; i++) {
                                RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.item_tab_radiobutton, null).findViewById(R.id.rb);
                                rb.setId(10000 + i);
                                productImageBinding.rg.addView(rb);

                                rb.setText(busiTypeImageBean.getData().get(i).getClassName() + "（" + busiTypeImageBean.getData().get(i).getNumber() + "）");
                                ViewGroup.LayoutParams para = rb.getLayoutParams();
                                para.width = ScreenUtils.getScreenWidth() / 3;
                                para.height = SizeUtils.dp2px(45);
                                rb.setLayoutParams(para);

                                int position = i;
                                if (position == 0) {
                                    productImageBinding.rg.check(rb.getId());
                                }

                                fragments.add(new ProductImageFragment(storeId, busiTypeImageBean.getData().get(i).getId(), busiTypeImageBean.getData().get(i).getNumber()));
                            }
                            tabAdapter = new FragmentTabAdapter(ProductImageActivity.this, getSupportFragmentManager(),
                                    fragments, R.id.content, productImageBinding.rg);
                        }
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadingState(LoadingStateEvent loadingStateEvent) {
        switch (loadingStateEvent.getType()) {
            case LoadingStateEvent.START_ANIM:
                startAnim(loadingStateEvent.getLoadingText());
                break;
            case LoadingStateEvent.STOP_ANIM:
                stopAnim();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
