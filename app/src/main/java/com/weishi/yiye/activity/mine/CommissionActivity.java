package com.weishi.yiye.activity.mine;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.weishi.yiye.R;
import com.weishi.yiye.adapter.CommissionAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommissionBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityCommissionBinding;

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
 * @Date：2018/3/30
 * @Description：佣金收益
 * @Version:v1.0.0
 *****************************/
public class CommissionActivity extends BaseSwipeBackActivity {
    private static final String TAG = CommissionActivity.class.getSimpleName();
    private ActivityCommissionBinding commentBinding;

    protected Handler mHandler = new Handler();
    private CommissionAdapter commissionAdapter;
    private List<CommissionBean.DataBean.CommissionDatasBean> datas = new ArrayList<>();

    @Override
    protected void initView() {
        commentBinding = DataBindingUtil.setContentView(CommissionActivity.this, R.layout.activity_commission);

        setTitleCenter("佣金收益");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        commissionAdapter = new CommissionAdapter(CommissionActivity.this, R.layout.item_commission);
        commentBinding.lvCommission.setAdapter(commissionAdapter);
    }

    @Override
    protected void initData() {
        getMyAward();
    }

    private void getMyAward() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("uid", mSp.getInt(Constants.USER_ID, 0));
        mapParams.put("currentPage", 1);
        mapParams.put("pageSize", 10);

        HttpUtils.doGet(Api.GET_COMMISSION, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);
                final CommissionBean commissionBean = GsonUtil.GsonToBean(result, CommissionBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commissionBean.getCode()) &&
                                commissionBean.getData().getCommissionDatas() != null &&
                                commissionBean.getData().getCommissionDatas().size() != 0) {
                            datas = commissionBean.getData().getCommissionDatas();
                            commissionAdapter.setData(datas);
                            commissionAdapter.notifyDataSetChanged();
                        } else {
                            commentBinding.lvCommission.setVisibility(View.GONE);
                            commentBinding.tvNoCommission.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}
