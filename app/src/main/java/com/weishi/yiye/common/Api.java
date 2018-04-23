package com.weishi.yiye.common;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/25
 * @Description：接口
 * @Version:v1.0.0
 *****************************/
public class Api {
    /**
     * 测试环境
     */
//    public static final String SERVER_URL = "http://yiye-test.lianqumall.com/";

    /**
     * 李季测试地址
     */
//    public static final String SERVER_URL = "http://192.168.3.5:9898/";

    /**
     * 谢宇翔测试地址
     */
//    public static final String SERVER_URL = "http://192.168.0.192:9898/";

    /**
     * 众享美业地址
     */
//    public static final String SERVER_URL = "http://120.79.147.7:10080/";

    /**
     * 众享美业测试环境地址
     */
    public static final String SERVER_URL = "http://47.98.103.60:10080/";

    public static final String STATE_SUCCESS = "200";

    public static final String STATE_SUCCESS_DATA = "SUCCESS";

    /******************************商品******************************/

    /**
     * 判断用户是否收藏该商品
     */
    public static final String GET_FAVORITE_STATUS = SERVER_URL + "api/front/product/getFavoriteStatus";

    /**
     * 取消商品收藏
     */
    public static final String DROP_PORDUCT_COLLECT = SERVER_URL + "api/front/product/dropPorductCollect";

    /**
     * 新增商品收藏
     */
    public static final String ADD_PORDUCT_COLLECT = SERVER_URL + "api/front/product/addPorductCollect";

    /**
     * 查询用户收藏的商品列表
     */
    public static final String GET_FAVORITE_PRODUCT = SERVER_URL + "api/front/product/getFavoriteProduct";

    /**
     * 查询商品信息
     */
    public static final String GET_PRODUCT_INFO = SERVER_URL + "api/front/product/getProductInfo";

    /**
     * 查询商品评论信息
     */
    public static final String GET_BATCH_USERINFO_BY_ID = SERVER_URL + "api/front/product/getBatchUserInfoById";

    /**
     * 查询店铺下的商品列表信息
     */
    public static final String GET_PRODUCTINFO_BY_STORE_ID = SERVER_URL + "api/front/product/getProductInfoByStoreId";

    /******************************商家******************************/

    /**
     * 商家分类
     */
    public static final String GET_BUSI_TYPE = SERVER_URL + "api/front/business/getBusiType";

    /**
     * 众享爆品
     */
    public static final String GET_EXPLOSIVE_STORE_LIST = SERVER_URL + "api/front/store/getExplosiveStoreList";

    /**
     * 获取商家列表
     */
    public static final String GET_STORE_LIST_BY_STORE = SERVER_URL + "api/front/store/getStoreListByStore";

    /**
     * 根据邀请码获取商家信息
     */
    public static final String GET_STORE_BY_INVIDE_CODE = SERVER_URL + "api/front/business/getBusinessInfoByInviteCode";


    /******************************广告******************************/

    /**
     * 首页banner图
     */
    public static final String GET_ADSHOW_LIST = SERVER_URL + "api/front/ad/getAdShowList";

    /******************************店铺******************************/

    /**
     * 判断用户是否收藏了该店铺
     */
    public static final String GET_STORE_COLLECT_BY_USER = SERVER_URL + "api/front/store/getStoreCollectByUser";

    /**
     * 取消店铺收藏
     */
    public static final String DORP_STORE_COLLECT = SERVER_URL + "api/front/store/dorpStoreCollect";

    /**
     * 新增店铺收藏
     */
    public static final String ADD_STORE_COLLECT = SERVER_URL + "api/front/store/addStoreCollect";

    /**
     * 查询用户收藏的店铺列表
     */
    public static final String GET_STORE_COLLECT_INFOS = SERVER_URL + "api/front/store/getStoreCollectInfos";

    /**
     * 查询店铺图片分类和个数
     */
    public static final String GET_BUSI_TYPE_IMAGE = SERVER_URL + "api/front/store/getBusiTypeImage";

