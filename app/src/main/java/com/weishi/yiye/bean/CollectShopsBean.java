package com.weishi.yiye.bean;

import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.ValidatorUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/10
 * @Description：收藏店铺实体类
 * @Version:v1.0.0
 *****************************/

public class CollectShopsBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":10,"size":3,"orderBy":null,"startRow":1,"endRow":3,"total":3,"pages":1,"list":[{"storeId":null,"id":20,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145209","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296026000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296026000,"sellTotal":null,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":null,"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":13,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145208","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518284642000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518284642000,"sellTotal":null,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":null,"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":12,"storeName":"琪雅","storeOwner":1,"shopkeeperName":"ll","mobile":"17655223311","address":"岳麓山角118号","storeLogo":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/qy.jpeg","introduce":"自家鸡鸭鱼，绿色健康","createtime":1517060397000,"status":1,"storeFatherType":4,"storeParentType":10,"phone":"110","lng":212.89325,"lat":128.214069,"isHot":1,"authingTime":1517060477000,"sellTotal":null,"starLevel":2,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":null,"searchDistance":null,"imgOssUrl":null,"commentLv":null}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
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
         * pageNum : 1
         * pageSize : 10
         * size : 3
         * orderBy : null
         * startRow : 1
         * endRow : 3
         * total : 3
         * pages : 1
         * list : [{"storeId":null,"id":20,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145209","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296026000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296026000,"sellTotal":null,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":null,"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":13,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145208","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518284642000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518284642000,"sellTotal":null,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":null,"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":12,"storeName":"琪雅","storeOwner":1,"shopkeeperName":"ll","mobile":"17655223311","address":"岳麓山角118号","storeLogo":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/qy.jpeg","introduce":"自家鸡鸭鱼，绿色健康","createtime":1517060397000,"status":1,"storeFatherType":4,"storeParentType":10,"phone":"110","lng":212.89325,"lat":128.214069,"isHot":1,"authingTime":1517060477000,"sellTotal":null,"starLevel":2,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":null,"searchDistance":null,"imgOssUrl":null,"commentLv":null}]
         * firstPage : 1
         * prePage : 0
         * nextPage : 0
         * lastPage : 1
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         */

        private int pageNum;
        private int pageSize;
        private int size;
        private Object orderBy;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;
        private int firstPage;
        private int prePage;
        private int nextPage;
        private int lastPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * storeId : null
             * id : 20
             * storeName : 大吉大利
             * storeOwner : 23
             * shopkeeperName : 今晚吃鸡
             * mobile : 13813145209
             * address : 麓谷公园
             * storeLogo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png
             * introduce : 蓝洞出品
             * createtime : 1518296026000
             * status : 1
             * storeFatherType : 2
             * storeParentType : 8
             * phone : null
             * lng : null
             * lat : null
             * isHot : 0
             * authingTime : 1518296026000
             * sellTotal : null
             * starLevel : 0.0
             * keyword : null
             * userLat : null
             * userLng : null
             * orderBy : null
             * busiType : null
             * pageNum : null
             * pageSize : null
             * distance : null
             * productList : null
             * searchDistance : null
             * imgOssUrl : null
             * commentLv : null
             */

            private Object storeId;
            private int id;
            private String storeName;
            private int storeOwner;
            private String shopkeeperName;
            private String mobile;
            private String address;
            private String storeLogo;
            private String introduce;
            private long createtime;
            private int status;
            private int storeFatherType;
            private int storeParentType;
            private Object phone;
            private Object lng;
            private Object lat;
            private int isHot;
            private long authingTime;
            private Object sellTotal;
            private double starLevel;
            private Object keyword;
            private Object userLat;
            private Object userLng;
            private Object orderBy;
            private Object busiType;
            private Object pageNum;
            private Object pageSize;
            private String distance;
            private Object productList;
            private Object searchDistance;
            private Object imgOssUrl;
            private Object commentLv;

            public Object getStoreId() {
                return storeId;
            }

            public void setStoreId(Object storeId) {
                this.storeId = storeId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getStoreOwner() {
                return storeOwner;
            }

            public void setStoreOwner(int storeOwner) {
                this.storeOwner = storeOwner;
            }

            public String getShopkeeperName() {
                return shopkeeperName;
            }

            public void setShopkeeperName(String shopkeeperName) {
                this.shopkeeperName = shopkeeperName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getStoreLogo() {
                return storeLogo;
            }

            public void setStoreLogo(String storeLogo) {
                this.storeLogo = storeLogo;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStoreFatherType() {
                return storeFatherType;
            }

            public void setStoreFatherType(int storeFatherType) {
                this.storeFatherType = storeFatherType;
            }

            public int getStoreParentType() {
                return storeParentType;
            }

            public void setStoreParentType(int storeParentType) {
                this.storeParentType = storeParentType;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getLng() {
                return lng;
            }

            public void setLng(Object lng) {
                this.lng = lng;
            }

            public Object getLat() {
                return lat;
            }

            public void setLat(Object lat) {
                this.lat = lat;
            }

            public int getIsHot() {
                return isHot;
            }

            public void setIsHot(int isHot) {
                this.isHot = isHot;
            }

            public long getAuthingTime() {
                return authingTime;
            }

            public void setAuthingTime(long authingTime) {
                this.authingTime = authingTime;
            }

            public Object getSellTotal() {
                return sellTotal;
            }

            public void setSellTotal(Object sellTotal) {
                this.sellTotal = sellTotal;
            }

            public double getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(double starLevel) {
                this.starLevel = starLevel;
            }

            public Object getKeyword() {
                return keyword;
            }

            public void setKeyword(Object keyword) {
                this.keyword = keyword;
            }

            public Object getUserLat() {
                return userLat;
            }

            public void setUserLat(Object userLat) {
                this.userLat = userLat;
            }

            public Object getUserLng() {
                return userLng;
            }

            public void setUserLng(Object userLng) {
                this.userLng = userLng;
            }

            public Object getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(Object orderBy) {
                this.orderBy = orderBy;
            }

            public Object getBusiType() {
                return busiType;
            }

            public void setBusiType(Object busiType) {
                this.busiType = busiType;
            }

            public Object getPageNum() {
                return pageNum;
            }

            public void setPageNum(Object pageNum) {
                this.pageNum = pageNum;
            }

            public Object getPageSize() {
                return pageSize;
            }

            public void setPageSize(Object pageSize) {
                this.pageSize = pageSize;
            }

            public String getDistance() {
                return ValidatorUtils.isEmptyString(distance) ?
                        StringConstants.STR_ZERO : new DecimalFormat("#0.00").format(Integer.valueOf(distance) / 1000) + "km";
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public Object getProductList() {
                return productList;
            }

            public void setProductList(Object productList) {
                this.productList = productList;
            }

            public Object getSearchDistance() {
                return searchDistance;
            }

            public void setSearchDistance(Object searchDistance) {
                this.searchDistance = searchDistance;
            }

            public Object getImgOssUrl() {
                return imgOssUrl;
            }

            public void setImgOssUrl(Object imgOssUrl) {
                this.imgOssUrl = imgOssUrl;
            }

            public Object getCommentLv() {
                return commentLv;
            }

            public void setCommentLv(Object commentLv) {
                this.commentLv = commentLv;
            }
        }
    }
}
