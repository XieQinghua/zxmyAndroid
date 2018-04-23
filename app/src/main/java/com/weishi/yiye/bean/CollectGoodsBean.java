package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/10
 * @Description：收藏商品实体类
 * @Version:v1.0.0
 *****************************/

public class CollectGoodsBean implements Serializable {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":4,"size":4,"orderBy":null,"startRow":0,"endRow":3,"total":4,"pages":1,"list":[{"id":83,"productNum":"2","busiFatherType":2,"busiFatherName":null,"busiParentType":4,"busiParentName":null,"price":108,"showImg":"https://img.alicdn.com/imgextra/i1/749391658/TB2vZHuXMMEF1JjSZFxXXbcVpXa_!!749391658.jpg_430x430q90.jpg","productName":"面部护理套装","weight":66,"isReward":1,"rewardPoint":1,"rewardPercent":1,"description":"   <p class=\"zzc mg2\">单人自助餐<span>1位<\/span><\/p>","productDetail":"测试商品2","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":1000,"createtime":1517686286000,"storeId":12,"sendType":1,"status":1,"isHotsale":1,"isHot":1,"validTime":1517686303000,"modifyTime":1517686306000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null},{"id":82,"productNum":"1","busiFatherType":1,"busiFatherName":null,"busiParentType":5,"busiParentName":null,"price":888,"showImg":"https://img.alicdn.com/bao/uploaded/i1/749391658/TB1IWqCe8fM8KJjSZFOXXXr5XXa_!!0-item_pic.jpg_430x430q90.jpg","productName":"护肤面部套装 多效修护3件套","weight":32,"isReward":23,"rewardPoint":0,"rewardPercent":23,"description":"jjjjjkjjkj \\n ooooooo","productDetail":"32","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":10000,"createtime":1516478256000,"storeId":12,"sendType":1,"status":1,"isHotsale":21,"isHot":1,"validTime":1516823872000,"modifyTime":1516996669000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null},{"id":51,"productNum":"1","busiFatherType":2,"busiFatherName":null,"busiParentType":4,"busiParentName":null,"price":88,"showImg":"https://img.alicdn.com/bao/uploaded/i4/TB1u7eFmwMPMeJjy1XbIA.wxVXa_044108.jpg_430x430q90.jpg","productName":"广济堂 凯蒂仙牌减肥胶囊","weight":66,"isReward":1,"rewardPoint":1,"rewardPercent":1,"description":"   <p class=\"zzc mg2\">单人自助餐<span>1位<\/span><\/p>","productDetail":"媲美测试商品1","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":999,"createtime":1517686159000,"storeId":5,"sendType":1,"status":1,"isHotsale":1,"isHot":1,"validTime":1517686193000,"modifyTime":1517686196000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null},{"id":50,"productNum":"1","busiFatherType":1,"busiFatherName":null,"busiParentType":5,"busiParentName":null,"price":888,"showImg":"https://img.alicdn.com/bao/uploaded/i3/TB1tKiHLpXXXXcWXpXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg","productName":"碧生源牌常菁茶","weight":32,"isReward":23,"rewardPoint":0,"rewardPercent":23,"description":"jjjjjkjjkj \\n ooooooo","productDetail":"32","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":10000,"createtime":1516478256000,"storeId":5,"sendType":1,"status":1,"isHotsale":21,"isHot":1,"validTime":1516823872000,"modifyTime":1516996669000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
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
         * pageSize : 4
         * size : 4
         * orderBy : null
         * startRow : 0
         * endRow : 3
         * total : 4
         * pages : 1
         * list : [{"id":83,"productNum":"2","busiFatherType":2,"busiFatherName":null,"busiParentType":4,"busiParentName":null,"price":108,"showImg":"https://img.alicdn.com/imgextra/i1/749391658/TB2vZHuXMMEF1JjSZFxXXbcVpXa_!!749391658.jpg_430x430q90.jpg","productName":"面部护理套装","weight":66,"isReward":1,"rewardPoint":1,"rewardPercent":1,"description":"   <p class=\"zzc mg2\">单人自助餐<span>1位<\/span><\/p>","productDetail":"测试商品2","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":1000,"createtime":1517686286000,"storeId":12,"sendType":1,"status":1,"isHotsale":1,"isHot":1,"validTime":1517686303000,"modifyTime":1517686306000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null},{"id":82,"productNum":"1","busiFatherType":1,"busiFatherName":null,"busiParentType":5,"busiParentName":null,"price":888,"showImg":"https://img.alicdn.com/bao/uploaded/i1/749391658/TB1IWqCe8fM8KJjSZFOXXXr5XXa_!!0-item_pic.jpg_430x430q90.jpg","productName":"护肤面部套装 多效修护3件套","weight":32,"isReward":23,"rewardPoint":0,"rewardPercent":23,"description":"jjjjjkjjkj \\n ooooooo","productDetail":"32","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":10000,"createtime":1516478256000,"storeId":12,"sendType":1,"status":1,"isHotsale":21,"isHot":1,"validTime":1516823872000,"modifyTime":1516996669000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null},{"id":51,"productNum":"1","busiFatherType":2,"busiFatherName":null,"busiParentType":4,"busiParentName":null,"price":88,"showImg":"https://img.alicdn.com/bao/uploaded/i4/TB1u7eFmwMPMeJjy1XbIA.wxVXa_044108.jpg_430x430q90.jpg","productName":"广济堂 凯蒂仙牌减肥胶囊","weight":66,"isReward":1,"rewardPoint":1,"rewardPercent":1,"description":"   <p class=\"zzc mg2\">单人自助餐<span>1位<\/span><\/p>","productDetail":"媲美测试商品1","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":999,"createtime":1517686159000,"storeId":5,"sendType":1,"status":1,"isHotsale":1,"isHot":1,"validTime":1517686193000,"modifyTime":1517686196000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null},{"id":50,"productNum":"1","busiFatherType":1,"busiFatherName":null,"busiParentType":5,"busiParentName":null,"price":888,"showImg":"https://img.alicdn.com/bao/uploaded/i3/TB1tKiHLpXXXXcWXpXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg","productName":"碧生源牌常菁茶","weight":32,"isReward":23,"rewardPoint":0,"rewardPercent":23,"description":"jjjjjkjjkj \\n ooooooo","productDetail":"32","detailImg":"http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg","detailImgList":null,"sellNum":10000,"createtime":1516478256000,"storeId":5,"sendType":1,"status":1,"isHotsale":21,"isHot":1,"validTime":1516823872000,"modifyTime":1516996669000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"avgCommentLv":null}]
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
             * id : 83
             * productNum : 2
             * busiFatherType : 2
             * busiFatherName : null
             * busiParentType : 4
             * busiParentName : null
             * price : 108
             * showImg : https://img.alicdn.com/imgextra/i1/749391658/TB2vZHuXMMEF1JjSZFxXXbcVpXa_!!749391658.jpg_430x430q90.jpg
             * productName : 面部护理套装
             * weight : 66
             * isReward : 1
             * rewardPoint : 1
             * rewardPercent : 1.0
             * description :    <p class="zzc mg2">单人自助餐<span>1位</span></p>
             * productDetail : 测试商品2
             * detailImg : http://img.zcool.cn/community/01a64b57313d250000002bf0e667c9.jpg
             * detailImgList : null
             * sellNum : 1000
             * createtime : 1517686286000
             * storeId : 12
             * sendType : 1
             * status : 1
             * isHotsale : 1
             * isHot : 1
             * validTime : 1517686303000
             * modifyTime : 1517686306000
             * commentLv : null
             * startTime : null
             * endTime : null
             * mobile : null
             * storeName : null
             * telephone : null
             * address : null
             * distance : null
             * avgCommentLv : null
             */

