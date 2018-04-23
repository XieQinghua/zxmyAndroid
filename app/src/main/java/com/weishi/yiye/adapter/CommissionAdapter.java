package com.weishi.yiye.adapter;

import android.content.Context;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.CommissionBean;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/30
 * @Description：佣金收益Adapter
 * @Version:v1.0.0
 *****************************/

public class CommissionAdapter extends CommonAdapter<CommissionBean.DataBean.CommissionDatasBean> {

    public CommissionAdapter(Context context, List<CommissionBean.DataBean.CommissionDatasBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public CommissionAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<CommissionBean.DataBean.CommissionDatasBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, CommissionBean.DataBean.CommissionDatasBean bean, int position) {
        holder.setDraweeViewImage(R.id.sdv_head, bean.getAvatar());
        holder.setText(R.id.tv_nickname, bean.getNickname());
        holder.setText(R.id.tv_user_level, "（" + bean.getUserLevel() + "）");
        //DIS: 分润,PDIS：代理升级，BUY:购物,  GIVE:赠送,  EXCHANGE: 兑换,  BACK_EXCHANGE:回兑, RECHARGE:充值
        switch (bean.getBusiType()) {
            case "DIS":
                holder.setText(R.id.tv_consum_type, "消费类型：分润");
                break;
            case "PDIS":
                holder.setText(R.id.tv_consum_type, "消费类型：代理升级");
                break;
            case "BUY":
                holder.setText(R.id.tv_consum_type, "消费类型：购物");
                break;
            case "GIVE":
                holder.setText(R.id.tv_consum_type, "消费类型：赠送");
                break;
            case "EXCHANGE":
                holder.setText(R.id.tv_consum_type, "消费类型：兑换");
                break;
            case "BACK_EXCHANGE":
                holder.setText(R.id.tv_consum_type, "消费类型：回兑");
                break;
            case "RECHARGE":
                holder.setText(R.id.tv_consum_type, "消费类型：充值");
                break;
            default:
                break;
        }
        holder.setText(R.id.tv_consumCount, "消费金额：" + mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(bean.getConsumeScore() / 10000));
        holder.setText(R.id.tv_commission_rate, "分佣比例：" + bean.getCommissionRate() + "%");
        holder.setText(R.id.tv_commission, "佣金收益：" + mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(bean.getProfitScore() / 10000));
        holder.setText(R.id.tv_consum_date, "消费时间：" + bean.getOccurTime());
    }
}
