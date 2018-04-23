package com.weishi.yiye.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.weishi.yiye.R;
import com.weishi.yiye.bean.ScoreBean;

import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/18
 * @Description：积分充值Adapter
 * @Version:v1.0.0
 *****************************/
public class ScoreBuyAdapter extends CommonAdapter<ScoreBean.DataBean> {

    public ScoreBuyAdapter(Context context, List<ScoreBean.DataBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public ScoreBuyAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void convert(ViewHolder holder, ScoreBean.DataBean dataBean, int position) {
        holder.setText(R.id.tv_score_number, mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(dataBean.getPoint()));
        holder.setText(R.id.tv_score_price, "售价：" + new DecimalFormat("#0.00").format(dataBean.getPrice()) + "元");
    }
}
