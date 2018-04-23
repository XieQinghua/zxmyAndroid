package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/25
 * @Description：
 * @Version:v1.0.0
 *****************************/

public class LoginBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"userInfo":{"registerTime":"2018-01-25 22:14:06","sex":0,"avatar":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/d914c67256fb4f479817e63df61cc44c","userId":116,"totalScore":10005350000,"createPlatform":"D","phone":"15200917596","invitationUrl":null,"nickname":"yyy","refereeUserID":null,"userType":1,"firstFlag":1,"againLogin":null,"invitationCode":null,"email":null},"loginTime":1518317005679,"isExists":false,"isBind":false,"isNew":false,"expireAt":1520909005679,"validPeriod":2592000000,"token":"OTQ5OTE0MzdkM2RlNDlmOTg5M2Y2YzkwMjI0ODUwZDg="}
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
         * userInfo : {"registerTime":"2018-01-25 22:14:06","sex":0,"avatar":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/d914c67256fb4f479817e63df61cc44c","userId":116,"totalScore":10005350000,"createPlatform":"D","phone":"15200917596","invitationUrl":null,"nickname":"yyy","refereeUserID":null,"userType":1,"firstFlag":1,"againLogin":null,"invitationCode":null,"email":null}
         * loginTime : 1518317005679
         * isExists : false
         * isBind : false
         * isNew : false
         * expireAt : 1520909005679
         * validPeriod : 2592000000
         * token : OTQ5OTE0MzdkM2RlNDlmOTg5M2Y2YzkwMjI0ODUwZDg=
         */

        private UserInfoBean userInfo;
        private long loginTime;
        private boolean isExists;
        private boolean isBind;
        private boolean isNew;
        private long expireAt;
        private long validPeriod;
        private String token;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public long getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(long loginTime) {
            this.loginTime = loginTime;
        }

        public boolean isIsExists() {
            return isExists;
        }

        public void setIsExists(boolean isExists) {
            this.isExists = isExists;
        }

        public boolean isIsBind() {
            return isBind;
        }

        public void setIsBind(boolean isBind) {
            this.isBind = isBind;
        }

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public long getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(long expireAt) {
            this.expireAt = expireAt;
        }

        public long getValidPeriod() {
            return validPeriod;
        }

        public void setValidPeriod(long validPeriod) {
            this.validPeriod = validPeriod;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserInfoBean {
            /**
             * registerTime : 2018-01-25 22:14:06
             * sex : 0
             * avatar : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/d914c67256fb4f479817e63df61cc44c
             * userId : 116
             * totalScore : 10005350000
             * createPlatform : D
             * phone : 15200917596
             * invitationUrl : null
             * nickname : yyy
             * refereeUserID : null
             * userType : 1
             * firstFlag : 1
             * againLogin : null
             * invitationCode : null
             * email : null
             */

            private String registerTime;
            private int sex;
            private String avatar;
            private int userId;
            private long totalScore;
            private String createPlatform;
            private String phone;
            private Object invitationUrl;
            private String nickname;
            private Object refereeUserID;
            private int userType;
            private int firstFlag;
            private Object againLogin;
            private Object invitationCode;
            private Object email;

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
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

            public long getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(long totalScore) {
                this.totalScore = totalScore;
            }

            public String getCreatePlatform() {
                return createPlatform;
            }

            public void setCreatePlatform(String createPlatform) {
                this.createPlatform = createPlatform;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getInvitationUrl() {
                return invitationUrl;
            }

            public void setInvitationUrl(Object invitationUrl) {
                this.invitationUrl = invitationUrl;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getRefereeUserID() {
                return refereeUserID;
            }

            public void setRefereeUserID(Object refereeUserID) {
                this.refereeUserID = refereeUserID;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public int getFirstFlag() {
                return firstFlag;
            }

            public void setFirstFlag(int firstFlag) {
                this.firstFlag = firstFlag;
            }

            public Object getAgainLogin() {
                return againLogin;
            }

            public void setAgainLogin(Object againLogin) {
                this.againLogin = againLogin;
            }

            public Object getInvitationCode() {
                return invitationCode;
            }

            public void setInvitationCode(Object invitationCode) {
                this.invitationCode = invitationCode;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }
        }
    }
}
