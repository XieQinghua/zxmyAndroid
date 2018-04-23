package com.weishi.yiye.bean.eventbus;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：修改个人信息
 * @Version:v1.0.0
 *****************************/
public class ChangeUserInfoEvent {
    private String nickName;
    private String avatar;

    public ChangeUserInfoEvent(String nickName, String avatar) {
        this.nickName = nickName;
        this.avatar = avatar;
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
}
