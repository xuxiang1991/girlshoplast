package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：搜索内容
 * 创建人：Dove
 * 创建时间：2016/4/26 15:43
 * 修改人：Dove
 * 修改时间：2016/4/26 15:43
 * 修改备注：
 */
public class allSearchDetail extends ServiceResult {


    /**
     * articles : {"endRow":5,"pageNo":1,"pageSize":5,"result":[{"brevity":"昨天你霸道总裁来撩妹，今天化作太阳的后裔来撩妹，明天还准备乔装成刑警来撩妹！汉子们这么闲吗？就不能消停会儿，让妹子来撩撩你们？ 信不信这些妹子口中唱出的撩汉歌，分分钟让所有汉子脸红耳赤肾上腺素激增！蔡依林 | 美人计妖王殿下 推荐是谁的眼神锁定我，却怕咬一口这苹果\u2026美人计点起这爱火，别站在原地没动作。▼ 陈粒 | 绝对占有 相对自由Salmo 推荐 女神 工程师让我占有你，占有你干净的心，温柔的声音，和完美柔软你的身体。","brifImg":"/nzh/upload/article/14617483873016465616c31343631373931323631323733333132653661373036372e6a7067.jpg","createTime":"2016-04-27 17:14:27","hehe":5,"id":82,"like":7,"pv":35,"title":"撩妹已腻！有哪些撩汉的歌？"}],"startRow":0,"totalNum":1,"totalPages":1}
     * users : [{"gender":"","level":8,"like":0,"motto":"我是ios工程师","myArticle":0,"myCart":0,"myDelivery":0,"myFavourite":0,"nickName":"岩哥","phone":"","portraitImage":"/nzh//upload/commodity/146036003788644433138302e706e67.png","userSign":""}]
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
         * endRow : 5
         * pageNo : 1
         * pageSize : 5
         * result : [{"brevity":"昨天你霸道总裁来撩妹，今天化作太阳的后裔来撩妹，明天还准备乔装成刑警来撩妹！汉子们这么闲吗？就不能消停会儿，让妹子来撩撩你们？ 信不信这些妹子口中唱出的撩汉歌，分分钟让所有汉子脸红耳赤肾上腺素激增！蔡依林 | 美人计妖王殿下 推荐是谁的眼神锁定我，却怕咬一口这苹果\u2026美人计点起这爱火，别站在原地没动作。▼ 陈粒 | 绝对占有 相对自由Salmo 推荐 女神 工程师让我占有你，占有你干净的心，温柔的声音，和完美柔软你的身体。","brifImg":"/nzh/upload/article/14617483873016465616c31343631373931323631323733333132653661373036372e6a7067.jpg","createTime":"2016-04-27 17:14:27","hehe":5,"id":82,"like":7,"pv":35,"title":"撩妹已腻！有哪些撩汉的歌？"}]
         * startRow : 0
         * totalNum : 1
         * totalPages : 1
         */

        private ArticlesEntity articles;
        /**
         * gender :
         * level : 8
         * like : 0
         * motto : 我是ios工程师
         * myArticle : 0
         * myCart : 0
         * myDelivery : 0
         * myFavourite : 0
         * nickName : 岩哥
         * phone :
         * portraitImage : /nzh//upload/commodity/146036003788644433138302e706e67.png
         * userSign :
         */

        private List<UsersEntity> users;

        public void setArticles(ArticlesEntity articles) {
            this.articles = articles;
        }

        public void setUsers(List<UsersEntity> users) {
            this.users = users;
        }

        public ArticlesEntity getArticles() {
            return articles;
        }

        public List<UsersEntity> getUsers() {
            return users;
        }

        public static class ArticlesEntity {
            private int endRow;
            private int pageNo;
            private int pageSize;
            private int startRow;
            private int totalNum;
            private int totalPages;
            /**
             * brevity : 昨天你霸道总裁来撩妹，今天化作太阳的后裔来撩妹，明天还准备乔装成刑警来撩妹！汉子们这么闲吗？就不能消停会儿，让妹子来撩撩你们？ 信不信这些妹子口中唱出的撩汉歌，分分钟让所有汉子脸红耳赤肾上腺素激增！蔡依林 | 美人计妖王殿下 推荐是谁的眼神锁定我，却怕咬一口这苹果…美人计点起这爱火，别站在原地没动作。▼ 陈粒 | 绝对占有 相对自由Salmo 推荐 女神 工程师让我占有你，占有你干净的心，温柔的声音，和完美柔软你的身体。
             * brifImg : /nzh/upload/article/14617483873016465616c31343631373931323631323733333132653661373036372e6a7067.jpg
             * createTime : 2016-04-27 17:14:27
             * hehe : 5
             * id : 82
             * like : 7
             * pv : 35
             * title : 撩妹已腻！有哪些撩汉的歌？
             */

            private List<ResultEntity> result;

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public void setResult(List<ResultEntity> result) {
                this.result = result;
            }

            public int getEndRow() {
                return endRow;
            }

            public int getPageNo() {
                return pageNo;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getStartRow() {
                return startRow;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public List<ResultEntity> getResult() {
                return result;
            }

            public static class ResultEntity {
                private String brevity;
                private String brifImg;
                private String createTime;
                private int hehe;
                private int id;
                private int like;
                private int pv;
                private String title;

                public void setBrevity(String brevity) {
                    this.brevity = brevity;
                }

                public void setBrifImg(String brifImg) {
                    this.brifImg = brifImg;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public void setHehe(int hehe) {
                    this.hehe = hehe;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setLike(int like) {
                    this.like = like;
                }

                public void setPv(int pv) {
                    this.pv = pv;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getBrevity() {
                    return brevity;
                }

                public String getBrifImg() {
                    return brifImg;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public int getHehe() {
                    return hehe;
                }

                public int getId() {
                    return id;
                }

                public int getLike() {
                    return like;
                }

                public int getPv() {
                    return pv;
                }

                public String getTitle() {
                    return title;
                }
            }
        }

        public static class UsersEntity {
            private String gender;
            private int level;
            private int like;
            private String motto;
            private int myArticle;
            private int myCart;
            private int myDelivery;
            private int myFavourite;
            private String nickName;
            private String phone;
            private String portraitImage;
            private String userSign;
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public void setMotto(String motto) {
                this.motto = motto;
            }

            public void setMyArticle(int myArticle) {
                this.myArticle = myArticle;
            }

            public void setMyCart(int myCart) {
                this.myCart = myCart;
            }

            public void setMyDelivery(int myDelivery) {
                this.myDelivery = myDelivery;
            }

            public void setMyFavourite(int myFavourite) {
                this.myFavourite = myFavourite;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setPortraitImage(String portraitImage) {
                this.portraitImage = portraitImage;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public String getGender() {
                return gender;
            }

            public int getLevel() {
                return level;
            }

            public int getLike() {
                return like;
            }

            public String getMotto() {
                return motto;
            }

            public int getMyArticle() {
                return myArticle;
            }

            public int getMyCart() {
                return myCart;
            }

            public int getMyDelivery() {
                return myDelivery;
            }

            public int getMyFavourite() {
                return myFavourite;
            }

            public String getNickName() {
                return nickName;
            }

            public String getPhone() {
                return phone;
            }

            public String getPortraitImage() {
                return portraitImage;
            }

            public String getUserSign() {
                return userSign;
            }
        }
    }
}
