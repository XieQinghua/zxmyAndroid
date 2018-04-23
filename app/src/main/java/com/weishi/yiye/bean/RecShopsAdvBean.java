package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/30
 * @Description：推荐商家广告
 * @Version:v1.0.0
 *****************************/

public class RecShopsAdvBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":104,"adImg":"http://zhongxiang.oss-cn-shanghai.aliyuncs.com/å\u008c»ç¾\u008e/è\u0084¸å\u008d\u009aå£«äº\u008cé\u0099¢æ\u0095´å½¢ï¼\u0088é\u0095¿æ²\u0099ï¼\u0089/674760f6c9cedaad9f58c27f77105c116591893.jpg","adType":4,"weight":50,"url":"e","belong":1,"adNumber":"商家推荐1","storeId":62,"status":1,"createtime":1522330897000,"authingTime":1522330897000,"busiType":0,"title":"e","skipType":2},{"id":105,"adImg":"https://zhongxiang.oss-cn-shanghai.aliyuncs.com/5.jpg","adType":4,"weight":49,"url":"ee","belong":1,"adNumber":"商家推荐2","storeId":60,"status":1,"createtime":1522330949000,"authingTime":1522330949000,"busiType":0,"title":"e","skipType":2},{"id":106,"adImg":"https://zhongxiang.oss-cn-shanghai.aliyuncs.com/3.jpg","adType":4,"weight":40,"url":"22","belong":1,"adNumber":"商家3","storeId":57,"status":1,"createtime":1522331213000,"authingTime":1522331213000,"busiType":0,"title":"ew","skipType":2},{"id":107,"adImg":"https://zhongxiang.oss-cn-shanghai.aliyuncs.com/10.jpg","adType":4,"weight":30,"url":"33","belong":1,"adNumber":"商家4","storeId":50,"status":1,"createtime":1522331136000,"authingTime":1522331138000,"busiType":0,"title":"ew","skipType":2}]
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
         * id : 104
         * adImg : http://zhongxiang.oss-cn-shanghai.aliyuncs.com/å»ç¾/è¸åå£«äºé¢æ´å½¢ï¼é¿æ²ï¼/674760f6c9cedaad9f58c27f77105c116591893.jpg
         * adType : 4
         * weight : 50
         * url : e
         * belong : 1
         * adNumber : 商家推荐1
         * storeId : 62
         * status : 1
         * createtime : 1522330897000
         * authingTime : 1522330897000
         * busiType : 0
         * title : e
         * skipType : 2
         */

        private int id;
        private String adImg;
        private int adType;
        private int weight;
        private String url;
        private int belong;
        private String adNumber;
        private int storeId;
        private int status;
        private long createtime;
        private long authingTime;
        private int busiType;
        private String title;
        private int skipType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdImg() {
            return adImg;
        }

        public void setAdImg(String adImg) {
            this.adImg = adImg;
        }

        public int getAdType() {
            return adType;
        }

        public void setAdType(int adType) {
            this.adType = adType;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getBelong() {
            return belong;
        }

        public void setBelong(int belong) {
            this.belong = belong;
        }

        public String getAdNumber() {
            return adNumber;
        }

        public void setAdNumber(String adNumber) {
            this.adNumber = adNumber;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getAuthingTime() {
            return authingTime;
        }

        public void setAuthingTime(long authingTime) {
            this.authingTime = authingTime;
        }

        public int getBusiType() {
            return busiType;
        }

        public void setBusiType(int busiType) {
            this.busiType = busiType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSkipType() {
            return skipType;
        }

        public void setSkipType(int skipType) {
            this.skipType = skipType;
        }
    }
}
