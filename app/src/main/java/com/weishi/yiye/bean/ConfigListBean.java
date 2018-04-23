package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/15
 * @Description：
 * @Version:v1.0.0
 *****************************/

public class ConfigListBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":1,"key":"isOpenOnlinePay","name":"是否支持线上支付，0关闭1开启","kind":"base","sort":1,"gmtCreateTime":1520434166000,"isDelete":0,"value":"1"},{"id":218,"key":"storeDetail","name":"店铺详情通用地址","kind":"base","sort":1,"gmtCreateTime":1520939897000,"isDelete":0,"value":"http://yiye-h5-test.lianqumall.com/yylmh5/html/detail.html"},{"id":219,"key":"productDetail","name":"商品详情通用地址","kind":"base","sort":1,"gmtCreateTime":1520939950000,"isDelete":0,"value":"http://yiye-h5-test.lianqumall.com/yylmh5/html/enter_store.html"},{"id":220,"key":"invitedRegister","name":"邀请码通用地址","kind":"base","sort":1,"gmtCreateTime":1520940001000,"isDelete":0,"value":"http://yiye-h5-test.lianqumall.com/yylmh5/html/register.html"},{"id":221,"key":"userAgreement","name":"用户协议地址","kind":"base","sort":1,"gmtCreateTime":1520940118000,"isDelete":0,"value":"http://yiye-h5-test.lianqumall.com/yylmh5/html/user_agreement.html"},{"id":223,"key":"userCoverCharge","name":"用户服务费","kind":"base","sort":1,"gmtCreateTime":1520948207000,"isDelete":0,"value":"10%"},{"id":224,"key":"belongBusiCharge","name":"用户归属该商家的商家服务费","kind":"base","sort":1,"gmtCreateTime":1520948311000,"isDelete":0,"value":"10%"},{"id":225,"key":"notBelongBusiCharge","name":"用户不归属该商家的商家服务费","kind":"base","sort":1,"gmtCreateTime":1520948346000,"isDelete":0,"value":"15%"}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * key : isOpenOnlinePay
         * name : 是否支持线上支付，0关闭1开启
         * kind : base
         * sort : 1
         * gmtCreateTime : 1520434166000
         * isDelete : 0
         * value : 1
         */

        private int id;
        private String key;
        private String name;
        private String kind;
        private int sort;
        private long gmtCreateTime;
        private int isDelete;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public long getGmtCreateTime() {
            return gmtCreateTime;
        }

        public void setGmtCreateTime(long gmtCreateTime) {
            this.gmtCreateTime = gmtCreateTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
