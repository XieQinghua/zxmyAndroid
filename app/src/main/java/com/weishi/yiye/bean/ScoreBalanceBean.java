package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：积分余额bean
 * @Version:v1.0.0
 *****************************/

public class ScoreBalanceBean {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"totalScore":10005350000}
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
         * totalScore : 10005350000
         */

        private long totalScore;

        public long getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(long totalScore) {
            this.totalScore = totalScore;
        }
    }
}
