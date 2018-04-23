package com.weishi.yiye.adapter;

import android.content.Context;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.bean.MsgBean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/18
 * @Description：系统消息Adapter
 * @Version:v1.0.0
 *****************************/

public class SystemMsgAdapter extends CommonAdapter<MsgBean.DataBean.ListBean> {

    public SystemMsgAdapter(Context context, List<MsgBean.DataBean.ListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public SystemMsgAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<MsgBean.DataBean.ListBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, MsgBean.DataBean.ListBean msgBean, int position) {
        TextView time = holder.getView(R.id.tv_time);
        TextView msgInfo = holder.getView(R.id.tv_msg_info);

        holder.setText(R.id.tv_time, TimeUtils.millis2String(msgBean.getSendTime(),
                new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())));
        holder.setText(R.id.tv_msg_info, msgBean.getMsgContent());
    }
}
