package com.weishi.yiye.bean;

import java.io.Serializable;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/2
 * @Description：我的邀请
 * @Version:v1.0.0
 *****************************/
public class InviteFriendBean implements Serializable {

    /**
     * msg : 操作成功
     * code : 200
     * data : {"subCount":4,"profitCount":59400,"secondSubCount":2,"nickname":"A","ids":[{"userId":23339},{"userId":23342},{"userId":23338},{"userId":23341}],"firstSubCount":2,"invitationCode":"TCK9P"}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * subCount : 4
         * profitCount : 59400
         * secondSubCount : 2
         * nickname : A
         * ids : [{"userId":23339},{"userId":23342},{"userId":23338},{"userId":23341}]
         * firstSubCount : 2
         * invitationCode : TCK9P
         */

        private int subCount;
        private int profitCount;
        private int secondSubCount;
        private String nickname;
        private int firstSubCount;
        private String invitationCode;
//        private List<IdsBean> ids;

        public int getSubCount() {
            return subCount;
        }

        public void setSubCount(int subCount) {
            this.subCount = subCount;
        }

        public int getProfitCount() {
            return profitCount;
        }

        public void setProfitCount(int profitCount) {
            this.profitCount = profitCount;
        }

        public int getSecondSubCount() {
            return secondSubCount;
        }

        public void setSecondSubCount(int secondSubCount) {
            this.secondSubCount = secondSubCount;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getFirstSubCount() {
            return firstSubCount;
        }

        public void setFirstSubCount(int firstSubCount) {
            this.firstSubCount = firstSubCount;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }

//        public List<IdsBean> getIds() {
//            return ids;
//        }
//
//        public void setIds(List<IdsBean> ids) {
//            this.ids = ids;
//        }
//
//        public static class IdsBean {
//            /**
//             * userId : 23339
//             */
//
//            private int userId;
//
//            public int getUserId() {
//                return userId;
//            }
//
//            public void setUserId(int userId) {
//                this.userId = userId;
//            }
//        }
    }
}
