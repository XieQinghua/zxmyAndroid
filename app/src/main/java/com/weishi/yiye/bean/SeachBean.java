package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/3
 * @Description：搜索返回
 * @Version:v1.0.0
 *****************************/
public class SeachBean {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":5,"size":4,"orderBy":null,"startRow":1,"endRow":4,"total":4,"pages":1,"list":[{"storeId":null,"id":13,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145208","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518284642000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518284642000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":20,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145209","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296026000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296026000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":21,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145200","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296224000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296224000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":22,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145201","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296291000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296291000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
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
         * pageSize : 5
         * size : 4
         * orderBy : null
         * startRow : 1
         * endRow : 4
         * total : 4
         * pages : 1
         * list : [{"storeId":null,"id":13,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145208","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518284642000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518284642000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":20,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145209","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296026000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296026000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":21,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145200","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296224000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296224000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null},{"storeId":null,"id":22,"storeName":"大吉大利","storeOwner":23,"shopkeeperName":"今晚吃鸡","mobile":"13813145201","address":"麓谷公园","storeLogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518150627197&di=f62b13ed89d9f4e509d08bc7a2b82a2e&imgtype=0&src=http://down.52pk.com/uploads/170921/5029_115934_1_lit.png","introduce":"蓝洞出品","createtime":1518296291000,"status":1,"storeFatherType":2,"storeParentType":8,"phone":null,"lng":null,"lat":null,"isHot":0,"authingTime":1518296291000,"sellTotal":0,"starLevel":0,"keyword":null,"userLat":null,"userLng":null,"orderBy":null,"busiType":null,"pageNum":null,"pageSize":null,"distance":null,"productList":[],"searchDistance":null,"imgOssUrl":null,"commentLv":null}]
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
        private List<QueryShopBean> list;
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

        public List<QueryShopBean> getList() {
            return list;
        }

        public void setList(List<QueryShopBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }
    }
}
