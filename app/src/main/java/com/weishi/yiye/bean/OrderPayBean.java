package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/8
 * @Description：美业支付订单bean
 * @Version:v1.0.0
 *****************************/

public class OrderPayBean {

    /**
     * code : 200
     * message : success
     * data : {"isNeedToPay":true}
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
         * isNeedToPay : true
         */

        private boolean isNeedToPay;

        public boolean isIsNeedToPay() {
            return isNeedToPay;
        }

        public void setIsNeedToPay(boolean isNeedToPay) {
            this.isNeedToPay = isNeedToPay;
        }
    }
}
