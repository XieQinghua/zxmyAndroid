package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/9
 * @Description：手机号码登录绑定微信bean
 * @Version:v1.0.0
 *****************************/

public class WeiXinBindBean {

    /**
     * code : 200
     * message : success
     * data : {"uid":116,"validPeriod":2592000000,"token":"YzI2MTVhMDAzMjVhNDdmYWE5MDQxMzg4ZjIxZDA0ZWM="}
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
         * uid : 116
         * validPeriod : 2592000000
         * token : YzI2MTVhMDAzMjVhNDdmYWE5MDQxMzg4ZjIxZDA0ZWM=
         */

        private int uid;
        private long validPeriod;
        private String token;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public long getValidPeriod() {
            return validPeriod;
        }

        public void setValidPeriod(long validPeriod) {
            this.validPeriod = validPeriod;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
