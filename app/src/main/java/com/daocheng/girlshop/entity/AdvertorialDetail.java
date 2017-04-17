package com.daocheng.girlshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：Dove
 * 创建时间：2016/3/23 16:15
 * 修改人：Dove
 * 修改时间：2016/3/23 16:15
 * 修改备注：
 */
public class AdvertorialDetail extends ServiceResult{


    /**
     * authorInfo : {"isFavourite":0,"like":0,"nickName":"Fghdfsh","portraitImage":""}
     * article : {"id":123,"title":"XXX","brifImg":"XXX","content":"XXX","tags":"XXX","pv":84,"createTime":"2016-03-16 14:20:20","isLike":1,"isHehe":0,"type":1,"commodityIds":"5,6","comments":[{"comment":"XXX","createTime":"2016-03-17 16:30:39","creator":"XXX"}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        /**
         * isFavourite : 0
         * like : 0
         * nickName : Fghdfsh
         * portraitImage :
         */

        private AuthorInfoEntity authorInfo;
        /**
         * id : 123
         * title : XXX
         * brifImg : XXX
         * content : XXX
         * tags : XXX
         * pv : 84
         * createTime : 2016-03-16 14:20:20
         * isLike : 1
         * isHehe : 0
         * type : 1
         * commodityIds : 5,6
         * comments : [{"comment":"XXX","createTime":"2016-03-17 16:30:39","creator":"XXX"}]
         */

        private ArticleEntity article;

        public void setAuthorInfo(AuthorInfoEntity authorInfo) {
            this.authorInfo = authorInfo;
        }

        public void setArticle(ArticleEntity article) {
            this.article = article;
        }

        public AuthorInfoEntity getAuthorInfo() {
            return authorInfo;
        }

        public ArticleEntity getArticle() {
            return article;
        }

        public static class AuthorInfoEntity implements Serializable{

            private int like;
            private String nickName;
            private String portraitImage;
            private int drId;

            public int getDrId() {
                return drId;
            }

            public void setDrId(int drId) {
                this.drId = drId;
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

        public static class ArticleEntity implements Serializable{
            private int id;
            private String title;
            private String brifImg;
            private String content;
            private String tags;
            private int pv;
            private String createTime;
            private int isLike;
            private int isHehe;
            private int like;
            private int hehe;
            private int type;
            private String commodityIds;
            private int isFavourite;


            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public int getHehe() {
                return hehe;
            }

            public void setHehe(int hehe) {
                this.hehe = hehe;
            }

            public int getIsFavourite() {
                return isFavourite;
            }

            public void setIsFavourite(int isFavourite) {
                this.isFavourite = isFavourite;
            }

            /**
             * comment : XXX
             * createTime : 2016-03-17 16:30:39
             * creator : XXX
             */


            private List<CommentsEntity> comments;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setBrifImg(String brifImg) {
                this.brifImg = brifImg;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setPv(int pv) {
                this.pv = pv;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setIsLike(int isLike) {
                this.isLike = isLike;
            }

            public void setIsHehe(int isHehe) {
                this.isHehe = isHehe;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setCommodityIds(String commodityIds) {
                this.commodityIds = commodityIds;
            }

            public void setComments(List<CommentsEntity> comments) {
                this.comments = comments;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBrifImg() {
                return brifImg;
            }

            public String getContent() {
                return content;
            }

            public String getTags() {
                return tags;
            }

            public int getPv() {
                return pv;
            }

            public String getCreateTime() {
                return createTime;
            }

            public int getIsLike() {
                return isLike;
            }

            public int getIsHehe() {
                return isHehe;
            }

            public int getType() {
                return type;
            }

            public String getCommodityIds() {
                return commodityIds;
            }

            public List<CommentsEntity> getComments() {
                return comments;
            }

            public static class CommentsEntity extends ServiceResult{
                private String comment;
                private String createTime;
                private String creator;
                private String portrait_image;
                private int like;
                private int hehe;
                private int id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPortrait_image() {
                    return portrait_image;
                }

                public void setPortrait_image(String portrait_image) {
                    this.portrait_image = portrait_image;
                }

                public int getLike() {
                    return like;
                }

                public void setLike(int like) {
                    this.like = like;
                }

                public int getHehe() {
                    return hehe;
                }

                public void setHehe(int hehe) {
                    this.hehe = hehe;
                }

                public void setComment(String comment) {
                    this.comment = comment;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public void setCreator(String creator) {
                    this.creator = creator;
                }

                public String getComment() {
                    return comment;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public String getCreator() {
                    return creator;
                }
            }
        }
    }
}
