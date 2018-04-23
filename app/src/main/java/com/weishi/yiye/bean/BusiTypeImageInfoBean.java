package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/31
 * @Description：查询店铺相对的分类图片信息
 * @Version:v1.0.0
 *****************************/

public class BusiTypeImageInfoBean {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":2,"pageSize":2,"size":2,"orderBy":null,"startRow":3,"endRow":4,"total":15,"pages":8,"list":[{"id":19,"url":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/55c14f38-88bb-451f-9d88-3245bc40a8dd","createtime":1522327205000,"status":1,"photoClassifyId":3,"photoClassifyName":"test03","storeId":10,"isBackimage":0},{"id":18,"url":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/55c14f38-88bb-451f-9d88-3245bc40a8dd","createtime":1522327204000,"status":1,"photoClassifyId":3,"photoClassifyName":"test03","storeId":10,"isBackimage":0}],"firstPage":1,"prePage":1,"nextPage":3,"lastPage":8,"isFirstPage":false,"isLastPage":false,"hasPreviousPage":true,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8]}
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
         * pageNum : 2
         * pageSize : 2
         * size : 2
         * orderBy : null
         * startRow : 3
         * endRow : 4
         * total : 15
         * pages : 8
         * list : [{"id":19,"url":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/55c14f38-88bb-451f-9d88-3245bc40a8dd","createtime":1522327205000,"status":1,"photoClassifyId":3,"photoClassifyName":"test03","storeId":10,"isBackimage":0},{"id":18,"url":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/55c14f38-88bb-451f-9d88-3245bc40a8dd","createtime":1522327204000,"status":1,"photoClassifyId":3,"photoClassifyName":"test03","storeId":10,"isBackimage":0}]
         * firstPage : 1
         * prePage : 1
         * nextPage : 3
         * lastPage : 8
         * isFirstPage : false
         * isLastPage : false
         * hasPreviousPage : true
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7,8]
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
             * id : 19
             * url : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/55c14f38-88bb-451f-9d88-3245bc40a8dd
             * createtime : 1522327205000
             * status : 1
             * photoClassifyId : 3
             * photoClassifyName : test03
             * storeId : 10
             * isBackimage : 0
             */

            private int id;
            private String url;
            private long createtime;
            private int status;
            private int photoClassifyId;
            private String photoClassifyName;
            private int storeId;
            private int isBackimage;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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

            public int getPhotoClassifyId() {
                return photoClassifyId;
            }

            public void setPhotoClassifyId(int photoClassifyId) {
                this.photoClassifyId = photoClassifyId;
            }

            public String getPhotoClassifyName() {
                return photoClassifyName;
            }

            public void setPhotoClassifyName(String photoClassifyName) {
                this.photoClassifyName = photoClassifyName;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getIsBackimage() {
                return isBackimage;
            }

            public void setIsBackimage(int isBackimage) {
                this.isBackimage = isBackimage;
            }
        }
    }
}
