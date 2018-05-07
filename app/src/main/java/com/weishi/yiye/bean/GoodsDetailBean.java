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
     * data : {"isExist":0,"productInfo":{"id":127,"productNum":"1","busiFatherType":107,"busiFatherName":null,"busiParentType":-1,"busiParentName":null,"price":99,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/1abe9555-7fbb-4787-9c1a-72594e6076d9","alternateImg":"","alternateImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/9b3902ea-8455-4e61-9d6a-2e6ee2377ab2","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/fa2f96de-7257-48d2-a84a-ac095d1acdc7","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/0ad324eb-6fa8-4635-b075-0899f1204ffa"],"productName":"芙蓉霜","weight":12,"isReward":1,"rewardPoint":10,"rewardPercent":0,"description":"只要99，众享爆品带回家！","productDetail":"芙蓉霜，会呼吸的霜，含三千万个结合水的水分子1","detailImg":"","detailImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/f201b1e8-4ae2-423f-b7d5-5094ba5b4161","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/3dacb376-1dfb-4a08-8ee1-03c687c1675a","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/3a4d5ce6-a23e-4aa5-9ebf-82c5c78cd432"],"sellNum":46,"createtime":1520505321000,"storeId":48,"sendType":2,"status":1,"isHotsale":0,"isHot":0,"validTime":1525795200000,"modifyTime":1524126700000,"commentLv":null,"startTime":1522252800000,"endTime":null,"mobile":null,"storeName":"众享爆品","telephone":"15399793962","address":"湖南省长沙市岳麓区平川路口天顶街道","distance":529,"starLevel":4.7333,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":"9:00-17:30","sortId":-1,"reserveInformation":"请提前一小时电话联系","notes":"1、单张团购券最多支持1人使用；\r\n2、团购用户不享受其他优惠；\r\n3、提供免费WIFI；\r\n4、自带酒水事宜请电话咨询商家；","refundMsgType":0,"groupPrice":66,"sortName":null,"indate":"2018-03-29至2018-05-09","marketPrice":199,"clickRate":214,"lat":28.20358,"lng":112.88926,"stock":0,"validTimeStr":"2018-05-09","startTimeStr":"2018-03-29","detailImgs":[{"id":377,"content":"采用无酒精新配方。进一步提升保湿力、加强角质层补水作用，令肌肤持续水润细腻。 不仅水分不足的肌肤可用，皮脂过剩的油性肌肤也能在补水同时平衡水油。","contentType":1,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":1},{"id":378,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/ed52f5cc-4a42-4cc5-8aca-58ce5ab89170","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":2},{"id":379,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/66e76bb0-43fe-4c31-b8e1-289b904e34e5","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":3},{"id":380,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e123a21c-4894-4146-a3d8-5e98cee4661c","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":4}],"alternateImgs":[{"id":374,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/99732cd6-a4a0-4a42-834c-75c99eee0f89","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402377000,"modifyTime":1525402377000,"sort":1},{"id":375,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/44b1135b-c426-424c-9a36-b8197cdc11b4","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402377000,"modifyTime":1525402377000,"sort":2},{"id":376,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e00e6f3f-3d63-4986-8d56-6721f8bc5dea","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":3}]}}
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
         * productInfo : {"id":127,"productNum":"1","busiFatherType":107,"busiFatherName":null,"busiParentType":-1,"busiParentName":null,"price":99,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/1abe9555-7fbb-4787-9c1a-72594e6076d9","alternateImg":"","alternateImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/9b3902ea-8455-4e61-9d6a-2e6ee2377ab2","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/fa2f96de-7257-48d2-a84a-ac095d1acdc7","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/0ad324eb-6fa8-4635-b075-0899f1204ffa"],"productName":"芙蓉霜","weight":12,"isReward":1,"rewardPoint":10,"rewardPercent":0,"description":"只要99，众享爆品带回家！","productDetail":"芙蓉霜，会呼吸的霜，含三千万个结合水的水分子1","detailImg":"","detailImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/f201b1e8-4ae2-423f-b7d5-5094ba5b4161","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/3dacb376-1dfb-4a08-8ee1-03c687c1675a","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/3a4d5ce6-a23e-4aa5-9ebf-82c5c78cd432"],"sellNum":46,"createtime":1520505321000,"storeId":48,"sendType":2,"status":1,"isHotsale":0,"isHot":0,"validTime":1525795200000,"modifyTime":1524126700000,"commentLv":null,"startTime":1522252800000,"endTime":null,"mobile":null,"storeName":"众享爆品","telephone":"15399793962","address":"湖南省长沙市岳麓区平川路口天顶街道","distance":529,"starLevel":4.7333,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":"9:00-17:30","sortId":-1,"reserveInformation":"请提前一小时电话联系","notes":"1、单张团购券最多支持1人使用；\r\n2、团购用户不享受其他优惠；\r\n3、提供免费WIFI；\r\n4、自带酒水事宜请电话咨询商家；","refundMsgType":0,"groupPrice":66,"sortName":null,"indate":"2018-03-29至2018-05-09","marketPrice":199,"clickRate":214,"lat":28.20358,"lng":112.88926,"stock":0,"validTimeStr":"2018-05-09","startTimeStr":"2018-03-29","detailImgs":[{"id":377,"content":"采用无酒精新配方。进一步提升保湿力、加强角质层补水作用，令肌肤持续水润细腻。 不仅水分不足的肌肤可用，皮脂过剩的油性肌肤也能在补水同时平衡水油。","contentType":1,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":1},{"id":378,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/ed52f5cc-4a42-4cc5-8aca-58ce5ab89170","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":2},{"id":379,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/66e76bb0-43fe-4c31-b8e1-289b904e34e5","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":3},{"id":380,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e123a21c-4894-4146-a3d8-5e98cee4661c","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":4}],"alternateImgs":[{"id":374,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/99732cd6-a4a0-4a42-834c-75c99eee0f89","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402377000,"modifyTime":1525402377000,"sort":1},{"id":375,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/44b1135b-c426-424c-9a36-b8197cdc11b4","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402377000,"modifyTime":1525402377000,"sort":2},{"id":376,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e00e6f3f-3d63-4986-8d56-6721f8bc5dea","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":3}]}
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
             * id : 127
             * productNum : 1
             * busiFatherType : 107
             * busiFatherName : null
             * busiParentType : -1
             * busiParentName : null
             * price : 99.0
             * showImg : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/1abe9555-7fbb-4787-9c1a-72594e6076d9
             * alternateImg :
             * alternateImgList : ["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/9b3902ea-8455-4e61-9d6a-2e6ee2377ab2","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/fa2f96de-7257-48d2-a84a-ac095d1acdc7","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/0ad324eb-6fa8-4635-b075-0899f1204ffa"]
             * productName : 芙蓉霜
             * weight : 12
             * isReward : 1
             * rewardPoint : 10
             * rewardPercent : 0.0
             * description : 只要99，众享爆品带回家！
             * productDetail : 芙蓉霜，会呼吸的霜，含三千万个结合水的水分子1
             * detailImg :
             * detailImgList : ["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/f201b1e8-4ae2-423f-b7d5-5094ba5b4161","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/3dacb376-1dfb-4a08-8ee1-03c687c1675a","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/3a4d5ce6-a23e-4aa5-9ebf-82c5c78cd432"]
             * sellNum : 46
             * createtime : 1520505321000
             * storeId : 48
             * sendType : 2
             * status : 1
             * isHotsale : 0
             * isHot : 0
             * validTime : 1525795200000
             * modifyTime : 1524126700000
             * commentLv : null
             * startTime : 1522252800000
             * endTime : null
             * mobile : null
             * storeName : 众享爆品
             * telephone : 15399793962
             * address : 湖南省长沙市岳麓区平川路口天顶街道
             * distance : 529
             * starLevel : 4.7333
             * detailImg1 : null
             * detailImg2 : null
             * detailImg3 : null
             * alternateImg1 : null
             * alternateImg2 : null
             * alternateImg3 : null
             * useTime : 9:00-17:30
             * sortId : -1
             * reserveInformation : 请提前一小时电话联系
             * notes : 1、单张团购券最多支持1人使用；
             * 2、团购用户不享受其他优惠；
             * 3、提供免费WIFI；
             * 4、自带酒水事宜请电话咨询商家；
             * refundMsgType : 0
             * groupPrice : 66.0
             * sortName : null
             * indate : 2018-03-29至2018-05-09
             * marketPrice : 199.0
             * clickRate : 214
             * lat : 28.20358
             * lng : 112.88926
             * stock : 0
             * validTimeStr : 2018-05-09
             * startTimeStr : 2018-03-29
             * detailImgs : [{"id":377,"content":"采用无酒精新配方。进一步提升保湿力、加强角质层补水作用，令肌肤持续水润细腻。 不仅水分不足的肌肤可用，皮脂过剩的油性肌肤也能在补水同时平衡水油。","contentType":1,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":1},{"id":378,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/ed52f5cc-4a42-4cc5-8aca-58ce5ab89170","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":2},{"id":379,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/66e76bb0-43fe-4c31-b8e1-289b904e34e5","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":3},{"id":380,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e123a21c-4894-4146-a3d8-5e98cee4661c","contentType":0,"kind":0,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":4}]
             * alternateImgs : [{"id":374,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/99732cd6-a4a0-4a42-834c-75c99eee0f89","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402377000,"modifyTime":1525402377000,"sort":1},{"id":375,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/44b1135b-c426-424c-9a36-b8197cdc11b4","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402377000,"modifyTime":1525402377000,"sort":2},{"id":376,"content":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e00e6f3f-3d63-4986-8d56-6721f8bc5dea","contentType":0,"kind":1,"productNum":"1","status":1,"createtime":1525402378000,"modifyTime":1525402378000,"sort":3}]
             */

            private int id;
            private String productNum;
            private int busiFatherType;
            private Object busiFatherName;
            private int busiParentType;
            private Object busiParentName;
            private double price;
            private String showImg;
            private String alternateImg;
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
            private long startTime;
            private Object endTime;
            private Object mobile;
            private String storeName;
            private String telephone;
            private String address;
            private double distance;
            private double starLevel;
            private Object detailImg1;
            private Object detailImg2;
            private Object detailImg3;
            private Object alternateImg1;
            private Object alternateImg2;
            private Object alternateImg3;
            private String useTime;
            private int sortId;
            private String reserveInformation;
            private String notes;
            private int refundMsgType;
            private double groupPrice;
            private Object sortName;
            private String indate;
            private double marketPrice;
            private int clickRate;
            private double lat;
            private double lng;
            private int stock;
            private String validTimeStr;
            private String startTimeStr;
            private List<String> alternateImgList;
            private List<String> detailImgList;
            private List<DetailImgsBean> detailImgs;
            private List<AlternateImgsBean> alternateImgs;

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

            public String getAlternateImg() {
                return alternateImg;
            }

            public void setAlternateImg(String alternateImg) {
                this.alternateImg = alternateImg;
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

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
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

            public Object getDetailImg1() {
                return detailImg1;
            }

            public void setDetailImg1(Object detailImg1) {
                this.detailImg1 = detailImg1;
            }

            public Object getDetailImg2() {
                return detailImg2;
            }

            public void setDetailImg2(Object detailImg2) {
                this.detailImg2 = detailImg2;
            }

            public Object getDetailImg3() {
                return detailImg3;
            }

            public void setDetailImg3(Object detailImg3) {
                this.detailImg3 = detailImg3;
            }

            public Object getAlternateImg1() {
                return alternateImg1;
            }

            public void setAlternateImg1(Object alternateImg1) {
                this.alternateImg1 = alternateImg1;
            }

            public Object getAlternateImg2() {
                return alternateImg2;
            }

            public void setAlternateImg2(Object alternateImg2) {
                this.alternateImg2 = alternateImg2;
            }

            public Object getAlternateImg3() {
                return alternateImg3;
            }

            public void setAlternateImg3(Object alternateImg3) {
                this.alternateImg3 = alternateImg3;
            }

            public String getUseTime() {
                return useTime;
            }

            public void setUseTime(String useTime) {
                this.useTime = useTime;
            }

            public int getSortId() {
                return sortId;
            }

            public void setSortId(int sortId) {
                this.sortId = sortId;
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

            public int getRefundMsgType() {
                return refundMsgType;
            }

            public void setRefundMsgType(int refundMsgType) {
                this.refundMsgType = refundMsgType;
            }

            public double getGroupPrice() {
                return groupPrice;
            }

            public void setGroupPrice(double groupPrice) {
                this.groupPrice = groupPrice;
            }

            public Object getSortName() {
                return sortName;
            }

            public void setSortName(Object sortName) {
                this.sortName = sortName;
            }

            public String getIndate() {
                return indate;
            }

            public void setIndate(String indate) {
                this.indate = indate;
            }

            public double getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(double marketPrice) {
                this.marketPrice = marketPrice;
            }

            public int getClickRate() {
                return clickRate;
            }

            public void setClickRate(int clickRate) {
                this.clickRate = clickRate;
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

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getValidTimeStr() {
                return validTimeStr;
            }

            public void setValidTimeStr(String validTimeStr) {
                this.validTimeStr = validTimeStr;
            }

            public String getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(String startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public List<String> getAlternateImgList() {
                return alternateImgList;
            }

            public void setAlternateImgList(List<String> alternateImgList) {
                this.alternateImgList = alternateImgList;
            }

            public List<String> getDetailImgList() {
                return detailImgList;
            }

            public void setDetailImgList(List<String> detailImgList) {
                this.detailImgList = detailImgList;
            }

            public List<DetailImgsBean> getDetailImgs() {
                return detailImgs;
            }

            public void setDetailImgs(List<DetailImgsBean> detailImgs) {
                this.detailImgs = detailImgs;
            }

            public List<AlternateImgsBean> getAlternateImgs() {
                return alternateImgs;
            }

            public void setAlternateImgs(List<AlternateImgsBean> alternateImgs) {
                this.alternateImgs = alternateImgs;
            }

            public static class DetailImgsBean implements Serializable {
                /**
                 * id : 377
                 * content : 采用无酒精新配方。进一步提升保湿力、加强角质层补水作用，令肌肤持续水润细腻。 不仅水分不足的肌肤可用，皮脂过剩的油性肌肤也能在补水同时平衡水油。
                 * contentType : 1
                 * kind : 0
                 * productNum : 1
                 * status : 1
                 * createtime : 1525402378000
                 * modifyTime : 1525402378000
                 * sort : 1
                 */

                private int id;
                private String content;
                private int contentType;
                private int kind;
                private String productNum;
                private int status;
                private long createtime;
                private long modifyTime;
                private int sort;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getContentType() {
                    return contentType;
                }

                public void setContentType(int contentType) {
                    this.contentType = contentType;
                }

                public int getKind() {
                    return kind;
                }

                public void setKind(int kind) {
                    this.kind = kind;
                }

                public String getProductNum() {
                    return productNum;
                }

                public void setProductNum(String productNum) {
                    this.productNum = productNum;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public long getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(long createtime) {
                    this.createtime = createtime;
                }

                public long getModifyTime() {
                    return modifyTime;
                }

                public void setModifyTime(long modifyTime) {
                    this.modifyTime = modifyTime;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }
            }

            public static class AlternateImgsBean implements Serializable {
                /**
                 * id : 374
                 * content : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/99732cd6-a4a0-4a42-834c-75c99eee0f89
                 * contentType : 0
                 * kind : 1
                 * productNum : 1
                 * status : 1
                 * createtime : 1525402377000
                 * modifyTime : 1525402377000
                 * sort : 1
                 */

                private int id;
                private String content;
                private int contentType;
                private int kind;
                private String productNum;
                private int status;
                private long createtime;
                private long modifyTime;
                private int sort;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getContentType() {
                    return contentType;
                }

                public void setContentType(int contentType) {
                    this.contentType = contentType;
                }

                public int getKind() {
                    return kind;
                }

                public void setKind(int kind) {
                    this.kind = kind;
                }

                public String getProductNum() {
                    return productNum;
                }

                public void setProductNum(String productNum) {
                    this.productNum = productNum;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public long getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(long createtime) {
                    this.createtime = createtime;
                }

                public long getModifyTime() {
                    return modifyTime;
                }

                public void setModifyTime(long modifyTime) {
                    this.modifyTime = modifyTime;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }
            }
        }
    }
}
