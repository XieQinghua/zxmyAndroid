package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/3
 * @Description：关键词bean
 * @Version:v1.0.0
 *****************************/
public class KeyWordBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":1,"keyword":"自助餐","searchNum":100008,"createtime":1517480337000,"modifyTime":1517480339000},{"id":2,"keyword":"皇冠KTV","searchNum":99999,"createtime":1517480369000,"modifyTime":1517480371000},{"id":3,"keyword":"湘菜","searchNum":99999,"createtime":1517480394000,"modifyTime":1517480396000},{"id":8,"keyword":"冠军台球室","searchNum":99999,"createtime":1517480491000,"modifyTime":1517480492000}]
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
         * keyword : 自助餐
         * searchNum : 100008
         * createtime : 1517480337000
         * modifyTime : 1517480339000
         */

        private int id;
        private String keyword;
        private int searchNum;
        private long createtime;
        private long modifyTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getSearchNum() {
            return searchNum;
        }

        public void setSearchNum(int searchNum) {
            this.searchNum = searchNum;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }
    }
}
