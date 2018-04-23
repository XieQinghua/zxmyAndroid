package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/2
 * @Description：店铺详情
 * @Version:v1.0.0
 *****************************/
public class ShopDetailBean implements Serializable {
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

    public class DataBean {
        private QueryShopBean storeInfo;
        private int isExist;
        private Integer id;
        private String businessName;//商家名字
        private String userName;//用户名字
        private List<QueryProductBean> productInfo;

        /********************美业新增字段********************/
        private String userType;
        private boolean canApplyMerchant;
        private int total;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public boolean isCanApplyMerchant() {
            return canApplyMerchant;
        }

        public void setCanApplyMerchant(boolean canApplyMerchant) {
            this.canApplyMerchant = canApplyMerchant;
        }

        /********************美业新增字段********************/

        public QueryShopBean getStoreInfo() {
            return storeInfo;
        }

        public void setStoreInfo(QueryShopBean storeInfo) {
            this.storeInfo = storeInfo;
        }

        public int getIsExist() {
            return isExist;
        }

        public void setIsExist(int isExist) {
            this.isExist = isExist;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<QueryProductBean> getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(List<QueryProductBean> productInfo) {
            this.productInfo = productInfo;
        }
        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
