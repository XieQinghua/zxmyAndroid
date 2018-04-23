package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/30
 * @Description：佣金收益bean
 * @Version:v1.0.0
 *****************************/

public class CommissionBean implements Serializable {

    /**
     * code : 200
     * message : 操作成功
     * data : {"total":2,"commissionDatas":[{"scoreSn":"279_990.0_50.0","commissionRate":"","userLevel":"代理商","consumeScore":0,"occurTime":"2018-03-31 15:56:06","nickname":"用户23861","profitScore":4950000,"busiType":"PDIS","userId":23861},{"scoreSn":"271","commissionRate":"","userLevel":"代理商","consumeScore":0,"occurTime":"2018-03-30 22:21:25","nickname":"用户23835","profitScore":4950000,"busiType":"PDIS","userId":23835}]}
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
         * total : 2
         * commissionDatas : [{"scoreSn":"279_990.0_50.0","commissionRate":"","userLevel":"代理商","consumeScore":0,"occurTime":"2018-03-31 15:56:06","nickname":"用户23861","profitScore":4950000,"busiType":"PDIS","userId":23861},{"scoreSn":"271","commissionRate":"","userLevel":"代理商","consumeScore":0,"occurTime":"2018-03-30 22:21:25","nickname":"用户23835","profitScore":4950000,"busiType":"PDIS","userId":23835}]
         */

        private int total;
        private List<CommissionDatasBean> commissionDatas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<CommissionDatasBean> getCommissionDatas() {
            return commissionDatas;
        }

        public void setCommissionDatas(List<CommissionDatasBean> commissionDatas) {
            this.commissionDatas = commissionDatas;
        }

        public static class CommissionDatasBean {
            /**
             * scoreSn : 279_990.0_50.0
             * commissionRate :
             * userLevel : 代理商
             * consumeScore : 0.0
             * occurTime : 2018-03-31 15:56:06
             * nickname : 用户23861
             * profitScore : 4950000.0
             * busiType : PDIS
             * userId : 23861
             */

            private String scoreSn;
            private String commissionRate;
            private String userLevel;
            private double consumeScore;
            private String occurTime;
            private String nickname;
            private double profitScore;
            private String busiType;
            private String avatar;
            private int userId;

            public String getScoreSn() {
                return scoreSn;
            }

            public void setScoreSn(String scoreSn) {
                this.scoreSn = scoreSn;
            }

            public String getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(String commissionRate) {
                this.commissionRate = commissionRate;
            }

            public String getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(String userLevel) {
                this.userLevel = userLevel;
            }

            public double getConsumeScore() {
                return consumeScore;
            }

            public void setConsumeScore(double consumeScore) {
                this.consumeScore = consumeScore;
            }

            public String getOccurTime() {
                return occurTime;
            }

            public void setOccurTime(String occurTime) {
                this.occurTime = occurTime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public double getProfitScore() {
                return profitScore;
            }

            public void setProfitScore(double profitScore) {
                this.profitScore = profitScore;
            }

            public String getBusiType() {
                return busiType;
            }

            public void setBusiType(String busiType) {
                this.busiType = busiType;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
