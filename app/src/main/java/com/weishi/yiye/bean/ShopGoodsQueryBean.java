package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/3
 * @Description：店铺商品实体类
 * @Version:v1.0.0
 *****************************/
public class ShopGoodsQueryBean implements Serializable {
    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":3,"size":3,"orderBy":null,"startRow":1,"endRow":3,"total":4,"pages":2,"list":[{"id":5,"productNum":"10","busiFatherType":3,"busiFatherName":null,"busiParentType":10,"busiParentName":null,"price":40,"showImg":"https://img.alicdn.com/bao/uploaded/i4/533497499/TB1axT0bf5TBuNjSspmXXaDRVXa_!!0-item_pic.jpg_430x430q90.jpg","alternateImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/7e675598-17ec-40df-a257-cd2e73413a96;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/a57cf063-c62d-44b1-9ccd-8c8107080a31;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/527c0d4c-4fca-4fe2-91a6-b88e23e8e79d;","alternateImgList":null,"productName":"复颜抗皱紧致化妆","weight":20,"isReward":1,"rewardPoint":30,"rewardPercent":0,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/a2fbd2f2-7376-4705-84db-f9958ff6f71f;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/818b1bb2-4edd-400f-99b1-5e434e7da8e0;","detailImgList":null,"sellNum":1,"createtime":1518326314000,"storeId":5,"sendType":2,"status":1,"isHotsale":0,"isHot":0,"validTime":1520035200000,"modifyTime":1519918391000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"starLevel":5,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":null,"validTimeStr":"2018-03-03"},{"id":51,"productNum":"10","busiFatherType":3,"busiFatherName":null,"busiParentType":10,"busiParentName":null,"price":63,"showImg":"img.alicdn.com/bao/uploaded/i2/2680721346/TB2I5T9beGSBuNjSspbXXciipXa_!!2680721346.jpg_430x430q90.jpg","alternateImg":null,"alternateImgList":null,"productName":"秋冬季补水保湿化妆","weight":20,"isReward":1,"rewardPoint":10,"rewardPercent":0,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","detailImgList":null,"sellNum":0,"createtime":1518268714000,"storeId":5,"sendType":2,"status":0,"isHotsale":0,"isHot":0,"validTime":1520006400000,"modifyTime":1518310335000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"starLevel":5,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":null,"validTimeStr":"2018-03-03"},{"id":66,"productNum":"10","busiFatherType":3,"busiFatherName":null,"busiParentType":10,"busiParentName":null,"price":78,"showImg":"https://img.alicdn.com/bao/uploaded/i1/3399871558/TB1KFxNX_mWBKNjSZFBXXXxUFXa_!!0-item_pic.jpg_430x430q90.jpg","alternateImg":null,"alternateImgList":null,"productName":"毛孔收缩收敛水","weight":20,"isReward":1,"rewardPoint":6,"rewardPercent":0,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","detailImgList":null,"sellNum":0,"createtime":1518268714000,"storeId":5,"sendType":2,"status":0,"isHotsale":0,"isHot":0,"validTime":1520006400000,"modifyTime":1518310335000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"starLevel":5,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":null,"validTimeStr":"2018-03-03"}],"firstPage":1,"prePage":0,"nextPage":2,"lastPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2]}
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
         * list : [{"id":5,"productNum":"10","busiFatherType":3,"busiFatherName":null,"busiParentType":10,"busiParentName":null,"price":40,"showImg":"https://img.alicdn.com/bao/uploaded/i4/533497499/TB1axT0bf5TBuNjSspmXXaDRVXa_!!0-item_pic.jpg_430x430q90.jpg","alternateImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/7e675598-17ec-40df-a257-cd2e73413a96;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/a57cf063-c62d-44b1-9ccd-8c8107080a31;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/527c0d4c-4fca-4fe2-91a6-b88e23e8e79d;","alternateImgList":null,"productName":"复颜抗皱紧致化妆","weight":20,"isReward":1,"rewardPoint":30,"rewardPercent":0,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/a2fbd2f2-7376-4705-84db-f9958ff6f71f;http://jiujie-pics.oss-cn-beijing.aliyuncs.com/818b1bb2-4edd-400f-99b1-5e434e7da8e0;","detailImgList":null,"sellNum":1,"createtime":1518326314000,"storeId":5,"sendType":2,"status":1,"isHotsale":0,"isHot":0,"validTime":1520035200000,"modifyTime":1519918391000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"starLevel":5,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":null,"validTimeStr":"2018-03-03"},{"id":51,"productNum":"10","busiFatherType":3,"busiFatherName":null,"busiParentType":10,"busiParentName":null,"price":63,"showImg":"img.alicdn.com/bao/uploaded/i2/2680721346/TB2I5T9beGSBuNjSspbXXciipXa_!!2680721346.jpg_430x430q90.jpg","alternateImg":null,"alternateImgList":null,"productName":"秋冬季补水保湿化妆","weight":20,"isReward":1,"rewardPoint":10,"rewardPercent":0,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","detailImgList":null,"sellNum":0,"createtime":1518268714000,"storeId":5,"sendType":2,"status":0,"isHotsale":0,"isHot":0,"validTime":1520006400000,"modifyTime":1518310335000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"starLevel":5,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":null,"validTimeStr":"2018-03-03"},{"id":66,"productNum":"10","busiFatherType":3,"busiFatherName":null,"busiParentType":10,"busiParentName":null,"price":78,"showImg":"https://img.alicdn.com/bao/uploaded/i1/3399871558/TB1KFxNX_mWBKNjSZFBXXXxUFXa_!!0-item_pic.jpg_430x430q90.jpg","alternateImg":null,"alternateImgList":null,"productName":"毛孔收缩收敛水","weight":20,"isReward":1,"rewardPoint":6,"rewardPercent":0,"description":"减肥棒棒哒","productDetail":"透明质酸另一名称为玻尿酸，玻尿酸为错误译名，RESTIAID hyaluronic acid的hyal-意思是像玻璃一样的、光亮透明的，而uronic acid指的是糖醛酸，玻尿酸与尿酸没有任何关系。","detailImg":"https://img.alicdn.com/bao/uploaded/i4/2884685500/TB1YbQIXhWYBuNjy1zkXXXGGpXa_!!0-item_pic.jpg_430x430q90.jpg","detailImgList":null,"sellNum":0,"createtime":1518268714000,"storeId":5,"sendType":2,"status":0,"isHotsale":0,"isHot":0,"validTime":1520006400000,"modifyTime":1518310335000,"commentLv":null,"startTime":null,"endTime":null,"mobile":null,"storeName":null,"telephone":null,"address":null,"distance":null,"starLevel":5,"detailImg1":null,"detailImg2":null,"detailImg3":null,"alternateImg1":null,"alternateImg2":null,"alternateImg3":null,"useTime":null,"validTimeStr":"2018-03-03"}]
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
        private List<QueryProductBean> list;
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

        public List<QueryProductBean> getList() {
            return list;
        }

        public void setList(List<QueryProductBean> list) {
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
