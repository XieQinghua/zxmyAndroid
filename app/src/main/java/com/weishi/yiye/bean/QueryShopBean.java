package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/24
 * @Description：商家实体类
 * @Version:v1.0.0
 *****************************/

public class QueryShopBean implements Serializable {
    private int id;
    private String storeName;
    private int storeOwner;
    private String shopkeeperName;
    private String mobile;
    private String address;
    private String storeLogo;
    private String introduce;
    private String createtime;
    private int status;
    private int storeFatherType;
    private int storeParentType;
    private String phone;
    private String lng;
    private String lat;
    private int isHot;
    private String authingTime;
    private int sellTotal;
    private double starLevel;
    private String keyword;
    private String userLat;
    private String userLng;
    private String orderBy;
    private String busiType;
    private String pageNum;
    private String pageSize;
    private double distance;
    private List<QueryProductBean> productList;
    private String searchDistance;
    private List<String> imgOssUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(int storeOwner) {
        this.storeOwner = storeOwner;
    }

    public String getShopkeeperName() {
        return shopkeeperName;
    }

    public void setShopkeeperName(String shopkeeperName) {
        this.shopkeeperName = shopkeeperName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStoreFatherType() {
        return storeFatherType;
    }

    public void setStoreFatherType(int storeFatherType) {
        this.storeFatherType = storeFatherType;
    }

    public int getStoreParentType() {
        return storeParentType;
    }

    public void setStoreParentType(int storeParentType) {
        this.storeParentType = storeParentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getAuthingTime() {
        return authingTime;
    }

    public void setAuthingTime(String authingTime) {
        this.authingTime = authingTime;
    }

    public int getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(int sellTotal) {
        this.sellTotal = sellTotal;
    }

    public double getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(double starLevel) {
        this.starLevel = starLevel;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLng() {
        return userLng;
    }

    public void setUserLng(String userLng) {
        this.userLng = userLng;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getSearchDistance() {
        return searchDistance;
    }

    public void setSearchDistance(String searchDistance) {
        this.searchDistance = searchDistance;
    }

    public List<String> getImgOssUrl() {
        return imgOssUrl;
    }

    public void setImgOssUrl(List<String> imgOssUrl) {
        this.imgOssUrl = imgOssUrl;
    }

    public List<QueryProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<QueryProductBean> productList) {
        this.productList = productList;
    }
}
