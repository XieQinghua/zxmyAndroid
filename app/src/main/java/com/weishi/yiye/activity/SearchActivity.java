package com.weishi.yiye.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.QueryShopsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.KeyWordBean;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.ShopQueryBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivitySearchBinding;
import com.weishi.yiye.view.ZFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/10
 * @Description：搜索页面
 * @Version:v1.0.0
 *****************************/
public class SearchActivity extends BaseSwipeBackActivity implements View.OnClickListener, RefreshLoadMoreLayout.CallBack {
    private static final String TAG = SearchActivity.class.getSimpleName();

    private ActivitySearchBinding searchBinding;

    private EditText et_search;
    private ImageView iv_empty;
    private TextView tv_cancel;

    private ZFlowLayout mFlowLayout;

    protected Handler mHandler = new Handler();
    protected RefreshLoadMoreLayout mRefreshloadmore;

    private View mHeader;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;
    private ListView lv_rec_shops;
    private QueryShopsAdapter mRecShopsAdapter;

    private List<QueryShopBean> shopsDatas = new ArrayList<QueryShopBean>();

    @Override
    protected void initView() {
        searchBinding = DataBindingUtil.setContentView(SearchActivity.this, R.layout.activity_search);

        et_search = searchBinding.etSearch;
        iv_empty = searchBinding.ivEmpty;
        tv_cancel = searchBinding.tvCancel;

        //获取编辑框焦点
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
        //打开软键盘
        Executors.newScheduledThreadPool(1)
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        InputMethodManager inputManager =
                                (InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(et_search, 0);
                    }
                }, 500, TimeUnit.MILLISECONDS);
        iv_empty.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        //监听EditText的文字变化
        clearText(et_search, iv_empty);
        //监听键盘搜索按键
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!et_search.getText().toString().trim().equals("")) {
                        search(et_search.getText().toString().trim());
                    } else {
                        Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        lv_rec_shops = searchBinding.lvRecShops;

        mHeader = LayoutInflater.from(SearchActivity.this).inflate(R.layout.activity_search_header, null);
        mFlowLayout = (ZFlowLayout) mHeader.findViewById(R.id.flowLayout);
        lv_rec_shops.addHeaderView(mHeader);

        mRefreshloadmore = searchBinding.refreshloadmore;
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        mRefreshloadmore.setCanRefresh(false);

        mRecShopsAdapter = new QueryShopsAdapter(SearchActivity.this, R.layout.item_rec_shops);
        lv_rec_shops.setAdapter(mRecShopsAdapter);
    }

    @Override
    protected void initData() {
        initLabel();
        //初始化店铺数据
        initShopData(1);
    }

    /**
     * 搜索
     *
     * @param keyword 关键词
     */
    private void search(String keyword) {
        // 先隐藏键盘
        ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SearchActivity.this
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

        Intent tempIntent = new Intent(SearchActivity.this, SearchResultActivity.class);
        tempIntent.putExtra(ShopConstants.KEY_SHOP_KEY_WORDS, keyword);
        startActivity(tempIntent);
    }

    /**
     * 初始化标签
     */
    @SuppressLint("ResourceAsColor")
    private void initLabel() {
        HttpUtils.doGet(Api.GET_HOT_SEARCH_KEYWORD, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        KeyWordBean keyWordBean = GsonUtil.GsonToBean(result, KeyWordBean.class);
                        if (Api.STATE_SUCCESS.equals(keyWordBean.getCode())) {
                            if (keyWordBean.getData() != null && keyWordBean.getData().size() != 0) {
                                setKeyWord(keyWordBean.getData());
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化店铺数据
     */
    private void initShopData(final int pageNum) {
        JSONObject jsonParams = new JSONObject();
        try {
            if (YiyeApplication.locationListBean != null) {
                jsonParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
                jsonParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
            } else {
                jsonParams.put("userLat", 0);
                jsonParams.put("userLng", 0);
            }
            jsonParams.put("pageNum", pageNum);
            jsonParams.put("pageSize", pageSize);
            jsonParams.put("status", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.GET_STORE_LIST_BY_STORE, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                final ShopQueryBean homeNearShopBean = GsonUtil.GsonToBean(result, ShopQueryBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "initShopData========");
                        if (Api.STATE_SUCCESS.equals(homeNearShopBean.getCode())) {
                            if (pageNum == 1) {
                                if (homeNearShopBean != null && homeNearShopBean.getData() != null && homeNearShopBean.getData().getList() != null) {
                                    shopsDatas = homeNearShopBean.getData().getList();
                                }
                            } else {
                                if (homeNearShopBean != null && homeNearShopBean.getData() != null && homeNearShopBean.getData().getList() != null) {
                                    shopsDatas.addAll(homeNearShopBean.getData().getList());
                                }
                            }

                            hasNextPage = homeNearShopBean.getData().isHasNextPage();

                            mRecShopsAdapter.setData(shopsDatas);
                            mRecShopsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    /**
     * 设置关键词
     *
     * @param keyWordList
     */
    @SuppressLint("ResourceAsColor")
    public void setKeyWord(final List<KeyWordBean.DataBean> keyWordList) {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置边距
        layoutParams.setMargins(10 * (int) getResources().getDimension(R.dimen.dp), 5 * (int) getResources().getDimension(R.dimen.dp),
                0, 5 * (int) getResources().getDimension(R.dimen.dp));
        for (int i = 0; i < keyWordList.size(); i++) {
            final TextView textView = new TextView(SearchActivity.this);
            textView.setTag(i);
            textView.setTextSize(14);
            textView.setText(keyWordList.get(i).getKeyword());
            textView.setPadding(10 * (int) getResources().getDimension(R.dimen.dp), 5 * (int) getResources().getDimension(R.dimen.dp),
                    10 * (int) getResources().getDimension(R.dimen.dp), 5 * (int) getResources().getDimension(R.dimen.dp));
            textView.setTextColor(R.color.app_text_color1);
            textView.setBackgroundResource(R.drawable.btn_search_label_shape);
            mFlowLayout.addView(textView, layoutParams);
            // 标签点击事件
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(keyWordList.get(finalI).getKeyword());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                //清空et_search
                et_search.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                SearchActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initShopData(pageNum);
        } else {
            Toast.makeText(SearchActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
