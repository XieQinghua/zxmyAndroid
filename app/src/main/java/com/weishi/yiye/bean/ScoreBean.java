package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/18
 * @Description：积分bean
 * @Version:v1.0.0
 *****************************/

public class ScoreBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":1,"point":8,"price":8,"creatttime":1517487727000,"modifytime":1517487735000,"modifyUser":2},{"id":2,"point":88,"price":88,"creatttime":1517487749000,"modifytime":1517574152000,"modifyUser":2},{"id":3,"point":108,"price":108,"creatttime":1517487770000,"modifytime":1517574173000,"modifyUser":2},{"id":4,"point":208,"price":208,"creatttime":1517487789000,"modifytime":1517574192000,"modifyUser":2},{"id":5,"point":888,"price":888,"creatttime":1517487822000,"modifytime":1517574225000,"modifyUser":2},{"id":6,"point":8888,"price":8888,"creatttime":1517487841000,"modifytime":1517574244000,"modifyUser":2}]
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
         * point : 8
         * price : 8
         * creatttime : 1517487727000
         * modifytime : 1517487735000
         * modifyUser : 2
         */

        private int id;
        private int point;
        private double price;
        private long creatttime;
        private long modifytime;
        private int modifyUser;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public long getCreatttime() {
            return creatttime;
        }

        public void setCreatttime(long creatttime) {
            this.creatttime = creatttime;
        }

        public long getModifytime() {
            return modifytime;
        }

        public void setModifytime(long modifytime) {
            this.modifytime = modifytime;
        }

        public int getModifyUser() {
            return modifyUser;
        }

        public void setModifyUser(int modifyUser) {
            this.modifyUser = modifyUser;
        }
    }
}
