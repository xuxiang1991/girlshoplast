package com.daocheng.girlshop.entity;

/**
 * 项目名称：girlshop
 * 类描述：登录
 * 创建人：Dove
 * 创建时间：2016/3/21 9:46
 * 修改人：Dove
 * 修改时间：2016/3/21 9:46
 * 修改备注：
 */
public class User extends ServiceResult{

    /**
     * phone : 18651697320
     * userName : irW286003383
     * portraitImage : xx/xx/xx/xx.jpg
     * level : 3
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
        private String userName;
        private String portraitImage;
        private int level;
        private String token;
        private String userSign;
        private boolean isRegister;
        private String nickName;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public boolean isRegister() {
            return isRegister;
        }

        public void setIsRegister(boolean isRegister) {
            this.isRegister = isRegister;
        }

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setPortraitImage(String portraitImage) {
            this.portraitImage = portraitImage;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserName() {
            return userName;
        }

        public String getPortraitImage() {
            return portraitImage;
        }

        public int getLevel() {
            return level;
        }
    }
}
