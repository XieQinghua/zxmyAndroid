package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/24
 * @Description：推荐商家实体类
 * @Version:v1.0.0
 *****************************/

public class RecShopsBean implements Serializable {
    private String shopsHeadUrl;
    private String shopsHeadId;
    private String shopsName;
    private double grade;
    private String address;
    private List<RecGoodsBean> recGoodsList;

    public RecShopsBean(String shopsHeadUrl, String shopsHeadId, String shopsName, double grade, String address, List<RecGoodsBean> recGoodsList) {
        this.shopsHeadUrl = shopsHeadUrl;
        this.shopsHeadId = shopsHeadId;
        this.shopsName = shopsName;
        this.grade = grade;
        this.address = address;
        this.recGoodsList = recGoodsList;
    }

    public String getShopsHeadUrl() {
        return shopsHeadUrl;
    }

    public void setShopsHeadUrl(String shopsHeadUrl) {
        this.shopsHeadUrl = shopsHeadUrl;
    }

    public String getShopsHeadId() {
        return shopsHeadId;
    }

    public void setShopsHeadId(String shopsHeadId) {
        this.shopsHeadId = shopsHeadId;
    }

    public String getShopsName() {
        return shopsName;
    }

    public void setShopsName(String shopsName) {
        this.shopsName = shopsName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RecGoodsBean> getRecGoodsList() {
        return recGoodsList;
    }

    public void setRecGoodsList(List<RecGoodsBean> recGoodsList) {
        this.recGoodsList = recGoodsList;
    }
}
