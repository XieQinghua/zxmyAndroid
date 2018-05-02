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
    private int businessFatherType = -1;
    private String businessFatherTypeName = "";
    private int businessParentType = -1;
    private String businessParentTypeName = "";
    private int businessSortType = -1;
    private String businessSortTypeName = "";

    public int getBusinessFatherType() {
        return businessFatherType;
    }

    public void setBusinessFatherType(int businessFatherType) {
        this.businessFatherType = businessFatherType;
    }

    public String getBusinessFatherTypeName() {
        return businessFatherTypeName;
    }

    public void setBusinessFatherTypeName(String businessFatherTypeName) {
        this.businessFatherTypeName = businessFatherTypeName;
    }

    public int getBusinessParentType() {
        return businessParentType;
    }

    public void setBusinessParentType(int businessParentType) {
        this.businessParentType = businessParentType;
    }

    public String getBusinessParentTypeName() {
        return businessParentTypeName;
    }

    public void setBusinessParentTypeName(String businessParentTypeName) {
        this.businessParentTypeName = businessParentTypeName;
    }

    public int getBusinessSortType() {
        return businessSortType;
    }

    public void setBusinessSortType(int businessSortType) {
        this.businessSortType = businessSortType;
    }

    public String getBusinessSortTypeName() {
        return businessSortTypeName;
    }

    public void setBusinessSortTypeName(String businessSortTypeName) {
        this.businessSortTypeName = businessSortTypeName;
    }
}
