package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：Dove
 * 创建时间：2016/4/6 10:32
 * 修改人：Dove
 * 修改时间：2016/4/6 10:32
 * 修改备注：
 */
public class FirstContent extends ServiceResult{


    /**
     * pageSize : 10
     * endRow : 10
     * totalNum : 1
     * result : [{"tags":"女神，美食","id":20,"createTime":"2016-04-01 17:05:32","title":"我就是女神","hehe":0,"brevity":"我就是女神下图是红酒商品over！","brifImg":"/nzh/upload/image/20160401/1459544680378020274.jpg","pv":0,"type":1,"like":0}]
     * totalPages : 1
     * startRow : 0
     * pageNo : 1
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int pageSize;
        private int endRow;
        private int totalNum;
        private int totalPages;
        private int startRow;
        private int pageNo;
        /**
         * tags : 女神，美食
         * id : 20
         * createTime : 2016-04-01 17:05:32
         * title : 我就是女神
         * hehe : 0
         * brevity : 我就是女神下图是红酒商品over！
         * brifImg : /nzh/upload/image/20160401/1459544680378020274.jpg
         * pv : 0
         * type : 1
         * like : 0
         */

        private List<ResultEntity> result;

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public void setResult(List<ResultEntity> result) {
            this.result = result;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getEndRow() {
            return endRow;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getStartRow() {
            return startRow;
        }

        public int getPageNo() {
            return pageNo;
        }

        public List<ResultEntity> getResult() {
            return result;
        }

        public static class ResultEntity {
            private String tags;
            private int id;
            private String createTime;
            private String title;
            private int hehe;
            private String brevity;
            private String brifImg;
            private int pv;
            private int type;
            private int like;

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setHehe(int hehe) {
                this.hehe = hehe;
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

            public void setType(int type) {
                this.type = type;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public String getTags() {
                return tags;
            }

            public int getId() {
                return id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getTitle() {
                return title;
            }

            public int getHehe() {
                return hehe;
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

            public int getType() {
                return type;
            }

            public int getLike() {
                return like;
            }
        }
    }
}
