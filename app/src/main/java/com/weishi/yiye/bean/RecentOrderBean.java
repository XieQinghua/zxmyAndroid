package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/31
 * @Description：最近订单
 * @Version:v1.0.0
 *****************************/

public class RecentOrderBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":10,"size":2,"orderBy":null,"startRow":1,"endRow":2,"total":2,"pages":1,"list":[{"id":172,"orderNum":"18020619420001","orderPrice":888,"rewardPoint":null,"userId":273,"mobile":"15200917596","useStatus":0,"orderStatus":0,"commentStatus":0,"payMethod":1,"createtime":1517917359000,"useTime":null,"useCode":null,"payTime":null,"modifyTime":null,"cancleTime":1517960559000,"productId":82,"productName":"护肤面部套装 多效修护3件套","storeId":null,"tagType":null,"number":1,"validTime":1516823872000,"showImg":"https://img.alicdn.com/bao/uploaded/i1/749391658/TB1IWqCe8fM8KJjSZFOXXXr5XXa_!!0-item_pic.jpg_430x430q90.jpg","storeName":null,"address":null,"phone":null,"payPasswd":null,"pendPayNum":null,"pendUseNum":null,"pendCommentNum":null,"refundNum":null,"orderStatusStr":null,"token":null,"showStatus":1},{"id":171,"orderNum":"18020619410001","orderPrice":88,"rewardPoint":null,"userId":273,"mobile":"15200917596","useStatus":0,"orderStatus":0,"commentStatus":0,"payMethod":1,"createtime":1517917303000,"useTime":null,"useCode":null,"payTime":null,"modifyTime":null,"cancleTime":1517960503000,"productId":51,"productName":"广济堂 凯蒂仙牌减肥胶囊","storeId":null,"tagType":null,"number":1,"validTime":1517686193000,"showImg":"https://img.alicdn.com/bao/uploaded/i4/TB1u7eFmwMPMeJjy1XbIA.wxVXa_044108.jpg_430x430q90.jpg","storeName":null,"address":null,"phone":null,"payPasswd":null,"pendPayNum":null,"pendUseNum":null,"pendCommentNum":null,"refundNum":null,"orderStatusStr":null,"token":null,"showStatus":1}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
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

    public static class DataBean implements Serializable {
        /**
         * pageNum : 1
         * pageSize : 10
         * size : 2
         * orderBy : null
         * startRow : 1
         * endRow : 2
         * total : 2
         * pages : 1
         * list : [{"id":172,"orderNum":"18020619420001","orderPrice":888,"rewardPoint":null,"userId":273,"mobile":"15200917596","useStatus":0,"orderStatus":0,"commentStatus":0,"payMethod":1,"createtime":1517917359000,"useTime":null,"useCode":null,"payTime":null,"modifyTime":null,"cancleTime":1517960559000,"productId":82,"productName":"护肤面部套装 多效修护3件套","storeId":null,"tagType":null,"number":1,"validTime":1516823872000,"showImg":"https://img.alicdn.com/bao/uploaded/i1/749391658/TB1IWqCe8fM8KJjSZFOXXXr5XXa_!!0-item_pic.jpg_430x430q90.jpg","storeName":null,"address":null,"phone":null,"payPasswd":null,"pendPayNum":null,"pendUseNum":null,"pendCommentNum":null,"refundNum":null,"orderStatusStr":null,"token":null,"showStatus":1},{"id":171,"orderNum":"18020619410001","orderPrice":88,"rewardPoint":null,"userId":273,"mobile":"15200917596","useStatus":0,"orderStatus":0,"commentStatus":0,"payMethod":1,"createtime":1517917303000,"useTime":null,"useCode":null,"payTime":null,"modifyTime":null,"cancleTime":1517960503000,"productId":51,"productName":"广济堂 凯蒂仙牌减肥胶囊","storeId":null,"tagType":null,"number":1,"validTime":1517686193000,"showImg":"https://img.alicdn.com/bao/uploaded/i4/TB1u7eFmwMPMeJjy1XbIA.wxVXa_044108.jpg_430x430q90.jpg","storeName":null,"address":null,"phone":null,"payPasswd":null,"pendPayNum":null,"pendUseNum":null,"pendCommentNum":null,"refundNum":null,"orderStatusStr":null,"token":null,"showStatus":1}]
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
             * id : 172
             * orderNum : 18020619420001
             * orderPrice : 888
             * rewardPoint : null
             * userId : 273
             * mobile : 15200917596
             * useStatus : 0
             * orderStatus : 0
             * commentStatus : 0
             * payMethod : 1
             * createtime : 1517917359000
             * useTime : null
             * useCode : null
             * payTime : null
             * modifyTime : null
             * cancleTime : 1517960559000
             * productId : 82
             * productName : 护肤面部套装 多效修护3件套
             * storeId : null
             * tagType : null
             * number : 1
             * validTime : 1516823872000
             * showImg : https://img.alicdn.com/bao/uploaded/i1/749391658/TB1IWqCe8fM8KJjSZFOXXXr5XXa_!!0-item_pic.jpg_430x430q90.jpg
             * storeName : null
             * address : null
             * phone : null
             * payPasswd : null
             * pendPayNum : null
             * pendUseNum : null
             * pendCommentNum : null
             * refundNum : null
             * orderStatusStr : null
             * token : null
             * showStatus : 1
             */

            private int id;
            private String orderNum;
            private double orderPrice;
            private int rewardPoint;
            private int userId;
            private String mobile;
            private int useStatus;
            private int orderStatus;
            private int commentStatus;
            private int payMethod;
            private long createtime;
            private Object useTime;
            private Object useCode;
            private Object payTime;
            private Object modifyTime;
            private long cancleTime;
            private int productId;
            private String productName;
            private int storeId;
            private Object tagType;
            private int number;
            private long validTime;
            private String showImg;
            private Object storeName;
            private Object address;
            private Object phone;
            private Object payPasswd;
            private Object pendPayNum;
            private Object pendUseNum;
            private Object pendCommentNum;
            private Object refundNum;
            private Object orderStatusStr;
            private Object token;
            private Integer showStatus;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public double getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(double orderPrice) {
                this.orderPrice = orderPrice;
            }

            public int getRewardPoint() {
                return rewardPoint;
            }

            public void setRewardPoint(int rewardPoint) {
                this.rewardPoint = rewardPoint;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getUseStatus() {
                return useStatus;
            }

            public void setUseStatus(int useStatus) {
                this.useStatus = useStatus;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getCommentStatus() {
                return commentStatus;
            }

            public void setCommentStatus(int commentStatus) {
                this.commentStatus = commentStatus;
            }

            public int getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(int payMethod) {
                this.payMethod = payMethod;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public Object getUseTime() {
                return useTime;
            }

            public void setUseTime(Object useTime) {
                this.useTime = useTime;
            }

            public Object getUseCode() {
                return useCode;
            }

            public void setUseCode(Object useCode) {
                this.useCode = useCode;
            }

            public Object getPayTime() {
                return payTime;
            }

            public void setPayTime(Object payTime) {
                this.payTime = payTime;
            }

            public Object getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(Object modifyTime) {
                this.modifyTime = modifyTime;
            }

            public long getCancleTime() {
                return cancleTime;
            }

            public void setCancleTime(long cancleTime) {
                this.cancleTime = cancleTime;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public Object getTagType() {
                return tagType;
            }

            public void setTagType(Object tagType) {
                this.tagType = tagType;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public long getValidTime() {
                return validTime;
            }

            public void setValidTime(long validTime) {
                this.validTime = validTime;
            }

            public String getShowImg() {
                return showImg;
            }

            public void setShowImg(String showImg) {
                this.showImg = showImg;
            }

            public Object getStoreName() {
                return storeName;
            }

            public void setStoreName(Object storeName) {
                this.storeName = storeName;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getPayPasswd() {
                return payPasswd;
            }

            public void setPayPasswd(Object payPasswd) {
                this.payPasswd = payPasswd;
            }

            public Object getPendPayNum() {
                return pendPayNum;
            }

            public void setPendPayNum(Object pendPayNum) {
                this.pendPayNum = pendPayNum;
            }

            public Object getPendUseNum() {
                return pendUseNum;
            }

            public void setPendUseNum(Object pendUseNum) {
                this.pendUseNum = pendUseNum;
            }

            public Object getPendCommentNum() {
                return pendCommentNum;
            }

            public void setPendCommentNum(Object pendCommentNum) {
                this.pendCommentNum = pendCommentNum;
            }

            public Object getRefundNum() {
                return refundNum;
            }

            public void setRefundNum(Object refundNum) {
                this.refundNum = refundNum;
            }

            public Object getOrderStatusStr() {
                return orderStatusStr;
            }

            public void setOrderStatusStr(Object orderStatusStr) {
                this.orderStatusStr = orderStatusStr;
            }

            public Object getToken() {
                return token;
            }

            public void setToken(Object token) {
                this.token = token;
            }

            public Integer getShowStatus() {
                return showStatus;
            }

            public void setShowStatus(Integer showStatus) {
                this.showStatus = showStatus;
            }
        }
    }
}
