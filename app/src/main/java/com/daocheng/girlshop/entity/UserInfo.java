package com.daocheng.girlshop.entity;

/**
 * 项目名称：girlshop
 * 类描述：用户信息
 * 创建人：Dove
 * 创建时间：2016/4/27 15:40
 * 修改人：Dove
 * 修改时间：2016/4/27 15:40
 * 修改备注：
 */
public class UserInfo extends ServiceResult{


    /**
     * phone : 18651697320
     * userSign : 8ea7b5d9b4e825fca0dbe3b2aff01fce
     * portraitImage : xx/xx/xx/xx.jpg
     * nickName : 女神
     * level : 3
     * gender : 女
     * address : 江苏省 苏州市
     * constellation : 金牛座
     * bloodType : O型
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
        private int level;
        private String gender;
        private String address;
        private String constellation;
        private String bloodType;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setportraitImage(String portraitImage) {
            this.portraitImage = portraitImage;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getportraitImage() {
            return portraitImage;
        }

        public String getNickName() {
            return nickName;
        }

        public int getLevel() {
            return level;
        }

        public String getGender() {
            return gender;
        }

        public String getAddress() {
            return address;
        }

        public String getConstellation() {
            return constellation;
        }

        public String getBloodType() {
            return bloodType;
        }
    }
}
