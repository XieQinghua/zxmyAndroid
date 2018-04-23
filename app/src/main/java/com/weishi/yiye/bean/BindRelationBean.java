package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/9
 * @Description：绑定关系bean
 * @Version:v1.0.0
 *****************************/

public class BindRelationBean {

    /**
     * code : 200
     * message : success
     * data : {"isBindPhone":1,"isBindThird":1}
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
         * isBindPhone : 1
         * isBindThird : 1
         */

        private int isBindPhone;
        private int isBindThird;

        public int getIsBindPhone() {
            return isBindPhone;
        }

        public void setIsBindPhone(int isBindPhone) {
            this.isBindPhone = isBindPhone;
        }

        public int getIsBindThird() {
            return isBindThird;
        }

        public void setIsBindThird(int isBindThird) {
            this.isBindThird = isBindThird;
        }
    }
}
