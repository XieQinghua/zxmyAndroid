package com.weishi.yiye.bean;

import java.io.Serializable;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：当前用户的订单数量bean
 * @Version:v1.0.0
 *****************************/

public class RechargeBean {

    /**
     * code : 200
     * message : success
     * data : {"id":null,"orderNum":null,"orderPrice":null,"rewardPoint":null,"userId":null,"mobile":null,"useStatus":null,"orderStatus":null,"commentStatus":null,"payMethod":null,"createtime":null,"useTime":null,"useCode":null,"payTime":null,"modifyTime":null,"cancleTime":null,"productId":null,"productName":null,"storeId":null,"tagType":null,"number":null,"validTime":null,"showImg":null,"storeName":null,"address":null,"phone":null,"payPasswd":null,"pendPayNum":39,"pendUseNum":0,"pendCommentNum":0,"refundNum":0,"orderStatusStr":null}
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

        public String orderId;//	String	是	订单号
        public String thirdTradeNo;//第三方交易流水号
        public WechatPayData wxdata;//微信支付参数接收对象
        public String orderStr;//支付宝支付接收字符串

        public static class WechatPayData implements Serializable {
            public String appid;//
            public String noncestr;
            public String partnerid;
            public String prepayid;
            public String sign;
            public String timestamp;
        }
    }
}
