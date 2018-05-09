package com.weishi.yiye.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.order.ImageActivity;
import com.weishi.yiye.bean.CommentBean;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.FullyGridLayoutManager;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/3
 * @Description：评论Adapter
 * @Version:v1.0.0
 *****************************/

public class CommentsAdapter extends CommonAdapter<CommentBean.DataBean.ListBean> {
    private Activity context;

    public CommentsAdapter(Activity context, List<CommentBean.DataBean.ListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public CommentsAdapter(Activity context, int layoutId) {
        super(context, layoutId);
        this.context = context;
    }

    @Override
    public void setData(List<CommentBean.DataBean.ListBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, final CommentBean.DataBean.ListBean dataBean, int position) {
        holder.setDraweeViewImage(R.id.sdv_head, dataBean.getAvatar());
        holder.setText(R.id.tv_comment_name, dataBean.getNickName());
        holder.setText(R.id.tv_comment_time, TimeUtils.millis2String(dataBean.getCreatetime(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())));
        holder.setStar(R.id.rb, dataBean.getCommentLv());
        holder.setText(R.id.tv_grade, new DecimalFormat("#0.0").format(dataBean.getCommentLv()) + "分");
        holder.setText(R.id.tv_comment_content, dataBean.getContent());

        if (dataBean.getCommentImgList() != null && dataBean.getCommentImgList().size() != 0) {
            RecyclerView recyclerView = holder.getView(R.id.recycler);
            recyclerView.setVisibility(View.VISIBLE);
            FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            CommentsImageAdapter adapter = new CommentsImageAdapter(mContext);
            adapter.setList(dataBean.getCommentImgList());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new CommentsImageAdapter.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(int position, View v) {
                                                   if (dataBean.getCommentImgList().size() > 0) {
                                                       Intent intent = new Intent(mContext, ImageActivity.class);
                                                       intent.putExtra(Constants.IMAGE_LIST, (Serializable) dataBean.getCommentImgList());
                                                       intent.putExtra(Constants.CURRENT_IMG_POSITION, position);
                                                       context.startActivity(intent);
                                                       context.overridePendingTransition(com.luck.picture.lib.R.anim.a5, 0);
                                                   }
                                               }
                                           }
            );
        } else {
            RecyclerView recyclerView = holder.getView(R.id.recycler);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
