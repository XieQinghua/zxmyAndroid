package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/5/8
 * @Description：商家入驻资料展示
 * @Version:v1.0.0
 *****************************/

public class ShopsJoinInfoBean {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":23590,"businessName":"胡啦","corporationName":"胡啦","mobile":"18573261111","email":null,"licenceNo":"","licenceImg":"","createtime":1525253731000,"inviteCode":null,"busiFatherType":101,"busiParentType":-1,"busiFatherTypeName":"美发","busiParentTypeName":"","address":null,"status":1,"reason":null,"idCard":"432502199511114525","idCardImg":"","auditingTime":1525253827000,"userId":24096,"auditingUser":"超级管理员","payStatus":0,"payMoney":null,"onlinePay":null,"relationContent":null,"provinceCode":"220000","cityCode":"220600","areaCode":"220623","provinceName":"吉林省","cityName":"白山市","areaName":"长白朝鲜族自治县","businessSortType":-1,"businessSortTypeName":"","alternateImg":"http://zhongxiang.oss-cn-shanghai.aliyuncs.com/deb4786c337e40089d13d7291daa6928;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/14d0a7377dfe43978df8c4462d6c1d09;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/ac0bedaec40b4853889973e82b235d83http://zhongxiang.oss-cn-shanghai.aliyuncs.com/552e7bd8652446b8a08f647ce59192c7;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/b285e9f69bcc4bbbb010c22c2f84bd72;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/10f13c9bc0824641be8472de4ded2f7c","storeLogo":"http://zhongxiang.oss-cn-shanghai.aliyuncs.com/f208892c483e4bf2938488333036c6b9","storeName":"六天","shopkeeperName":"胡啦","lng":112.88683623075485,"lat":28.208297603414017}
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
         * id : 23590
         * businessName : 胡啦
         * corporationName : 胡啦
         * mobile : 18573261111
         * email : null
         * licenceNo :
         * licenceImg :
         * createtime : 1525253731000
         * inviteCode : null
         * busiFatherType : 101
         * busiParentType : -1
         * busiFatherTypeName : 美发
         * busiParentTypeName :
         * address : null
         * status : 1
         * reason : null
         * idCard : 432502199511114525
         * idCardImg :
         * auditingTime : 1525253827000
         * userId : 24096
         * auditingUser : 超级管理员
         * payStatus : 0
         * payMoney : null
         * onlinePay : null
         * relationContent : null
         * provinceCode : 220000
         * cityCode : 220600
         * areaCode : 220623
         * provinceName : 吉林省
         * cityName : 白山市
         * areaName : 长白朝鲜族自治县
         * businessSortType : -1
         * businessSortTypeName :
         * alternateImg : http://zhongxiang.oss-cn-shanghai.aliyuncs.com/deb4786c337e40089d13d7291daa6928;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/14d0a7377dfe43978df8c4462d6c1d09;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/ac0bedaec40b4853889973e82b235d83http://zhongxiang.oss-cn-shanghai.aliyuncs.com/552e7bd8652446b8a08f647ce59192c7;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/b285e9f69bcc4bbbb010c22c2f84bd72;http://zhongxiang.oss-cn-shanghai.aliyuncs.com/10f13c9bc0824641be8472de4ded2f7c
         * storeLogo : http://zhongxiang.oss-cn-shanghai.aliyuncs.com/f208892c483e4bf2938488333036c6b9
         * storeName : 六天
         * shopkeeperName : 胡啦
         * lng : 112.88683623075485
         * lat : 28.208297603414017
         */

        private int id;
        private String businessName;
        private String corporationName;
        private String mobile;
        private Object email;
        private String licenceNo;
        private String licenceImg;
        private long createtime;
        private String inviteCode;
        private int busiFatherType;
        private int busiParentType;
        private String busiFatherTypeName;
        private String busiParentTypeName;
        private String address;
        private int status;
        private Object reason;
        private String idCard;
        private String idCardImg;
        private long auditingTime;
        private int userId;
        private String auditingUser;
        private int payStatus;
        private Object payMoney;
        private Object onlinePay;
        private Object relationContent;
        private String provinceCode;
        private String cityCode;
        private String areaCode;
        private String provinceName;
        private String cityName;
        private String areaName;
        private int businessSortType;
        private String businessSortTypeName;
        private String alternateImg;
        private String storeLogo;
        private String storeName;
        private String shopkeeperName;
        private double lng;
        private double lat;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getCorporationName() {
            return corporationName;
        }

        public void setCorporationName(String corporationName) {
            this.corporationName = corporationName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getLicenceNo() {
            return licenceNo;
        }

        public void setLicenceNo(String licenceNo) {
            this.licenceNo = licenceNo;
        }

        public String getLicenceImg() {
            return licenceImg;
        }

        public void setLicenceImg(String licenceImg) {
            this.licenceImg = licenceImg;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
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

        public String getBusiFatherTypeName() {
            return busiFatherTypeName;
        }

        public void setBusiFatherTypeName(String busiFatherTypeName) {
            this.busiFatherTypeName = busiFatherTypeName;
        }

        public String getBusiParentTypeName() {
            return busiParentTypeName;
        }

        public void setBusiParentTypeName(String busiParentTypeName) {
            this.busiParentTypeName = busiParentTypeName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object reason) {
            this.reason = reason;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCardImg() {
            return idCardImg;
        }

        public void setIdCardImg(String idCardImg) {
            this.idCardImg = idCardImg;
        }

        public long getAuditingTime() {
            return auditingTime;
        }

        public void setAuditingTime(long auditingTime) {
            this.auditingTime = auditingTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAuditingUser() {
            return auditingUser;
        }

        public void setAuditingUser(String auditingUser) {
            this.auditingUser = auditingUser;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public Object getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(Object payMoney) {
            this.payMoney = payMoney;
        }

        public Object getOnlinePay() {
            return onlinePay;
        }

        public void setOnlinePay(Object onlinePay) {
            this.onlinePay = onlinePay;
        }

        public Object getRelationContent() {
            return relationContent;
        }

        public void setRelationContent(Object relationContent) {
            this.relationContent = relationContent;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getBusinessSortType() {
            return businessSortType;
        }

        public void setBusinessSortType(int businessSortType) {
            this.businessSortType = businessSortType;
        }

        public String getBusinessSortTypeName() {
            return businessSortTypeName;
        }

        public void setBusinessSortTypeName(String businessSortTypeName) {
            this.businessSortTypeName = businessSortTypeName;
        }

        public String getAlternateImg() {
            return alternateImg;
        }

        public void setAlternateImg(String alternateImg) {
            this.alternateImg = alternateImg;
        }

        public String getStoreLogo() {
            return storeLogo;
        }

        public void setStoreLogo(String storeLogo) {
            this.storeLogo = storeLogo;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getShopkeeperName() {
            return shopkeeperName;
        }

        public void setShopkeeperName(String shopkeeperName) {
            this.shopkeeperName = shopkeeperName;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
