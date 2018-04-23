package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/18
 * @Description：系统消息bean
 * @Version:v1.0.0
 *****************************/

public class MsgBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":10,"size":10,"orderBy":null,"startRow":1,"endRow":10,"total":11,"pages":2,"list":[{"id":4,"msgType":1,"userId":null,"msgContent":"防守打法33","isread":0,"sendTime":1517408123000},{"id":1,"msgType":1,"userId":null,"msgContent":"开会了","isread":0,"sendTime":1516982364000},{"id":2,"msgType":1,"userId":null,"msgContent":"下班了","isread":0,"sendTime":1516895993000},{"id":11,"msgType":1,"userId":null,"msgContent":"GDGGFD","isread":0,"sendTime":1516889876000},{"id":8,"msgType":1,"userId":null,"msgContent":"防守打法","isread":0,"sendTime":1516889816000},{"id":7,"msgType":1,"userId":null,"msgContent":"分手大师","isread":0,"sendTime":1516889795000},{"id":6,"msgType":1,"userId":null,"msgContent":"发顺丰","isread":0,"sendTime":1516889776000},{"id":9,"msgType":1,"userId":null,"msgContent":"SD","isread":0,"sendTime":1516803435000},{"id":5,"msgType":1,"userId":null,"msgContent":"方法","isread":0,"sendTime":1516803351000},{"id":10,"msgType":1,"userId":null,"msgContent":"GDFG","isread":0,"sendTime":1516717055000}],"firstPage":1,"prePage":0,"nextPage":2,"lastPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2]}
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
         * size : 10
         * orderBy : null
         * startRow : 1
         * endRow : 10
         * total : 11
         * pages : 2
         * list : [{"id":4,"msgType":1,"userId":null,"msgContent":"防守打法33","isread":0,"sendTime":1517408123000},{"id":1,"msgType":1,"userId":null,"msgContent":"开会了","isread":0,"sendTime":1516982364000},{"id":2,"msgType":1,"userId":null,"msgContent":"下班了","isread":0,"sendTime":1516895993000},{"id":11,"msgType":1,"userId":null,"msgContent":"GDGGFD","isread":0,"sendTime":1516889876000},{"id":8,"msgType":1,"userId":null,"msgContent":"防守打法","isread":0,"sendTime":1516889816000},{"id":7,"msgType":1,"userId":null,"msgContent":"分手大师","isread":0,"sendTime":1516889795000},{"id":6,"msgType":1,"userId":null,"msgContent":"发顺丰","isread":0,"sendTime":1516889776000},{"id":9,"msgType":1,"userId":null,"msgContent":"SD","isread":0,"sendTime":1516803435000},{"id":5,"msgType":1,"userId":null,"msgContent":"方法","isread":0,"sendTime":1516803351000},{"id":10,"msgType":1,"userId":null,"msgContent":"GDFG","isread":0,"sendTime":1516717055000}]
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
             * id : 4
             * msgType : 1
             * userId : null
             * msgContent : 防守打法33
             * isread : 0
             * sendTime : 1517408123000
             */

            private int id;
            private int msgType;
            private Object userId;
            private String msgContent;
            private int isread;
            private long sendTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMsgType() {
                return msgType;
            }

            public void setMsgType(int msgType) {
                this.msgType = msgType;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public String getMsgContent() {
                return msgContent;
            }

            public void setMsgContent(String msgContent) {
                this.msgContent = msgContent;
            }

            public int getIsread() {
                return isread;
            }

            public void setIsread(int isread) {
                this.isread = isread;
            }

            public long getSendTime() {
                return sendTime;
            }

            public void setSendTime(long sendTime) {
                this.sendTime = sendTime;
            }
        }
    }
}
