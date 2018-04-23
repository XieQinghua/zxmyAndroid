package com.weishi.yiye.activity;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.qbw.customview.RefreshLoadMoreLayout;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.CommentsAdapter;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommentBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityCommentsListBinding;

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
 * @Date：2018/3/3
 * @Description：评论列表界面
 * @Version:v1.0.0
 *****************************/
public class CommentsListActivity extends BaseSwipeBackActivity implements RefreshLoadMoreLayout.CallBack {
    private static final String TAG = CommentsListActivity.class.getSimpleName();
    private ActivityCommentsListBinding commentsListBinding;
    private CommentsAdapter commentsAdapter;
    private List<CommentBean.DataBean.ListBean> commentsList;

    protected Handler mHandler = new Handler();
    private String type;
    private int id;
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasNextPage = true;

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        id = getIntent().getIntExtra("id", 0);
        commentsListBinding = DataBindingUtil.setContentView(CommentsListActivity.this, R.layout.activity_comments_list);
        commentsListBinding.refreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        setTitleCenter("评论列表");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        commentsList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(CommentsListActivity.this, R.layout.item_comment);
        commentsListBinding.lvComments.setAdapter(commentsAdapter);
    }

    @Override
    protected void initData() {
        initCommentData(1);
    }

    private void initCommentData(final int pageNum) {
        startAnim(null);
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("pageNum", pageNum);
        mapParams.put("pageSize", pageSize);
        mapParams.put(type, id);

        HttpUtils.doGet(Api.GET_BATCH_USERINFO_BY_ID, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();
                String result = response.body().string();
                Log.e(TAG, result);

                final CommentBean commentBean = GsonUtil.GsonToBean(result, CommentBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commentBean.getCode()) && commentBean.getData() != null) {
                            if (pageNum == 1) {
                                if (commentBean != null &&
                                        commentBean.getData() != null &&
                                        commentBean.getData().getList() != null &&
                                        commentBean.getData().getList().size() != 0) {
                                    commentsList = commentBean.getData().getList();
                                } else {
                                    commentsListBinding.refreshloadmore.setVisibility(View.GONE);
                                    commentsListBinding.tvNoComments.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (commentBean != null &&
                                        commentBean.getData() != null &&
                                        commentBean.getData().getList() != null &&
                                        commentBean.getData().getList().size() != 0) {
                                    commentsList.addAll(commentBean.getData().getList());
                                }
                            }

                            hasNextPage = commentBean.getData().isHasNextPage();

                            commentsAdapter.setData(commentsList);
                            commentsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentsListBinding.refreshloadmore.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (hasNextPage) {
            pageNum++;
            initCommentData(pageNum);
        } else {
            Toast.makeText(CommentsListActivity.this, getString(R.string.bottom_line), Toast.LENGTH_SHORT).show();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentsListBinding.refreshloadmore.stopLoadMore();
            }
        }, 500);
    }
}
