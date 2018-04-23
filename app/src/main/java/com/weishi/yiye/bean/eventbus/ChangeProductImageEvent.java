package com.weishi.yiye.bean.eventbus;

import java.util.ArrayList;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/4/3
 * @Description：请求商品图片事件
 * @Version:v1.0.0
 *****************************/
public class ChangeProductImageEvent {
    /**
     * 请求加载
     */
    public static final int REQUEST_LOADMORE = 1;

    /**
     * 加载完成
     */
    public static final int LOAD_COMPLETE = 2;

    private int type;

    public ArrayList<String> imgList = new ArrayList<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public ChangeProductImageEvent(int type) {
        this.type = type;
    }

    public ChangeProductImageEvent(int type, ArrayList<String> imgList) {
        this.type = type;
        this.imgList = imgList;
    }
}
