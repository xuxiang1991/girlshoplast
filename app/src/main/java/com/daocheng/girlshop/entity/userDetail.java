package com.daocheng.girlshop.entity;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：Dove
 * 创建时间：2016/4/21 11:59
 * 修改人：Dove
 * 修改时间：2016/4/21 11:59
 * 修改备注：
 */
public class userDetail extends ServiceResult{


    /**
     * phone : 13962325335
     * userSign : 8ea7b5d9b4e825fca0dbe3b2aff01fce
     * portraitImage :
     * nickName : 小公举
     * gender : 男
     * level : 1
     * like : 0
     * motto :
     * myFavourite : 0
     * myCart : 1
     * myDelivery : 2
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String phone;
        private String userSign;
        private String portraitImage;
        private String nickName;
        private String gender;
        private int level;
        private int like;
        private String motto;
        private int myFavourite;
        private int myCart;
        private int myDelivery;
        private int myArticle;
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getMyArticle() {
            return myArticle;
        }

        public void setMyArticle(int myArticle) {
            this.myArticle = myArticle;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setPortraitImage(String portraitImage) {
            this.portraitImage = portraitImage;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public void setMyFavourite(int myFavourite) {
            this.myFavourite = myFavourite;
        }

        public void setMyCart(int myCart) {
            this.myCart = myCart;
        }

        public void setMyDelivery(int myDelivery) {
            this.myDelivery = myDelivery;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getPortraitImage() {
            return portraitImage;
        }

        public String getNickName() {
            return nickName;
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

        public int getMyFavourite() {
            return myFavourite;
        }

        public int getMyCart() {
            return myCart;
        }

        public int getMyDelivery() {
            return myDelivery;
        }
    }
}
