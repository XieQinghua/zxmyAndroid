package com.weishi.yiye.bean.eventbus;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/27
 * @Description：网络连接和断开的消息
 * @Version:v1.0.0
 *****************************/
public class NetWorkEvent {
    /**
     * 网络连接
     */
    public static final int NET_DISCONNECT = 11;

    /**
     * socket连接成功，暂时以订阅事件连接成功为准
     */
    public static final int NET_CONNECT = 12;

    private int type;

    public NetWorkEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
