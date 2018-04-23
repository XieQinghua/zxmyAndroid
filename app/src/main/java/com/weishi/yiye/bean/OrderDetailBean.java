package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/28
 * @Description：订单详情
 * @Version:v1.0.0
 *****************************/

public class OrderDetailBean {

    /**
     * code : 200
     * message : success
     * data : {"id":null,"orderNum":"1803032035200001","orderPrice":30,"rewardPoint":null,"userId":null,"mobile":"15200917596","useStatus":null,"orderStatus":null,"commentStatus":0,"payMethod":null,"createtime":null,"useTime":null,"useCode":"180303203933","payTime":1520080773000,"modifyTime":null,"cancleTime":1520123720000,"productId":4,"productName":"韩束墨菊巨补水化","storeId":3,"tagType":null,"number":1,"validTime":1520035200000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/d16e7e8e-0d47-4c28-9fcc-473fe6cdc9ce","storeName":"美丽田园BEAUTY FARM","address":"建安像素汇1楼C212","phone":"110","payPasswd":null,"pendPayNum":null,"pendUseNum":null,"pendCommentNum":null,"refundNum":null,"orderStatusStr":null,"token":null,"showStatus":null,"isBind":null,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"","detailImgList":["https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/45e18853-d0b5-47c4-b7d8-44540aaa4c67","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/585b4b5b-e6f2-4783-bc0f-bca9bb1f3277"],"distance":954,"storeLat":28.214069,"storeLng":112.89325}
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
         * id : null
         * orderNum : 1803032035200001
         * orderPrice : 30
         * rewardPoint : null
         * userId : null
         * mobile : 15200917596
         * useStatus : null
         * orderStatus : null
         * commentStatus : 0
         * payMethod : null
         * createtime : null
         * useTime : null
         * useCode : 180303203933
         * payTime : 1520080773000
         * modifyTime : null
         * cancleTime : 1520123720000
         * productId : 4
         * productName : 韩束墨菊巨补水化
         * storeId : 3
         * tagType : null
         * number : 1
         * validTime : 1520035200000
         * showImg : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/d16e7e8e-0d47-4c28-9fcc-473fe6cdc9ce
         * storeName : 美丽田园BEAUTY FARM
         * address : 建安像素汇1楼C212
         * phone : 110
         * payPasswd : null
         * pendPayNum : null
         * pendUseNum : null
         * pendCommentNum : null
         * refundNum : null
         * orderStatusStr : null
         * token : null
         * showStatus : null
         * isBind : null
         * description : 减肥棒棒哒
         * productDetail : 透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。
         * detailImg :
         * detailImgList : ["https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/45e18853-d0b5-47c4-b7d8-44540aaa4c67","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/585b4b5b-e6f2-4783-bc0f-bca9bb1f3277"]
         * distance : 954.0
         * storeLat : 28.214069
         * storeLng : 112.89325
         */

        private int id;
        private String orderNum;
        private Double orderPrice;
        private int rewardPoint;
        private Object userId;
        private String mobile;
        private Object useStatus;
        private Object orderStatus;
        private int commentStatus;
        private Object payMethod;
        private Object createtime;
        private Long useTime;
        private String useCode;
        private Long payTime;
        private Object modifyTime;
        private long cancleTime;
        private int productId;
        private String productName;
        private int storeId;
        private Object tagType;
        private int number;
        private long validTime;
        private String showImg;
        private String storeName;
        private String address;
        private String phone;
        private Object payPasswd;
        private Object pendPayNum;
        private Object pendUseNum;
        private Object pendCommentNum;
        private Object refundNum;
        private Object orderStatusStr;
        private Object token;
        private Object showStatus;
        private Object isBind;
        private String description;
        private String productDetail;
        private String detailImg;
        private double distance;
        private double storeLat;
        private double storeLng;
        private List<String> detailImgList;

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

        public Double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(Double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getRewardPoint() {
            return rewardPoint;
        }

        public void setRewardPoint(int rewardPoint) {
            this.rewardPoint = rewardPoint;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
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

        public int getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(int commentStatus) {
            this.commentStatus = commentStatus;
        }

        public Object getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(Object payMethod) {
            this.payMethod = payMethod;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }

        public Long getUseTime() {
            return useTime;
        }

        public void setUseTime(Long useTime) {
            this.useTime = useTime;
        }

        public String getUseCode() {
            return useCode;
        }

        public void setUseCode(String useCode) {
            this.useCode = useCode;
        }

        public Long getPayTime() {
            return payTime;
        }

        public void setPayTime(Long payTime) {
            this.payTime = payTime;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public long getCancleTime() {
            return cancleTime;
        }

        public void setCancleTime(long cancleTime) {
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
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

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getStoreLat() {
            return storeLat;
        }

        public void setStoreLat(double storeLat) {
            this.storeLat = storeLat;
        }

        public double getStoreLng() {
            return storeLng;
        }

        public void setStoreLng(double storeLng) {
            this.storeLng = storeLng;
        }

        public List<String> getDetailImgList() {
            return detailImgList;
        }

        public void setDetailImgList(List<String> detailImgList) {
            this.detailImgList = detailImgList;
        }
    }
}