            private int id;
            private String productNum;
            private int busiFatherType;
            private Object busiFatherName;
            private int busiParentType;
            private Object busiParentName;
            private double price;
            private String showImg;
            private String productName;
            private int weight;
            private int isReward;
            private int rewardPoint;
            private double rewardPercent;
            private String description;
            private String productDetail;
            private String detailImg;
            private Object detailImgList;
            private int sellNum;
            private long createtime;
            private int storeId;
            private int sendType;
            private int status;
            private int isHotsale;
            private int isHot;
            private long validTime;
            private long modifyTime;
            private Object commentLv;
            private Object startTime;
            private Object endTime;
            private Object mobile;
            private Object storeName;
            private Object telephone;
            private Object address;
            private double distance;
            private Object avgCommentLv;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProductNum() {
                return productNum;
            }

            public void setProductNum(String productNum) {
                this.productNum = productNum;
            }

            public int getBusiFatherType() {
                return busiFatherType;
            }

            public void setBusiFatherType(int busiFatherType) {
                this.busiFatherType = busiFatherType;
            }

            public Object getBusiFatherName() {
                return busiFatherName;
            }

            public void setBusiFatherName(Object busiFatherName) {
                this.busiFatherName = busiFatherName;
            }

            public int getBusiParentType() {
                return busiParentType;
            }

