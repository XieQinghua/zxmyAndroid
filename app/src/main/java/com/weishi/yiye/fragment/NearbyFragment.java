package com.weishi.yiye.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.SearchActivity;
import com.weishi.yiye.activity.nearby.LocationListActivity;
import com.weishi.yiye.adapter.FragmentTabAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.ThreeShopTypeBean;
import com.weishi.yiye.bean.eventbus.LocationStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.fragment.nearby.NearbyClassFragment;

import org.greenrobot.eventbus.EventBus;
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
 * @Author：zym
 * @Date：2018/1/9
 * @Description：附近
 * @Version:v1.0.0
 *****************************/
public class NearbyFragment extends BaseFragment implements View.OnClickListener,
        RefreshLoadMoreLayout.CallBack {
    private static final String TAG = NearbyFragment.class.getSimpleName();

    private static final int REQUEST_NEARBY = 1;

    private TextView tv_nearby_address;

    protected RadioGroup rg;

    public FragmentTabAdapter tabAdapter;

    /**
     * TabLayout标签
     */
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        tv_nearby_address = findView(R.id.tv_nearby_address);
        if (YiyeApplication.locationListBean != null) {
            tv_nearby_address.setText(YiyeApplication.locationListBean.getName());
        } else {
            tv_nearby_address.setText("定位中..");
        }

        rg = findView(R.id.rg);

        findView(R.id.tv_nearby_address).setOnClickListener(this);
        findView(R.id.tv_search).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //初始化TabLayout
        initTableLayout();
    }

    private void initTableLayout() {
        Map<String, Object> mapParams = new HashMap<>();
        HttpUtils.doGet(Api.GET_ALL_SORT, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                if (getActivity() == null) {
                    return;
                }
                final ThreeShopTypeBean threeShopTypeBean = GsonUtil.GsonToBean(result, ThreeShopTypeBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (threeShopTypeBean != null && threeShopTypeBean.data != null && threeShopTypeBean.data.size() > 0) {
                            if (fragments.size() == threeShopTypeBean.data.size()) {
                                //如果已经加载则不重复加载( Can not perform this action after onSaveInstanceState )
                                return;
                            }
                            for (int i = 0; i < threeShopTypeBean.data.size(); i++) {
                                final ThreeShopTypeBean.DataBean dataBean = threeShopTypeBean.data.get(i);
                                RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.item_tab_radiobutton, null).findViewById(R.id.rb);
                                rb.setId(1000 + i);
                                rg.addView(rb);

                                rb.setText(dataBean.typeName);
                                ViewGroup.LayoutParams para = rb.getLayoutParams();
                                para.width = ScreenUtils.getScreenWidth() / 5;
                                para.height = SizeUtils.dp2px(45);
                                rb.setLayoutParams(para);

                                if (i == 0) {
                                    rg.check(rb.getId());
                                }
                                fragments.add(new NearbyClassFragment(dataBean.sortId, (ArrayList<ThreeShopTypeBean.DataBean.TwoProperty>) dataBean.property));
                            }
                            tabAdapter = new FragmentTabAdapter(getActivity(), getActivity().getSupportFragmentManager(),
                                    fragments, R.id.nearby_content, rg);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_nearby_address:
                if (YiyeApplication.locationListBean != null) {
                    intent = new Intent(getActivity(), LocationListActivity.class);
                    startActivityForResult(intent, REQUEST_NEARBY);
                } else {
                    Toast.makeText(getActivity(), "请先打开定位权限", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationState(LocationStateEvent locationStateEvent) {
        switch (locationStateEvent.getState()) {
            case LocationStateEvent.SUCCESS:
                tv_nearby_address.setText(YiyeApplication.locationListBean.getName());
                initTableLayout();
                break;
            case LocationStateEvent.FAIL:
                tv_nearby_address.setText("定位中..");
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            LocationListBean locationListBean = (LocationListBean) data.getSerializableExtra("LocationListBean");
            switch (requestCode) {
                case REQUEST_NEARBY:
                    tv_nearby_address.setText(locationListBean.getName());
                    //Toast.makeText(getActivity(), locationListBean.getName(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        initTableLayout();
    }

    @Override
    public void onLoadMore() {
        initTableLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
