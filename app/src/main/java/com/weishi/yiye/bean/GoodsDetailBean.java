package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/2
 * @Description：商品详情
 * @Version:v1.0.0
 *****************************/
public class GoodsDetailBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : {"isExist":0,"productInfo":{"id":97,"productNum":"1","busiFatherType":1,"busiFatherName":null,"busiParentType":5,"busiParentName":null,"price":100,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/0c4606f7-f34d-4dad-ac3f-d90458420725","productName":"爽肤水","weight":23,"isReward":1,"rewardPoint":10,"rewardPercent":0,"description":"清爽","productDetail":"撒旦撒旦撒旦撒的我反对反对法","detailImg":"","detailImgList":["https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg"],"sellNum":0,"createtime":1518332901000,"storeId":14,"sendType":2,"status":1,"isHotsale":0,"isHot":0,"validTime":1520006400000,"modifyTime":1518332901000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":"78888888","telephone":"555555","address":"","distance":null,"starLevel":0}}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * isExist : 0
         * productInfo : {"id":97,"productNum":"1","busiFatherType":1,"busiFatherName":null,"busiParentType":5,"busiParentName":null,"price":100,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/0c4606f7-f34d-4dad-ac3f-d90458420725","productName":"爽肤水","weight":23,"isReward":1,"rewardPoint":10,"rewardPercent":0,"description":"清爽","productDetail":"撒旦撒旦撒旦撒的我反对反对法","detailImg":"","detailImgList":["https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg"],"sellNum":0,"createtime":1518332901000,"storeId":14,"sendType":2,"status":1,"isHotsale":0,"isHot":0,"validTime":1520006400000,"modifyTime":1518332901000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":"78888888","telephone":"555555","address":"","distance":null,"starLevel":0}
         */

        private int isExist;
        private ProductInfoBean productInfo;

        public int getIsExist() {
            return isExist;
        }

        public void setIsExist(int isExist) {
            this.isExist = isExist;
        }

        public ProductInfoBean getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(ProductInfoBean productInfo) {
            this.productInfo = productInfo;
        }

        public static class ProductInfoBean implements Serializable {
            /**
             * id : 97
             * productNum : 1
             * busiFatherType : 1
             * busiFatherName : null
             * busiParentType : 5
             * busiParentName : null
             * price : 100
             * showImg : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/0c4606f7-f34d-4dad-ac3f-d90458420725
             * productName : 爽肤水
             * weight : 23
             * isReward : 1
             * rewardPoint : 10
             * rewardPercent : 0.0
             * description : 清爽
             * productDetail : 撒旦撒旦撒旦撒的我反对反对法
             * detailImg :
             * detailImgList : ["https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg"]
             * sellNum : 0
             * createtime : 1518332901000
             * storeId : 14
             * sendType : 2
             * status : 1
             * isHotsale : 0
             * isHot : 0
             * validTime : 1520006400000
             * modifyTime : 1518332901000
             * commentLv : null
             * startTime : null
             * endTime : null
             * mobile : null
             * storeName : 78888888
             * telephone : 555555
             * address :
             * distance : null
             * starLevel : 0.0
             */

            private int id;
            private String productNum;
            private int busiFatherType;
            private Object busiFatherName;
            private int busiParentType;
            private Object busiParentName;
            private double price;
            private String showImg;
            private String productName;
            private int weight;
            private int isReward;
            private int rewardPoint;
            private double rewardPercent;
            private String description;
            private String productDetail;
            private String detailImg;
            private int sellNum;
            private long createtime;
            private int storeId;
            private int sendType;
            private int status;
            private int isHotsale;
            private int isHot;
            private long validTime;
            private long modifyTime;
            private Object commentLv;
            private Object startTime;
            private Object endTime;
            private Object mobile;
            private String storeName;
            private String telephone;
            private String address;
            private double distance;
            private double starLevel;
            private List<String> detailImgList;

            private String alternateImg;
            private List<String> alternateImgList;

            private double lat;
            private double lng;
            private String validTimeStr;
            private String reserveInformation; //预约信息
            private String notes; //注意事项
            private double groupPrice; //团购价
            private String indate; //有效期
            private double marketPrice;//市场价

            public double getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(double marketPrice) {
                this.marketPrice = marketPrice;
            }

            public String getReserveInformation() {
                return reserveInformation;
            }

            public void setReserveInformation(String reserveInformation) {
                this.reserveInformation = reserveInformation;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public double getGroupPrice() {
                return groupPrice;
            }

            public void setGroupPrice(double groupPrice) {
                this.groupPrice = groupPrice;
            }

            public String getIndate() {
                return indate;
            }

            public void setIndate(String indate) {
                this.indate = indate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProductNum() {
                return productNum;
            }

            public void setProductNum(String productNum) {
                this.productNum = productNum;
            }

            public int getBusiFatherType() {
                return busiFatherType;
            }

            public void setBusiFatherType(int busiFatherType) {
                this.busiFatherType = busiFatherType;
            }

            public Object getBusiFatherName() {
                return busiFatherName;
            }

            public void setBusiFatherName(Object busiFatherName) {
                this.busiFatherName = busiFatherName;
            }

            public int getBusiParentType() {
                return busiParentType;
            }

            public void setBusiParentType(int busiParentType) {
                this.busiParentType = busiParentType;
            }

            public Object getBusiParentName() {
                return busiParentName;
            }

            public void setBusiParentName(Object busiParentName) {
                this.busiParentName = busiParentName;
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

            public double getRewardPercent() {
                return rewardPercent;
            }

            public void setRewardPercent(double rewardPercent) {
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

            public int getSellNum() {
                return sellNum;
            }

            public void setSellNum(int sellNum) {
                this.sellNum = sellNum;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
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

            public long getValidTime() {
                return validTime;
            }

            public void setValidTime(long validTime) {
                this.validTime = validTime;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public Object getCommentLv() {
                return commentLv;
            }

            public void setCommentLv(Object commentLv) {
                this.commentLv = commentLv;
            }

            public Object getStartTime() {
                return startTime;
            }

            public void setStartTime(Object startTime) {
                this.startTime = startTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
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

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public double getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(double starLevel) {
                this.starLevel = starLevel;
            }

            public List<String> getDetailImgList() {
                return detailImgList;
            }

            public void setDetailImgList(List<String> detailImgList) {
                this.detailImgList = detailImgList;
            }

            public String getAlternateImg() {
                return alternateImg;
            }

            public void setAlternateImg(String alternateImg) {
                this.alternateImg = alternateImg;
            }

            public List<String> getAlternateImgList() {
                return alternateImgList;
            }

            public void setAlternateImgList(List<String> alternateImgList) {
                this.alternateImgList = alternateImgList;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getValidTimeStr() {
                return validTimeStr;
            }

            public void setValidTimeStr(String validTimeStr) {
                this.validTimeStr = validTimeStr;
            }
        }
    }
}
