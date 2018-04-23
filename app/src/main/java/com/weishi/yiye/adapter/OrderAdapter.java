package com.weishi.yiye.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.GoodsDetailActivity;
import com.weishi.yiye.activity.order.CommentActivity;
import com.weishi.yiye.activity.order.OrderDetailActivity;
import com.weishi.yiye.activity.order.PayScoreActivity;
import com.weishi.yiye.bean.RecentOrderBean;
import com.weishi.yiye.common.GoodsConstants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by zym on 2018/1/11.
 */

public class OrderAdapter extends CommonAdapter<RecentOrderBean.DataBean.ListBean> {
    public OrderAdapter(Context context, List<RecentOrderBean.DataBean.ListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public OrderAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<RecentOrderBean.DataBean.ListBean> datas) {
        super.setData(datas);
    }

    @SuppressLint("NewApi")
    @Override
    public void convert(ViewHolder holder, final RecentOrderBean.DataBean.ListBean bean, int position) {
        holder.setDraweeViewImage(R.id.sdv_orders_head, bean.getShowImg());
        holder.setText(R.id.tv_goods_name, bean.getProductName());
        holder.setText(R.id.tv_goods_score, mContext.getString(R.string.money_unit) + new DecimalFormat("#0.00").format(bean.getOrderPrice()));
        holder.setText(R.id.tv_goods_amount, "数量：x" + bean.getNumber());
        holder.setText(R.id.tv_service_time, "有效期至：" + TimeUtils.millis2String(bean.getValidTime(),
                new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())));
        //1：待付款，2：取消，3：待评价，4：已评价，5：未使用，6：已使用，7：已退款，8： 退款中  showStatus的状态值
        if (bean.getShowStatus() != null) {
            switch (bean.getShowStatus()) {
                case 1:
                    holder.setText(R.id.tv_function_info, "待付款");
                    holder.setText(R.id.btn_function, "付款");
                    holder.setBackGround(R.id.btn_function, mContext.getResources().getDrawable(R.drawable.btn_gray_rim_shape2));
                    holder.setTextColor(R.id.btn_function, R.color.app_text_color2);
                    holder.getView(R.id.btn_function).setVisibility(View.VISIBLE);
                    holder.getView(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, PayScoreActivity.class);
                            intent.putExtra("orderPrice", bean.getOrderPrice());
                            intent.putExtra("orderNum", bean.getOrderNum());
                            intent.putExtra("rewardPoint", bean.getRewardPoint());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    holder.setText(R.id.tv_function_info, "已取消");
                    holder.setText(R.id.btn_function, "再次预定");
                    holder.setBackGround(R.id.btn_function, mContext.getResources().getDrawable(R.drawable.btn_gray_rim_shape2));
                    holder.setTextColor(R.id.btn_function, R.color.app_text_color2);
                    holder.getView(R.id.btn_function).setVisibility(View.VISIBLE);
                    holder.getView(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 再次预定,跳转商品详情页面
                            Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                            intent.putExtra(GoodsConstants.KEY_GOODS_ID, bean.getProductId());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    holder.setText(R.id.tv_function_info, "待评价");
                    holder.setText(R.id.btn_function, "评价");
                    holder.setBackGround(R.id.btn_function, mContext.getResources().getDrawable(R.drawable.btn_gray_rim_shape2));
                    holder.setTextColor(R.id.btn_function, R.color.app_text_color2);
                    holder.getView(R.id.btn_function).setVisibility(View.VISIBLE);
                    holder.getView(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, CommentActivity.class);
                            // 传评价订单数据
                            intent.putExtra("orderId", bean.getId());
                            intent.putExtra("productId", bean.getProductId());
                            intent.putExtra("storeId", bean.getStoreId());
                            intent.putExtra("showImg", bean.getShowImg());
                            intent.putExtra("productName", bean.getProductName());
                            intent.putExtra("orderPrice", bean.getOrderPrice());
                            intent.putExtra("orderNum", bean.getNumber());
                            intent.putExtra("validTime", bean.getValidTime());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    holder.setText(R.id.tv_function_info, "已评价");
                    holder.getView(R.id.btn_function).setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    holder.setText(R.id.tv_function_info, "待使用");
                    holder.setText(R.id.btn_function, "使用");
                    holder.setBackGround(R.id.btn_function, mContext.getResources().getDrawable(R.drawable.btn_gray_rim_shape2));
                    holder.setTextColor(R.id.btn_function, R.color.app_text_color2);
                    holder.getView(R.id.btn_function).setVisibility(View.VISIBLE);
                    holder.getView(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, OrderDetailActivity.class);
                            intent.putExtra("orderId", bean.getId());
                            intent.putExtra("orderNum", bean.getOrderNum());
                            intent.putExtra("useStatus", bean.getUseStatus());
                            intent.putExtra("orderStatus", bean.getOrderStatus() + "");
                            intent.putExtra("showStatus", bean.getShowStatus());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 6:
                    holder.setText(R.id.tv_function_info, "已使用");
                    holder.setText(R.id.btn_function, "评价");
                    holder.getView(R.id.btn_function).setVisibility(View.VISIBLE);
                    holder.setBackGround(R.id.btn_function, mContext.getResources().getDrawable(R.drawable.btn_red_selector1));
                    holder.getView(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, CommentActivity.class);
                            // 传评价订单数据
                            intent.putExtra("orderId", bean.getId());
                            intent.putExtra("productId", bean.getProductId());
                            intent.putExtra("storeId", bean.getStoreId());
                            intent.putExtra("showImg", bean.getShowImg());
                            intent.putExtra("productName", bean.getProductName());
                            intent.putExtra("orderPrice", bean.getOrderPrice());
                            intent.putExtra("orderNum", bean.getNumber());
                            intent.putExtra("validTime", bean.getValidTime());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 7:
                    holder.setText(R.id.tv_function_info, "已退款");
                    holder.getView(R.id.btn_function).setVisibility(View.INVISIBLE);
                    break;
                case 8:
                    holder.setText(R.id.tv_function_info, "退款中");
                    holder.getView(R.id.btn_function).setVisibility(View.INVISIBLE);
                    break;
                case 9:
                    holder.setText(R.id.tv_function_info, "支付中");
                    holder.setText(R.id.btn_function, "付款");
                    holder.setBackGround(R.id.btn_function, mContext.getResources().getDrawable(R.drawable.btn_gray_rim_shape2));
                    holder.setTextColor(R.id.btn_function, R.color.app_text_color2);
                    holder.getView(R.id.btn_function).setVisibility(View.VISIBLE);
                    holder.getView(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, PayScoreActivity.class);
                            intent.putExtra("orderPrice", bean.getOrderPrice());
                            intent.putExtra("orderNum", bean.getOrderNum());
                            intent.putExtra("rewardPoint", bean.getRewardPoint());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        } else {
            holder.setText(R.id.tv_function_info, "");
            holder.setText(R.id.btn_function, "");
            holder.getView(R.id.btn_function).setVisibility(View.GONE);
        }
    }
}
