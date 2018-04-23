package com.weishi.yiye.bean.eventbus;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/8
 * @Description：订单操作事件
 * @Version:v1.0.0
 *****************************/
public class OrderActionEvent {

    /**
     * 提交订单
     */
    public static final int SUBMIT_ORDER = 0;
    /**
     * 付款成功
     */
    public static final int PAY_SUCCESS = 1;

    /**
     * 评论成功
     */
    public static final int COMMENT_SUCCESS = 2;

    /**
     * 退款成功
     */
    public static final int REFUND_SUCCESS = 3;

    /**
     * 使用完成
     */
    public static final int USE_COMPLETE = 4;

    /**
     * 取消订单
     */
    public static final int CANCEL_ORDER = 5;

    private int type;

    public OrderActionEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
