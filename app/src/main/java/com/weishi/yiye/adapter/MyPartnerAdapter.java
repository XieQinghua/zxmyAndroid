package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.MyPartnerBean;

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

public class MyPartnerAdapter extends CommonAdapter<MyPartnerBean.DataBean> {

    public MyPartnerAdapter(Context context, List<MyPartnerBean.DataBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public MyPartnerAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<MyPartnerBean.DataBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, MyPartnerBean.DataBean myPartnerBean, int position) {
        if (position % 2 == 0) {
            holder.setBackGround(R.id.ll_content, R.color.white);
        } else {
            holder.setBackGround(R.id.ll_content, R.color.main_bk);
        }
        holder.setText(R.id.tv_number, String.format("%03d", position + 1));
        holder.setText(R.id.tv_partner_name, myPartnerBean.getNickname());
        holder.setText(R.id.tv_partner_score, new DecimalFormat("#0.00").format(myPartnerBean.getCountScore() * 1d / 10000));
        holder.setText(R.id.tv_partner_time, myPartnerBean.getRegisterTime());
    }
}
