package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/4/10
 * @Description：美业快报
 * @Version:v1.0.0
 *****************************/
public class HeadlineBean {

    /**
     * code : 200
     * message : success
     * data : {"pageNum":1,"pageSize":10,"size":9,"orderBy":null,"startRow":1,"endRow":9,"total":9,"pages":1,"list":[{"id":9,"title":"中国美业平台战略论坛今天举行","author":"aywen","content":"5月27~28日，美丽看中国-菲迅五周年辉煌庆典暨中国美业平台战略峰会将在中国菲迅教育集团宁波总部隆重举行，力邀优秀产品经销商、美业厂家代表、优秀发廊经营者参与其中。","createtime":1522497957000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/8473abcd-ed33-492b-99db-1481f038ab24","weight":500,"url":"http://news.138job.com/paper/244/91269.shtml"},{"id":5,"title":"测试快报5","author":"admin","content":"这是一条测试记录5","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335768&di=d371a5c0af33b979ad1905e50f2bcca4&imgtype=0&src=http://xs3.op.xywy.com/api.iu1.xywy.com/cms/20160122/5fc62093ba0547a1199870b4c3dc605c55010.jpg","weight":5,"url":""},{"id":4,"title":"测试快报4","author":"admin","content":"这是一条测试记录4","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335767&di=abbb0f5d5088cd058ad9a5b207ddd660&imgtype=0&src=http://06.imgmini.eastday.com/mobile/20170102/20170102022530_cd30d693bbc73aab3a934ecb134fcad0_2.jpeg","weight":4,"url":""},{"id":3,"title":"测试快报3修改","author":"xyx","content":"这是一条测试记录3已经被修改","createtime":1522305366000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/c42f27ac-94d4-4115-91e0-0c585b510761","weight":3,"url":""},{"id":2,"title":"测试快报2","author":"admin","content":"这是一条测试记录2","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335766&di=df5c5728e0dc9f52f7ff12cee74a932b&imgtype=0&src=http://uploads.jy135.com/allimg/201705/8-1F503095R2.jpg","weight":2,"url":""},{"id":1,"title":"测试快报1","author":"admin","content":"这是一条测试记录1","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335763&di=93fb5657c930e75142a28d4bd2ada212&imgtype=0&src=http://image1.wulinsoso.com/hdpic/16sucai/2014/03/07/202043699.jpg","weight":1,"url":""},{"id":7,"title":"test1","author":"YY","content":"test1","createtime":1522336538000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/903994c8-3408-4a15-9217-cd0e68d7d2b7","weight":1,"url":""},{"id":8,"title":"链接走一波","author":"lj","content":"链接走一波","createtime":1522485668000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/72620bf7-8378-487d-8956-aa50c2a2b22c","weight":1,"url":"www.qq.com"},{"id":10,"title":"1","author":"baidu","content":"...............11","createtime":1522660973000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/2e050848-cc76-4766-ac0f-cb78fe874f4f","weight":0,"url":"=1"}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
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
         * size : 9
         * orderBy : null
         * startRow : 1
         * endRow : 9
         * total : 9
         * pages : 1
         * list : [{"id":9,"title":"中国美业平台战略论坛今天举行","author":"aywen","content":"5月27~28日，美丽看中国-菲迅五周年辉煌庆典暨中国美业平台战略峰会将在中国菲迅教育集团宁波总部隆重举行，力邀优秀产品经销商、美业厂家代表、优秀发廊经营者参与其中。","createtime":1522497957000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/8473abcd-ed33-492b-99db-1481f038ab24","weight":500,"url":"http://news.138job.com/paper/244/91269.shtml"},{"id":5,"title":"测试快报5","author":"admin","content":"这是一条测试记录5","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335768&di=d371a5c0af33b979ad1905e50f2bcca4&imgtype=0&src=http://xs3.op.xywy.com/api.iu1.xywy.com/cms/20160122/5fc62093ba0547a1199870b4c3dc605c55010.jpg","weight":5,"url":""},{"id":4,"title":"测试快报4","author":"admin","content":"这是一条测试记录4","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335767&di=abbb0f5d5088cd058ad9a5b207ddd660&imgtype=0&src=http://06.imgmini.eastday.com/mobile/20170102/20170102022530_cd30d693bbc73aab3a934ecb134fcad0_2.jpeg","weight":4,"url":""},{"id":3,"title":"测试快报3修改","author":"xyx","content":"这是一条测试记录3已经被修改","createtime":1522305366000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/c42f27ac-94d4-4115-91e0-0c585b510761","weight":3,"url":""},{"id":2,"title":"测试快报2","author":"admin","content":"这是一条测试记录2","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335766&di=df5c5728e0dc9f52f7ff12cee74a932b&imgtype=0&src=http://uploads.jy135.com/allimg/201705/8-1F503095R2.jpg","weight":2,"url":""},{"id":1,"title":"测试快报1","author":"admin","content":"这是一条测试记录1","createtime":1522305366000,"showImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522315335763&di=93fb5657c930e75142a28d4bd2ada212&imgtype=0&src=http://image1.wulinsoso.com/hdpic/16sucai/2014/03/07/202043699.jpg","weight":1,"url":""},{"id":7,"title":"test1","author":"YY","content":"test1","createtime":1522336538000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/903994c8-3408-4a15-9217-cd0e68d7d2b7","weight":1,"url":""},{"id":8,"title":"链接走一波","author":"lj","content":"链接走一波","createtime":1522485668000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/72620bf7-8378-487d-8956-aa50c2a2b22c","weight":1,"url":"www.qq.com"},{"id":10,"title":"1","author":"baidu","content":"...............11","createtime":1522660973000,"showImg":"http://jiujie-pics.oss-cn-beijing.aliyuncs.com/2e050848-cc76-4766-ac0f-cb78fe874f4f","weight":0,"url":"=1"}]
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
             * id : 9
             * title : 中国美业平台战略论坛今天举行
             * author : aywen
             * content : 5月27~28日，美丽看中国-菲迅五周年辉煌庆典暨中国美业平台战略峰会将在中国菲迅教育集团宁波总部隆重举行，力邀优秀产品经销商、美业厂家代表、优秀发廊经营者参与其中。
             * createtime : 1522497957000
             * showImg : http://jiujie-pics.oss-cn-beijing.aliyuncs.com/8473abcd-ed33-492b-99db-1481f038ab24
             * weight : 500
             * url : http://news.138job.com/paper/244/91269.shtml
             */

            private int id;
            private String title;
            private String author;
            private String content;
            private long createtime;
            private String showImg;
            private int weight;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public String getShowImg() {
                return showImg;
            }

            public void setShowImg(String showImg) {
                this.showImg = showImg;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
