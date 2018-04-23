package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/8
 * @Description：商家入驻
 * @Version:v1.0.0
 *****************************/

public class ShopsJoinBean {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":75,"businessName":"哈哈哈","corporationName":"谢谢","mobile":"15200917596","email":null,"licenceNo":"123456","licenceImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/42989815f8324cd6a975aca3c1bcc4b9","createtime":1520521194000,"inviteCode":"w7sk","busiFatherType":1,"busiParentType":1,"busiFatherTypeName":"美容","busiParentTypeName":"美容","address":"麓谷","status":0,"reason":null,"idCard":"430124198809024975","idCardImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/2a4c41bfa468471bb172fc45329b356a","auditingTime":null,"userId":23256,"auditingUser":null,"payStatus":0,"payMoney":0.01,"onlinePay":1}
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
         * id : 75
         * businessName : 哈哈哈
         * corporationName : 谢谢
         * mobile : 15200917596
         * email : null
         * licenceNo : 123456
         * licenceImg : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/42989815f8324cd6a975aca3c1bcc4b9
         * createtime : 1520521194000
         * inviteCode : w7sk
         * busiFatherType : 1
         * busiParentType : 1
         * busiFatherTypeName : 美容
         * busiParentTypeName : 美容
         * address : 麓谷
         * status : 0
         * reason : null
         * idCard : 430124198809024975
         * idCardImg : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/2a4c41bfa468471bb172fc45329b356a
         * auditingTime : null
         * userId : 23256
         * auditingUser : null
         * payStatus : 0
         * payMoney : 0.01
         * onlinePay : 1
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
        private Object auditingTime;
        private int userId;
        private Object auditingUser;
        private int payStatus;
        private double payMoney;
        private int onlinePay;

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

        public Object getAuditingTime() {
            return auditingTime;
        }

        public void setAuditingTime(Object auditingTime) {
            this.auditingTime = auditingTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getAuditingUser() {
            return auditingUser;
        }

        public void setAuditingUser(Object auditingUser) {
            this.auditingUser = auditingUser;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public double getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(double payMoney) {
            this.payMoney = payMoney;
        }

        public int getOnlinePay() {
            return onlinePay;
        }

        public void setOnlinePay(int onlinePay) {
            this.onlinePay = onlinePay;
        }
    }
}
