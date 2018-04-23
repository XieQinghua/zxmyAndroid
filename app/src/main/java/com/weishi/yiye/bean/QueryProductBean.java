package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/24
 * @Description：商品实体类
 * @Version:v1.0.0
 *****************************/

public class QueryProductBean implements Serializable {
    private int id;
    private int productNum;
    private int busiFatherType;
    private int busiParentType;
    private double price;
    private String showImg;
    private String productName;
    private int weight;
    private int isReward;
    private int rewardPoint;
    private String rewardPercent;
    private String description;
    private String productDetail;
    private String detailImg;
    private List<String> detailImgList;
    private int sellNum;
    private String createtime;
    private int storeId;
    private int sendType;
    private int status;
    private int isHotsale;
    private int isHot;
    private String validTime;
    private String modifyTime;
    private String commentLv;
    private String startTime;
    private String endTime;
    private String mobile;
    private String storeName;
    private String telephone;
    private String address;
    private String distance;

    /********************美业新增字段********************/
    private double starLevel;
    private String detailImg1;
    private String detailImg2;
    private String detailImg3;
    private String alternateImg1;
    private String alternateImg2;
    private String alternateImg3;
    private String useTime;
    private String validTimeStr;
    private double marketPrice;

    public double getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(double starLevel) {
        this.starLevel = starLevel;
    }

    public String getDetailImg1() {
        return detailImg1;
    }

    public void setDetailImg1(String detailImg1) {
        this.detailImg1 = detailImg1;
    }

    public String getDetailImg2() {
        return detailImg2;
    }

    public void setDetailImg2(String detailImg2) {
        this.detailImg2 = detailImg2;
    }

    public String getDetailImg3() {
        return detailImg3;
    }

    public void setDetailImg3(String detailImg3) {
        this.detailImg3 = detailImg3;
    }

    public String getAlternateImg1() {
        return alternateImg1;
    }

    public void setAlternateImg1(String alternateImg1) {
        this.alternateImg1 = alternateImg1;
    }

    public String getAlternateImg2() {
        return alternateImg2;
    }

    public void setAlternateImg2(String alternateImg2) {
        this.alternateImg2 = alternateImg2;
    }

    public String getAlternateImg3() {
        return alternateImg3;
    }

    public void setAlternateImg3(String alternateImg3) {
        this.alternateImg3 = alternateImg3;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getValidTimeStr() {
        return validTimeStr;
    }

    public void setValidTimeStr(String validTimeStr) {
        this.validTimeStr = validTimeStr;
    }

    /********************美业新增字段********************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getBusiFatherType() {
        return busiFatherType;
    }

    public void setBusiFatherType(int busiFatherType) {
        this.busiFatherType = busiFatherType;
    }

    public int getBusiParentType() {
        return busiParentType;
    }

    public void setBusiParentType(int busiParentType) {
        this.busiParentType = busiParentType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIsReward() {
        return isReward;
    }

    public void setIsReward(int isReward) {
        this.isReward = isReward;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getRewardPercent() {
        return rewardPercent;
    }

    public void setRewardPercent(String rewardPercent) {
        this.rewardPercent = rewardPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public List<String> getDetailImgList() {
        return detailImgList;
    }

    public void setDetailImgList(List<String> detailImgList) {
        this.detailImgList = detailImgList;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsHotsale() {
        return isHotsale;
    }

    public void setIsHotsale(int isHotsale) {
        this.isHotsale = isHotsale;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCommentLv() {
        return commentLv;
    }

    public void setCommentLv(String commentLv) {
        this.commentLv = commentLv;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }
}