    /**
     * 查询店铺相对的分类图片信息
     */
    public static final String GET_BUSI_TYPE_IMAGE_INFO = SERVER_URL + "api/front/store/getBusiTypeImageInfo";

    /**
     * 查询店铺信息
     */
    public static final String GET_STORE_INFOS = SERVER_URL + "api/front/store/getStoreInfos";

    /******************************异业联盟公共接口******************************/

    /**
     * 校验用户token是否有效并更新缓存的token过期时间
     */
    public static final String VALIDATE_TOKEN = SERVER_URL + "api/front/user/validateToken";

    /**
     * 获取短信验证码接口
     */
    public static final String GET_VERIFY_CODE = SERVER_URL + "api/front/user/getVerifyCode";

    /******************************搜索******************************/

    /**
     * 搜索功能接口
     */
    public static final String GET_LIST_BY_KEYWORD = SERVER_URL + "api/front/keyword/getListByKeyword";

    /**
     * 搜索关键词接口
     */
    public static final String GET_HOT_SEARCH_KEYWORD = SERVER_URL + "api/front/keyword/getHotSearchKeyWord";

    /******************************用户******************************/

    /**
     * APP绑定关系是否存在
     */
    public static final String IS_BIND_RELATION = SERVER_URL + "api/front/user/isBindRelation";

    /**
     * APP绑定微信
     */
    public static final String WEIXIN_BIND = SERVER_URL + "api/front/user/weixinBind";

    /**
     * APP解除微信绑定
     */
    public static final String WEIXIN_BIND_REMOVE = SERVER_URL + "api/front/user/weixinBindRemove";

    /**
     * 修改用户信息
     */
    public static final String MODIFY_USER_INFO = SERVER_URL + "api/front/user/modifyUserInfo";

    /**
     * 查询用户信息
     */
    public static final String GET_USER_INFO = SERVER_URL + "api/front/user/getUserInfo";

    /**
     * 充值模版
     */
    public static final String GET_RECHARGE_SET = SERVER_URL + "api/front/pay/getRechargeSet";

    /**
     * 创建商家申请接口
     */
    public static final String SAVE_BUSI_APPLY = SERVER_URL + "api/front/user/saveBusiApply";

    /**
     * 查询用户商家申请记录
     */
    public static final String GET_BUSINESS_APPLY = SERVER_URL + "api/front/user/getBusinessApply";

    /**
     * 查询用户积分余额
     */
    public static final String GET_SCORE_BALANCE = SERVER_URL + "api/front/user/getScoreBalance";

    /**
     * 修改支付密码
     */
    public static final String MODIFY_PAY_PWD = SERVER_URL + "api/front/user/safety/modifyPayPwd";

    /**
     * 修改登录密码
     */
    public static final String MODIFY_LOGIN_PWD = SERVER_URL + "api/front/user/safety/modifyLoginPwd";

    /**
     * 更换手机号码
     */
    public static final String REFRESH_PHONE = SERVER_URL + "api/front/user/safety/refreshPhone";

    /**
     * 登录、注册
     */
    public static final String USER_LOGIN = SERVER_URL + "api/front/user/userLogin";

    /**
     * 更新消息状态（是否已查看）
     */
    public static final String CHANGE_MSG_STATUS = SERVER_URL + "api/front/sys/changeMsgStatus";

    /**
     * 获取最新的未读消息
     */
    public static final String GET_NEW_INFORMATION = SERVER_URL + "api/front/sys/getNewInformation";

    /**
     * 获取最新系统消息列表（页面查询）
     */
    public static final String GET_SYSTEM_MESSAGE = SERVER_URL + "api/front/sys/getSystemMessage";

    /**
     * 绑定手机接口
     */
    public static final String BIND_PHONE = SERVER_URL + "api/front/user/bindPhone";

