package com.weishi.yiye.activity.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.CommonWebViewActivity;
import com.weishi.yiye.adapter.HeadlineAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.HeadlineBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityHeadlineBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/28.
 * 美业快报
 */

public class HeadlineActivity extends BaseSwipeBackActivity implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = HeadlineActivity.class.getSimpleName();
    private ActivityHeadlineBinding headlineBinding;
    private HeadlineAdapter headlineAdapter;
    private List<HeadlineBean.DataBean.ListBean> headlineList;

    protected Handler mHandler = new Handler();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected void initView() {
        headlineBinding = DataBindingUtil.setContentView(HeadlineActivity.this, R.layout.activity_headline);
        headlineBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        setTitleCenter("美业快报");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        headlineList = new ArrayList<>();
        headlineAdapter = new HeadlineAdapter(HeadlineActivity.this, R.layout.item_headline);
        headlineBinding.lvHeadline.setAdapter(headlineAdapter);
        headlineBinding.lvHeadline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HeadlineActivity.this, CommonWebViewActivity.class);
                intent.putExtra(Constants.INTENT_COMMON_ADV_URL, headlineList.get(i).getUrl());
                intent.putExtra(Constants.INTENT_COMMON_ADV_TITLE, headlineList.get(i).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        initHeadlineData(1);
    }

    private void initHeadlineData(final int pageNum) {
        startAnim(null);
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);

        HttpUtils.doGet(Api.GET_ARTICLE_LIST, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();
                String result = response.body().string();
                Log.e(TAG, result);

                final HeadlineBean headlineBean = GsonUtil.GsonToBean(result, HeadlineBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(headlineBean.getCode()) && headlineBean.getData() != null) {
                            if (pageNum == 1) {
                                if (headlineBean != null &&
                                        headlineBean.getData() != null &&
                                        headlineBean.getData().getList() != null &&
                                        headlineBean.getData().getList().size() != 0) {
                                    headlineList = headlineBean.getData().getList();
                                } else {
                                    headlineBinding.refreshloadmore.setVisibility(View.GONE);
                                    headlineBinding.tvNoComments.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (headlineBean != null &&
                                        headlineBean.getData() != null &&
                                        headlineBean.getData().getList() != null &&
                                        headlineBean.getData().getList().size() != 0) {
                                    headlineList.addAll(headlineBean.getData().getList());
                                }
                            }

                            hasNextPage = headlineBean.getData().isHasNextPage();

                            headlineAdapter.setData(headlineList);
                            headlineAdapter.notifyDataSetChanged();
                        } else if (Api.STATE_SUCCESS.equals(headlineBean.getCode())) {
                            if (headlineBean.getData() == null) {
                                Toast.makeText(HeadlineActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                            } else if (headlineBean.getData().getList() == null || headlineBean.getData().getList().size() == 0) {
                                Toast.makeText(HeadlineActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        initHeadlineData(pageNum);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                headlineBinding.refreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initHeadlineData(pageNum);
        } else {
            Toast.makeText(HeadlineActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                headlineBinding.refreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