            public void setBusiParentType(int busiParentType) {
                this.busiParentType = busiParentType;
            }

            public Object getBusiParentName() {
                return busiParentName;
            }

            public void setBusiParentName(Object busiParentName) {
                this.busiParentName = busiParentName;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getShowImg() {
                return showImg;
            }

            public void setShowImg(String showImg) {
                this.showImg = showImg;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public int getIsReward() {
                return isReward;
            }

            public void setIsReward(int isReward) {
                this.isReward = isReward;
            }

            public int getRewardPoint() {
                return rewardPoint;
            }

            public void setRewardPoint(int rewardPoint) {
                this.rewardPoint = rewardPoint;
            }

            public double getRewardPercent() {
                return rewardPercent;
            }

            public void setRewardPercent(double rewardPercent) {
                this.rewardPercent = rewardPercent;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getProductDetail() {
                return productDetail;
            }

            public void setProductDetail(String productDetail) {
                this.productDetail = productDetail;
            }

            public String getDetailImg() {
                return detailImg;
            }

            public void setDetailImg(String detailImg) {
                this.detailImg = detailImg;
            }

            public Object getDetailImgList() {
                return detailImgList;
            }

            public void setDetailImgList(Object detailImgList) {
                this.detailImgList = detailImgList;
            }

            public int getSellNum() {
                return sellNum;
            }

            public void setSellNum(int sellNum) {
                this.sellNum = sellNum;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getSendType() {
                return sendType;
            }

            public void setSendType(int sendType) {
                this.sendType = sendType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIsHotsale() {
                return isHotsale;
            }

            public void setIsHotsale(int isHotsale) {
                this.isHotsale = isHotsale;
            }

            public int getIsHot() {
                return isHot;
            }

            public void setIsHot(int isHot) {
                this.isHot = isHot;
            }

            public long getValidTime() {
                return validTime;
            }

            public void setValidTime(long validTime) {
                this.validTime = validTime;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public Object getCommentLv() {
                return commentLv;
            }

            public void setCommentLv(Object commentLv) {
                this.commentLv = commentLv;
            }

            public Object getStartTime() {
                return startTime;
            }

            public void setStartTime(Object startTime) {
                this.startTime = startTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public Object getStoreName() {
                return storeName;
            }

            public void setStoreName(Object storeName) {
                this.storeName = storeName;
            }

            public Object getTelephone() {
                return telephone;
            }

            public void setTelephone(Object telephone) {
                this.telephone = telephone;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public Object getAvgCommentLv() {
                return avgCommentLv;
            }

            public void setAvgCommentLv(Object avgCommentLv) {
                this.avgCommentLv = avgCommentLv;
            }
        }
    }
}