    /**
     * 退出登录
     */
    public static final String USER_LOGIN_OUT = SERVER_URL + "api/front/user/userLoginOut";

    /**
     * 我的邀请
     */
    public static final String USER_INVITING_FRIENDS = SERVER_URL + "api/front/user/invitingFriendsCount";

    /**
     * 我的奖励
     */
    public static final String USER_MY_REWARD_COUNT = SERVER_URL + "api/front/user/myRewardCount";

    /**
     * 我的伙伴
     */
    public static final String USER_MY_PARTNER_COUNT = SERVER_URL + "api/front/user/myPartnerCount";

    /**
     * 获取用户佣金记录
     */
    public static final String GET_COMMISSION = SERVER_URL + "api/front/user/getCommission";

    /**
     * 获取用户代理数据
     */
    public static final String GET_AGENTDATA = SERVER_URL + "api/front/user/getAgentData";

    /******************************订单******************************/

    /**
     * 创建订单
     */
    public static final String CREATE_ORDER = SERVER_URL + "api/front/order/createOrder";

    /**
     * 创建订单评论
     */
    public static final String CREATE_ORDER_COMMENT = SERVER_URL + "api/front/order/createOrderComment";

    /**
     * 取消订单
     */
    public static final String CANCEL_ORDER = SERVER_URL + "api/front/order/cancelOrder";

    /**
     * 查询当前用户的订单数量
     */
    public static final String COUNT_ORDER_NUM = SERVER_URL + "api/front/order/countOrderNum";

    /**
     * 查询订单列表（按条件查询）
     */
    public static final String GET_ORDER_LIST = SERVER_URL + "api/front/order/getOrderList";

    /**
     * 订单付款
     */
    public static final String ORDER_PAY = SERVER_URL + "api/front/order/orderPay";

    /**
     * 获取订单支付信息
     */
    public static final String GEI_ORDER_PAY_DETAIL = SERVER_URL + "api/front/order/geiOrderPayDetail";

    /**
     * 订单详情
     */
    public static final String GET_ORDER_DETAIL = SERVER_URL + "api/front/order/getOrderDetail";

    /**
     * 订单退款
     */
    public static final String ORDER_REFUND = SERVER_URL + "api/front/order/orderRefund";

    /**
     * 积分充值
     */
    public static final String USER_SCORE_RECHARGE = SERVER_URL + "api/front/pay/pay";

    /**
     * 头像上传
     */
    public static final String IMAGE_UPLOAD = SERVER_URL + "api/file/upload";

    /**
     * 查询首页广告数据
     */
    public static final String GET_HOME_PAGE_ADV_DATAS = SERVER_URL + "api/front/app/getAppHomePageData";

    /**
     * 查询首页banner图
     */
    public static final String GET_HOME_PAGE_BANNERS = SERVER_URL + "api/front/ad/getAdShowList";

    /**
     * 查询首页第三方商城广告位
     */
    public static final String GET_HOME_PAGE_THIRDPLATFORM_ADV = SERVER_URL + "api/front/ad/getOtherApp";

    /******************************区域******************************/

    /**
     * 查询省
     */
    public static final String GET_PROVINCE_LIST = SERVER_URL + "api/front/area/getProvinceList";

    /**
     * 查询市区
     */
    public static final String GET_PARENT_CODE_LIST = SERVER_URL + "api/front/area/getParentCodeList";

    /******************************区域End******************************/

    /**
     * 扫码付款
     */
    public static final String SCAN_QRCODE_PAY = SERVER_URL + "api/front/pay/payByQRCodeInfo";

    /**
     * 系统配置接口
     */
    public static final String GET_CONFIG_LIST = SERVER_URL + "api/front/config/getConfigList";
    /**
     * 美业快报
     */
    public static final String GET_ARTICLE_LIST = SERVER_URL + "api/front/article/getArticleList";
    /**
     * 首页分类
     */
    public static final String GET_ALL_SORT = SERVER_URL + "api/front/business/getAllSort";
}
