package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/30
 * @Description：商家分类对应的实体类
 * @Version:v1.0.0
 *****************************/

public class ShopTypeBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : null
     */

    private String code;
    private String message;
    private List<ShopType> data;

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

    public List<ShopType> getData() {
        return data;
    }

    public void setData(List<ShopType> data) {
        this.data = data;
    }

    public class ShopType {
        private int id;
        private String typeName;
        private int parentId;
        private String createtime;
        private int status;
        private String icon;

        private String fatherTypeName;
        private String lineColors;
        private String ombre;
        private int sortId;
        private int weight;
        private String property;

        public String getFatherTypeName() {
            return fatherTypeName;
        }

        public void setFatherTypeName(String fatherTypeName) {
            this.fatherTypeName = fatherTypeName;
        }

        public String getLineColors() {
            return lineColors;
        }

        public void setLineColors(String lineColors) {
            this.lineColors = lineColors;
        }

        public String getOmbre() {
            return ombre;
        }

        public void setOmbre(String ombre) {
            this.ombre = ombre;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
