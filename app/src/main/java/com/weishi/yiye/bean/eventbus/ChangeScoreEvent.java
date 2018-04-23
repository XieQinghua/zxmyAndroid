package com.weishi.yiye.bean.eventbus;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/6
 * @Description：积分变化事件
 * @Version:v1.0.0
 *****************************/
public class ChangeScoreEvent {
    /**
     * 充值积分
     */
    public static final int SCORE_BUY = 1;

    /**
     * 支付积分
     */
    public static final int SCORE_PAY = 2;

    /**
     * 退款
     */
    public static final int REFUND = 3;

    private int type;

    public ChangeScoreEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
