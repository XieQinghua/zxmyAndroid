package com.weishi.yiye.bean.eventbus;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：用于Fragment与宿主Activity交互loading动画
 * @Version:v1.0.0
 *****************************/
public class LoadingStateEvent {
    public static final int START_ANIM = 0;
    public static final int STOP_ANIM = 1;

    public int type;
    public String loadingText;

    public LoadingStateEvent(int type) {
        this.type = type;
    }

    public LoadingStateEvent(int type, String loadingText) {
        this.type = type;
        this.loadingText = loadingText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }
}
