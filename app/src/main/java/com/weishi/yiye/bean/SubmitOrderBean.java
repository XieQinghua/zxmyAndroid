package com.weishi.yiye.bean;

import java.io.Serializable;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/4
 * @Description：提交订单
 * @Version:v1.0.0
 *****************************/
public class SubmitOrderBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : {"id":268,"orderNum":"18021114280001","orderPrice":200,"rewardPoint":50,"userId":116,"mobile":"15200917596","useStatus":null,"orderStatus":null,"commentStatus":null,"payMethod":null,"createtime":1518330493399,"useTime":null,"useCode":null,"payTime":null,"modifyTime":null,"cancleTime":null,"productId":57,"productName":"美丽传说","storeId":12,"tagType":null,"number":10,"validTime":1520006400000,"showImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","storeName":"琪雅","address":null,"phone":null,"payPasswd":null,"pendPayNum":null,"pendUseNum":null,"pendCommentNum":null,"refundNum":null,"orderStatusStr":null,"token":null,"showStatus":null,"isBind":null,"description":null,"productDetail":null}
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

    public static class DataBean {
        /**
         * id : 268
         * orderNum : 18021114280001
         * orderPrice : 200
         * rewardPoint : 50
         * userId : 116
         * mobile : 15200917596
         * useStatus : null
         * orderStatus : null
         * commentStatus : null
         * payMethod : null
         * createtime : 1518330493399
         * useTime : null
         * useCode : null
         * payTime : null
         * modifyTime : null
         * cancleTime : null
         * productId : 57
         * productName : 美丽传说
         * storeId : 12
         * tagType : null
         * number : 10
         * validTime : 1520006400000
         * showImg : https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg
         * storeName : 琪雅
         * address : null
         * phone : null
         * payPasswd : null
         * pendPayNum : null
         * pendUseNum : null
         * pendCommentNum : null
         * refundNum : null
         * orderStatusStr : null
         * token : null
         * showStatus : null
         * isBind : null
         * description : null
         * productDetail : null
         */

        private int id;
        private String orderNum;
        private double orderPrice;
        private int rewardPoint;
        private int userId;
        private String mobile;
        private Object useStatus;
        private Object orderStatus;
        private Object commentStatus;
        private Object payMethod;
        private long createtime;
        private Object useTime;
        private Object useCode;
        private Object payTime;
        private Object modifyTime;
        private Object cancleTime;
        private int productId;
        private String productName;
        private int storeId;
        private Object tagType;
        private int number;
        private long validTime;
        private String showImg;
        private String storeName;
        private Object address;
        private Object phone;
        private Object payPasswd;
        private Object pendPayNum;
        private Object pendUseNum;
        private Object pendCommentNum;
        private Object refundNum;
        private Object orderStatusStr;
        private Object token;
        private Object showStatus;
        private Object isBind;
        private Object description;
        private Object productDetail;

        private double payInBalance;
        private double payInCash;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getRewardPoint() {
            return rewardPoint;
        }

        public void setRewardPoint(int rewardPoint) {
            this.rewardPoint = rewardPoint;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(Object useStatus) {
            this.useStatus = useStatus;
        }

        public Object getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Object orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Object getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(Object commentStatus) {
            this.commentStatus = commentStatus;
        }

        public Object getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(Object payMethod) {
            this.payMethod = payMethod;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public Object getUseTime() {
            return useTime;
        }

        public void setUseTime(Object useTime) {
            this.useTime = useTime;
        }

        public Object getUseCode() {
            return useCode;
        }

        public void setUseCode(Object useCode) {
            this.useCode = useCode;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public Object getCancleTime() {
            return cancleTime;
        }

        public void setCancleTime(Object cancleTime) {
            this.cancleTime = cancleTime;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public Object getTagType() {
            return tagType;
        }

        public void setTagType(Object tagType) {
            this.tagType = tagType;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public long getValidTime() {
            return validTime;
        }

        public void setValidTime(long validTime) {
            this.validTime = validTime;
        }

        public String getShowImg() {
            return showImg;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getPayPasswd() {
            return payPasswd;
        }

        public void setPayPasswd(Object payPasswd) {
            this.payPasswd = payPasswd;
        }

        public Object getPendPayNum() {
            return pendPayNum;
        }

        public void setPendPayNum(Object pendPayNum) {
            this.pendPayNum = pendPayNum;
        }

        public Object getPendUseNum() {
            return pendUseNum;
        }

        public void setPendUseNum(Object pendUseNum) {
            this.pendUseNum = pendUseNum;
        }

        public Object getPendCommentNum() {
            return pendCommentNum;
        }

        public void setPendCommentNum(Object pendCommentNum) {
            this.pendCommentNum = pendCommentNum;
        }

        public Object getRefundNum() {
            return refundNum;
        }

        public void setRefundNum(Object refundNum) {
            this.refundNum = refundNum;
        }

        public Object getOrderStatusStr() {
            return orderStatusStr;
        }

        public void setOrderStatusStr(Object orderStatusStr) {
            this.orderStatusStr = orderStatusStr;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public Object getShowStatus() {
            return showStatus;
        }

        public void setShowStatus(Object showStatus) {
            this.showStatus = showStatus;
        }

        public Object getIsBind() {
            return isBind;
        }

        public void setIsBind(Object isBind) {
            this.isBind = isBind;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getProductDetail() {
            return productDetail;
        }

        public void setProductDetail(Object productDetail) {
            this.productDetail = productDetail;
        }

        public double getPayInBalance() {
            return payInBalance;
        }

        public void setPayInBalance(double payInBalance) {
            this.payInBalance = payInBalance;
        }

        public double getPayInCash() {
            return payInCash;
        }

        public void setPayInCash(double payInCash) {
            this.payInCash = payInCash;
        }
    }
}
