package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/3
 * @Description：评论信息bean
 * @Version:v1.0.0
 *****************************/

public class CommentBean {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":3,"size":3,"orderBy":null,"startRow":1,"endRow":3,"total":4,"pages":2,"list":[{"id":56,"productId":105,"storeId":37,"userId":231,"orderId":383,"content":"","commentImg":"","commentImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/1ade69a354954c4298581f960caa06bd"],"commentLv":5,"isShow":null,"status":null,"createtime":1519894007000,"modifyTime":null,"modifyUser":null,"nickName":null,"countNum":null,"avatar":null},{"id":58,"productId":105,"storeId":37,"userId":23116,"orderId":384,"content":"，麓","commentImg":"","commentImgList":null,"commentLv":5,"isShow":null,"status":null,"createtime":1519902943000,"modifyTime":null,"modifyUser":null,"nickName":null,"countNum":null,"avatar":null},{"id":59,"productId":105,"storeId":37,"userId":23116,"orderId":394,"content":"就","commentImg":"","commentImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/dcde8aca12074baebe98f90e757aa77a","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/8e8a85277b9b493faddfca2978bb15e5","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/dbafca2a51794b43a820811b67e1ae90","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/be12111ccbdb40bb9f109ffbf5e38a9d","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/9d0fa76fb0394fc38b93af6b7fd130e5","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/994c3d3e43f14c80be1b6527a83a6bca","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/4ee4ca6f6ac149f397650f95c1679a67","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/7f7bf0cc335b428bb2e86b51797cb643"],"commentLv":5,"isShow":null,"status":null,"createtime":1519908602000,"modifyTime":null,"modifyUser":null,"nickName":null,"countNum":null,"avatar":null}],"firstPage":1,"prePage":0,"nextPage":2,"lastPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2]}
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
         * pageSize : 3
         * size : 3
         * orderBy : null
         * startRow : 1
         * endRow : 3
         * total : 4
         * pages : 2
         * list : [{"id":56,"productId":105,"storeId":37,"userId":231,"orderId":383,"content":"","commentImg":"","commentImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/1ade69a354954c4298581f960caa06bd"],"commentLv":5,"isShow":null,"status":null,"createtime":1519894007000,"modifyTime":null,"modifyUser":null,"nickName":null,"countNum":null,"avatar":null},{"id":58,"productId":105,"storeId":37,"userId":23116,"orderId":384,"content":"，麓","commentImg":"","commentImgList":null,"commentLv":5,"isShow":null,"status":null,"createtime":1519902943000,"modifyTime":null,"modifyUser":null,"nickName":null,"countNum":null,"avatar":null},{"id":59,"productId":105,"storeId":37,"userId":23116,"orderId":394,"content":"就","commentImg":"","commentImgList":["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/dcde8aca12074baebe98f90e757aa77a","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/8e8a85277b9b493faddfca2978bb15e5","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/dbafca2a51794b43a820811b67e1ae90","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/be12111ccbdb40bb9f109ffbf5e38a9d","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/9d0fa76fb0394fc38b93af6b7fd130e5","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/994c3d3e43f14c80be1b6527a83a6bca","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/4ee4ca6f6ac149f397650f95c1679a67","http://jiujie-pics.oss-cn-beijing.aliyuncs.com/7f7bf0cc335b428bb2e86b51797cb643"],"commentLv":5,"isShow":null,"status":null,"createtime":1519908602000,"modifyTime":null,"modifyUser":null,"nickName":null,"countNum":null,"avatar":null}]
         * firstPage : 1
         * prePage : 0
         * nextPage : 2
         * lastPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2]
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
             * id : 56
             * productId : 105
             * storeId : 37
             * userId : 231
             * orderId : 383
             * content :
             * commentImg :
             * commentImgList : ["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/1ade69a354954c4298581f960caa06bd"]
             * commentLv : 5
             * isShow : null
             * status : null
             * createtime : 1519894007000
             * modifyTime : null
             * modifyUser : null
             * nickName : null
             * countNum : null
             * avatar : null
             */

            private int id;
            private int productId;
            private int storeId;
            private int userId;
            private int orderId;
            private String content;
            private String commentImg;
            private int commentLv;
            private Object isShow;
            private Object status;
            private long createtime;
            private Object modifyTime;
            private Object modifyUser;
            private String nickName;
            private Object countNum;
            private String avatar;
            private List<String> commentImgList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCommentImg() {
                return commentImg;
            }

            public void setCommentImg(String commentImg) {
                this.commentImg = commentImg;
            }

            public int getCommentLv() {
                return commentLv;
            }

            public void setCommentLv(int commentLv) {
                this.commentLv = commentLv;
            }

            public Object getIsShow() {
                return isShow;
            }

            public void setIsShow(Object isShow) {
                this.isShow = isShow;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public Object getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(Object modifyTime) {
                this.modifyTime = modifyTime;
            }

            public Object getModifyUser() {
                return modifyUser;
            }

            public void setModifyUser(Object modifyUser) {
                this.modifyUser = modifyUser;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public Object getCountNum() {
                return countNum;
            }

            public void setCountNum(Object countNum) {
                this.countNum = countNum;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public List<String> getCommentImgList() {
                return commentImgList;
            }

            public void setCommentImgList(List<String> commentImgList) {
                this.commentImgList = commentImgList;
            }
        }
    }
}
