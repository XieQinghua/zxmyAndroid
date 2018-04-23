package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/30
 * @Description：用户代理数据bean
 * @Version:v1.0.0
 *****************************/

public class AgentDataBean implements Serializable {

    /**
     * code : 200
     * message : 操作成功
     * data : {"total":7,"userAgentDataItems":[{"registerTime":"2018-03-08 19:27:33","nickname":"叶周永","firstDisSub":0,"avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ8h7EdibB80tWTWdnn1q0aE7WcYyXqk18lhOADuVTGKAC4ULqdqUVcKEhPQTl85iblSkQOEm5KmHfw/132","userId":23266,"countScore":0},{"registerTime":"2018-03-30 19:56:02","nickname":"用户23836","firstDisSub":0,"avatar":"","userId":23836,"countScore":0},{"registerTime":"2018-03-31 09:30:24","nickname":"用户23843","firstDisSub":0,"avatar":"","userId":23843,"countScore":0},{"registerTime":"2018-03-31 09:31:09","nickname":"用户23844","firstDisSub":1,"avatar":"","userId":23844,"countScore":0},{"registerTime":"2018-03-31 09:46:26","nickname":"用户23846","firstDisSub":0,"avatar":"","userId":23846,"countScore":0},{"registerTime":"2018-03-31 09:51:46","nickname":"用户23847","firstDisSub":4,"avatar":"","userId":23847,"countScore":0},{"registerTime":"2018-03-31 09:56:26","nickname":"用户23853","firstDisSub":0,"avatar":"","userId":23853,"countScore":0}]}
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
         * total : 7
         * userAgentDataItems : [{"registerTime":"2018-03-08 19:27:33","nickname":"叶周永","firstDisSub":0,"avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ8h7EdibB80tWTWdnn1q0aE7WcYyXqk18lhOADuVTGKAC4ULqdqUVcKEhPQTl85iblSkQOEm5KmHfw/132","userId":23266,"countScore":0},{"registerTime":"2018-03-30 19:56:02","nickname":"用户23836","firstDisSub":0,"avatar":"","userId":23836,"countScore":0},{"registerTime":"2018-03-31 09:30:24","nickname":"用户23843","firstDisSub":0,"avatar":"","userId":23843,"countScore":0},{"registerTime":"2018-03-31 09:31:09","nickname":"用户23844","firstDisSub":1,"avatar":"","userId":23844,"countScore":0},{"registerTime":"2018-03-31 09:46:26","nickname":"用户23846","firstDisSub":0,"avatar":"","userId":23846,"countScore":0},{"registerTime":"2018-03-31 09:51:46","nickname":"用户23847","firstDisSub":4,"avatar":"","userId":23847,"countScore":0},{"registerTime":"2018-03-31 09:56:26","nickname":"用户23853","firstDisSub":0,"avatar":"","userId":23853,"countScore":0}]
         */

        private int total;
        private List<UserAgentDataItemsBean> userAgentDataItems;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<UserAgentDataItemsBean> getUserAgentDataItems() {
            return userAgentDataItems;
        }

        public void setUserAgentDataItems(List<UserAgentDataItemsBean> userAgentDataItems) {
            this.userAgentDataItems = userAgentDataItems;
        }

        public static class UserAgentDataItemsBean {
            /**
             * registerTime : 2018-03-08 19:27:33
             * nickname : 叶周永
             * firstDisSub : 0
             * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ8h7EdibB80tWTWdnn1q0aE7WcYyXqk18lhOADuVTGKAC4ULqdqUVcKEhPQTl85iblSkQOEm5KmHfw/132
             * userId : 23266
             * countScore : 0.0
             */

            private String registerTime;
            private String nickname;
            private int firstDisSub;
            private String avatar;
            private int userId;
            private double countScore;

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getFirstDisSub() {
                return firstDisSub;
            }

            public void setFirstDisSub(int firstDisSub) {
                this.firstDisSub = firstDisSub;
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

            public double getCountScore() {
                return countScore;
            }

            public void setCountScore(double countScore) {
                this.countScore = countScore;
            }
        }
    }
}
