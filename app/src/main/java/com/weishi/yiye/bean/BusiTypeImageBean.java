package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/31
 * @Description：查询店铺图片分类和个数
 * @Version:v1.0.0
 *****************************/

public class BusiTypeImageBean {

    /**
     * code : 200
     * message : success
     * data : [{"className":"美容","number":2,"id":1},{"className":"店铺","number":1,"id":6},{"className":"环境","number":6,"id":7},{"className":"美容","number":10,"id":8},{"className":"美发","number":10,"id":9},{"className":"日化","number":0,"id":10},{"className":"医美","number":0,"id":11},{"className":"嘻嘻","number":4,"id":18},{"className":"全部","number":33,"id":0}]
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
         * className : 美容
         * number : 2
         * id : 1
         */

        private String className;
        private int number;
        private int id;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
