package com.weishi.yiye.bean;

import java.io.Serializable;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/5
 * @Description：区域选择实体类
 * @Version:v1.0.0
 *****************************/
public class SelectShopTypeBean implements Serializable {

    private int shopTypeFirstParentId;
    private String shopTypeFirstParentName;
    private int shopTypeSecondParentId;
    private String shopTypeSecondParentName;

    public int getShopTypeFirstParentId() {
        return shopTypeFirstParentId;
    }

    public void setShopTypeFirstParentId(int shopTypeFirstParentId) {
        this.shopTypeFirstParentId = shopTypeFirstParentId;
    }

    public String getShopTypeFirstParentName() {
        return shopTypeFirstParentName;
    }

    public void setShopTypeFirstParentName(String shopTypeFirstParentName) {
        this.shopTypeFirstParentName = shopTypeFirstParentName;
    }

    public int getShopTypeSecondParentId() {
        return shopTypeSecondParentId;
    }

    public void setShopTypeSecondParentId(int shopTypeSecondParentId) {
        this.shopTypeSecondParentId = shopTypeSecondParentId;
    }

    public String getShopTypeSecondParentName() {
        return shopTypeSecondParentName;
    }

    public void setShopTypeSecondParentName(String shopTypeSecondParentName) {
        this.shopTypeSecondParentName = shopTypeSecondParentName;
    }
}
