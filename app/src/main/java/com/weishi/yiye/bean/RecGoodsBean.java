package com.weishi.yiye.bean;

import java.io.Serializable;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/10
 * @Description：推荐商品实体类
 * @Version:v1.0.0
 *****************************/

public class RecGoodsBean implements Serializable {
    private int imgUrl;
    private String goodsName = "";
    private String serviceTime = "";
    private String score = "";
    private String soldOut = "";

    public RecGoodsBean(int imgUrl, String goodsName, String serviceTime, String score, String soldOut) {
        this.imgUrl = imgUrl;
        this.goodsName = goodsName;
        this.serviceTime = serviceTime;
        this.score = score;
        this.soldOut = soldOut;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }
}
