package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.MyAwardBean;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/25
 * @Description：我的奖励Adapter
 * @Version:v1.0.0
 *****************************/

public class MyAwardAdapter extends CommonAdapter<MyAwardBean.DataBean> {

    public MyAwardAdapter(Context context, List<MyAwardBean.DataBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public MyAwardAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<MyAwardBean.DataBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, MyAwardBean.DataBean myAwardBean, int position) {
        holder.setText(R.id.tv_award_name, myAwardBean.getNickname());
        holder.setText(R.id.tv_award_score, "+" + new DecimalFormat("#0.00").format(myAwardBean.getProfitScore() * 1d / 10000));
        holder.setText(R.id.tv_award_time, myAwardBean.getOccurTime());
    }
}
