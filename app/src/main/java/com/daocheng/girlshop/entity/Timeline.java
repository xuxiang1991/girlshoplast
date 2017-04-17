package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：时间轴
 * 创建人：Dove
 * 创建时间：2016/4/19 17:18
 * 修改人：Dove
 * 修改时间：2016/4/19 17:18
 * 修改备注：
 */
public class Timeline extends ServiceResult {


    /**
     * drInfo : {"drId":10,"isFocus":"未关注","like":100,"nickName":"岩哥","portraitImage":"/nzh//upload/commodity/146036003788644433138302e706e67.png"}
     * pageList : {"startRow":0,"endRow":"10","pageNo":1,"pageSize":10,"totalPages":1,"totalNum":2,"result":[{"id":123,"showImg":"/nzh/upload/image/20160317/1458182309427064625.jpg","content":"发表了文章《女神就是我，我就是女神》","createTime":"2016-03-16 14:20:20"}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * drId : 10
         * isFocus : 未关注
         * like : 100
         * nickName : 岩哥
         * portraitImage : /nzh//upload/commodity/146036003788644433138302e706e67.png
         */

        private DrInfoEntity drInfo;
        /**
         * startRow : 0
         * endRow : 10
         * pageNo : 1
         * pageSize : 10
         * totalPages : 1
         * totalNum : 2
         * result : [{"id":123,"showImg":"/nzh/upload/image/20160317/1458182309427064625.jpg","content":"发表了文章《女神就是我，我就是女神》","createTime":"2016-03-16 14:20:20"}]
         */

        private PageListEntity pageList;

        public void setDrInfo(DrInfoEntity drInfo) {
            this.drInfo = drInfo;
        }

        public void setPageList(PageListEntity pageList) {
            this.pageList = pageList;
        }

        public DrInfoEntity getDrInfo() {
            return drInfo;
        }

        public PageListEntity getPageList() {
            return pageList;
        }

        public static class DrInfoEntity {
            private int drId;
            private int isFocus;
            private int like;
            private String nickName;
            private String portraitImage;
            private String gender;
            private int articleCount;
            private String address;

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getArticleCount() {
                return articleCount;
            }

            public void setArticleCount(int articleCount) {
                this.articleCount = articleCount;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setDrId(int drId) {
                this.drId = drId;
            }

            public int getIsFocus() {
                return isFocus;
            }

            public void setIsFocus(int isFocus) {
                this.isFocus = isFocus;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public void setPortraitImage(String portraitImage) {
                this.portraitImage = portraitImage;
            }

            public int getDrId() {
                return drId;
            }


            public int getLike() {
                return like;
            }

            public String getNickName() {
                return nickName;
            }

            public String getPortraitImage() {
                return portraitImage;
            }
        }

        public static class PageListEntity {
            private int startRow;
            private String endRow;
            private int pageNo;
            private int pageSize;
            private int totalPages;
            private int totalNum;
            /**
             * id : 123
             * showImg : /nzh/upload/image/20160317/1458182309427064625.jpg
             * content : 发表了文章《女神就是我，我就是女神》
             * createTime : 2016-03-16 14:20:20
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
                private int articleId;
                private String showImg;
                private String content;
                private String createTime;
                private String brevity;
                private String title;


                public int getArticleId() {
                    return articleId;
                }

                public void setArticleId(int articleId) {
                    this.articleId = articleId;
                }

                public String getBrevity() {
                    return brevity;
                }

                public void setBrevity(String brevity) {
                    this.brevity = brevity;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setShowImg(String showImg) {
                    this.showImg = showImg;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }


                public String getShowImg() {
                    return showImg;
                }

                public String getContent() {
                    return content;
                }

                public String getCreateTime() {
                    return createTime;
                }
            }
        }
    }
}
