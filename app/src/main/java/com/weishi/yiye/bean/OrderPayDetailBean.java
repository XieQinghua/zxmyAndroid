package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/8
 * @Description：获取订单支付信息
 * @Version:v1.0.0
 *****************************/

public class OrderPayDetailBean {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":755,"userId":"23256","orderId":"1805091410170001","payInCash":0,"payInBalance":320,"payInScore":0,"payInCommission":0,"amount":320,"isDelete":"0","createTime":1525846218000,"scanOrderId":""}
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
         * id : 755
         * userId : 23256
         * orderId : 1805091410170001
         * payInCash : 0.0
         * payInBalance : 320.0
         * payInScore : 0.0
         * payInCommission : 0.0
         * amount : 320.0
         * isDelete : 0
         * createTime : 1525846218000
         * scanOrderId :
         */

        private int id;
        private String userId;
        private String orderId;
        private double payInCash;
        private double payInBalance;
        private double payInScore;
        private double payInCommission;
        private double amount;
        private String isDelete;
        private long createTime;
        private String scanOrderId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPayInCash() {
            return payInCash;
        }

        public void setPayInCash(double payInCash) {
            this.payInCash = payInCash;
        }

        public double getPayInBalance() {
            return payInBalance;
        }

        public void setPayInBalance(double payInBalance) {
            this.payInBalance = payInBalance;
        }

        public double getPayInScore() {
            return payInScore;
        }

        public void setPayInScore(double payInScore) {
            this.payInScore = payInScore;
        }

        public double getPayInCommission() {
            return payInCommission;
        }

        public void setPayInCommission(double payInCommission) {
            this.payInCommission = payInCommission;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getScanOrderId() {
            return scanOrderId;
        }

        public void setScanOrderId(String scanOrderId) {
            this.scanOrderId = scanOrderId;
        }
    }
}
