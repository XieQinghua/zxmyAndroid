package com.weishi.yiye.bean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/30
 * @Description：用户信息bean
 * @Version:v1.0.0
 *****************************/

public class UserInfoBean {

    /**
     * code : 200
     * message : success
     * data : {"id":23256,"createtime":null,"status":null,"auditingTime":null,"nickName":"郎多","avatar":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e73e200bbc1644a18271557d188132d7","mobile":null,"beInvited":null,"inviteCode":null,"roleName":"普通用户","phone":null,"totalScore":null}
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
         * id : 23256
         * createtime : null
         * status : null
         * auditingTime : null
         * nickName : 郎多
         * avatar : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/e73e200bbc1644a18271557d188132d7
         * mobile : null
         * beInvited : null
         * inviteCode : null
         * roleName : 普通用户
         * phone : null
         * totalScore : null
         */

        private int id;
        private Object createtime;
        private Object status;
        private Object auditingTime;
        private String nickName;
        private String avatar;
        private Object mobile;
        private Object beInvited;
        private Object inviteCode;
        private String roleName;
        private Object phone;
        private Object totalScore;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getAuditingTime() {
            return auditingTime;
        }

        public void setAuditingTime(Object auditingTime) {
            this.auditingTime = auditingTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public Object getBeInvited() {
            return beInvited;
        }

        public void setBeInvited(Object beInvited) {
            this.beInvited = beInvited;
        }

        public Object getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(Object inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(Object totalScore) {
            this.totalScore = totalScore;
        }
    }
}
