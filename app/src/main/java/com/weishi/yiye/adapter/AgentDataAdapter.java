package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.AgentDataBean;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/30
 * @Description：用户代理Adapter
 * @Version:v1.0.0
 *****************************/

public class AgentDataAdapter extends CommonAdapter<AgentDataBean.DataBean.UserAgentDataItemsBean> {

    public AgentDataAdapter(Context context, List<AgentDataBean.DataBean.UserAgentDataItemsBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public AgentDataAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<AgentDataBean.DataBean.UserAgentDataItemsBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, AgentDataBean.DataBean.UserAgentDataItemsBean bean, int position) {
        holder.setDraweeViewImage(R.id.sdv_head, bean.getAvatar());
        holder.setText(R.id.tv_nickname, bean.getNickname());
        holder.setText(R.id.tv_first_subcount, "一级会员：" + bean.getFirstDisSub());
        holder.setText(R.id.tv_second_subcount, "");
        holder.setText(R.id.tv_commission, "佣金收益：" + mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(bean.getCountScore() / 10000));
//        holder.setText(R.id.tv_register_date, TimeUtils.millis2String(bean.getRegisterDate(),
//                new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())));
        holder.setText(R.id.tv_register_date, "注册时间：" + bean.getRegisterTime());
    }
}
