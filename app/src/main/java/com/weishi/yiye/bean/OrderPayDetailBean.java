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
     * data : {"id":477,"userId":"23256","orderId":"1803082101180001","payInCash":150,"payInBalance":0,"payInScore":0,"payInCommission":0,"payInPaymentForGoods":0,"isDelete":"0","createTime":1520542878000,"scanOrderId":""}
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
         * id : 477
         * userId : 23256
         * orderId : 1803082101180001
         * payInCash : 150.0
         * payInBalance : 0.0
         * payInScore : 0.0
         * payInCommission : 0.0
         * payInPaymentForGoods : 0.0
         * isDelete : 0
         * createTime : 1520542878000
         * scanOrderId :
         */

        private int id;
        private String userId;
        private String orderId;
        private double payInCash;
        private double payInBalance;
        private double payInScore;
        private double payInCommission;
        private double payInPaymentForGoods;
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

        public double getPayInPaymentForGoods() {
            return payInPaymentForGoods;
        }

        public void setPayInPaymentForGoods(double payInPaymentForGoods) {
            this.payInPaymentForGoods = payInPaymentForGoods;
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
