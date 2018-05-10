package com.weishi.yiye.common;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：常量
 * @Version:v1.0.0
 *****************************/
public class Constants {
    public static final String BUGLY_ID = "dc5b54677d";

    public static final String ORDER_TYPE_WHOLE = "0";
    public static final String ORDER_TYPE_TAKEPAYMENT = "1";
    public static final String ORDER_TYPE_BANDUSE = "2";
    public static final String ORDER_TYPE_EVALUATION = "3";
    public static final String ORDER_TYPE_REFUND = "4";

    public static final String IS_LOGIN = "isLogin";
    public static final String PHONE = "phone";
    public static final String SEX = "sex";
    public static final String NICKNAME = "nickname";
    public static final String USER_TYPE = "userType";
    public static final String AVATAR = "avatar";
    public static final String USER_ID = "userId";
    public static final String TOKEN = "token";
    public static final String IS_EXISTS = "isExists";
    public static final String IS_BIND = "isBind";
    public static final String INVITE_CODE = "inviteCode";
    public static final String IS_SET_PAYMENTCODE = "setPaymentCode";
    public static final String LOGIN_TYPE = "loginType";
    public static final String ROLE_NAME = "roleName";

    /**
     * 支付方式定义
     **/
    public static final String ALIPAY_APP = "AlipayApp";
    public static final String TENGPAY_APP = "TengPayApp";
    public static final String UNIONPAY_APP = "UnionPayApp";

    /**
     * 微信支付AppID
     */
    public static final String WETCHAT_APP_ID = "wx09c468b270e307bd";

    /**
     * 加载公共广告的URL
     */
    public static final String INTENT_COMMON_ADV_URL = "advUrl";
    /**
     * 加载公共广告的标题
     */
    public static final String INTENT_COMMON_ADV_TITLE = "advTitle";

    /**
     * 区域相关的常量:省
     */
    public static final String AREA_PROVINCE = "province";
    public static final String AREA_PROVINCE_CODE = "provinceCode";
    public static final String AREA_PROVINCE_NAME = "provinceName";
    /**
     * 区域相关的常量:市
     */
    public static final String AREA_CITY = "city";
    public static final String AREA_CITY_CODE = "cityCode";
    public static final String AREA_CITY_NAME = "cityName";
    /**
     * 区域相关的常量:区
     */
    public static final String AREA_AREA = "area";
    public static final String AREA_AREA_CODE = "areaCode";
    public static final String AREA_AREA_NAME = "areaName";

    //相册中图片对象集合
    public static final String IMAGE_LIST = "image_list";
    //相册名称
    public static final String BUCKET_NAME = "buck_name";
    //可添加的图片数量
    public static final String CAN_ADD_IMAGE_SIZE = "can_add_image_size";
    //当前选择的照片位置
    public static final String CURRENT_IMG_POSITION = "current_img_position";
}
