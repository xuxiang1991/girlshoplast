package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：收藏列表
 * 创建人：Dove
 * 创建时间：2016/4/28 11:21
 * 修改人：Dove
 * 修改时间：2016/4/28 11:21
 * 修改备注：
 */
public class collectList extends ServiceResult{


    /**
     * startRow : 0
     * endRow : 10
     * pageNo : 1
     * pageSize : 10
     * totalPages : 1
     * totalNum : 2
     * result : [{"id":123,"title":"XXX","brevity":"XXX","brifImg":"XXX","pv":84,"createTime":"2016-03-16 14:20:20","like":84,"portraitImage":"xxx","author":"岩哥","hehe":22}]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int startRow;
        private String endRow;
        private int pageNo;
        private int pageSize;
        private int totalPages;
        private int totalNum;
        /**
         * id : 123
         * title : XXX
         * brevity : XXX
         * brifImg : XXX
         * pv : 84
         * createTime : 2016-03-16 14:20:20
         * like : 84
         * portraitImage : xxx
         * author : 岩哥
         * hehe : 22
         */

        private List<ResultEntity> result;

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public void setEndRow(String endRow) {
            this.endRow = endRow;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public void setResult(List<ResultEntity> result) {
            this.result = result;
        }

        public int getStartRow() {
            return startRow;
        }

        public String getEndRow() {
            return endRow;
        }

        public int getPageNo() {
            return pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public List<ResultEntity> getResult() {
            return result;
        }

        public static class ResultEntity {
            private int id;
            private String title;
            private String brevity;
            private String brifImg;
            private int pv;
            private String createTime;
            private int like;
            private String portraitImage;
            private String author;
            private int hehe;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setBrevity(String brevity) {
                this.brevity = brevity;
            }

            public void setBrifImg(String brifImg) {
                this.brifImg = brifImg;
            }

            public void setPv(int pv) {
                this.pv = pv;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public void setPortraitImage(String portraitImage) {
                this.portraitImage = portraitImage;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public void setHehe(int hehe) {
                this.hehe = hehe;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBrevity() {
                return brevity;
            }

            public String getBrifImg() {
                return brifImg;
            }

            public int getPv() {
                return pv;
            }

            public String getCreateTime() {
                return createTime;
            }

            public int getLike() {
                return like;
            }

            public String getPortraitImage() {
                return portraitImage;
            }

            public String getAuthor() {
                return author;
            }

            public int getHehe() {
                return hehe;
            }
        }
    }
}
