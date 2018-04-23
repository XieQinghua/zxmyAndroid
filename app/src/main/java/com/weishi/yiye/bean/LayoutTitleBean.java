package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/25
 * @Description：LayoutTab标题内容存储实体类
 * @Version:v1.0.0
 *****************************/

public class LayoutTitleBean {

    private int targetId;
    private String title;

    public LayoutTitleBean(int targetId, String title) {
        this.targetId = targetId;
        this.title = title;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
