package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：课程详情
 * 创建人：jdd
 * 创建时间：2016/6/21 19:13
 * 修改人：jdd
 * 修改时间：2016/6/21 19:13
 * 修改备注：
 */

public class dingkeDetail extends ServiceResult{


    /**
     * userid : 720
     * level : 游客
     * title : 手续费是
     * starttime : 2016-06-21 02:30:00
     * endtime : 2016-06-21 06:30:00
     * teacher : Maggie
     * classroom : Las Vegas
     * amount : 100
     * amountd : 0
     * note : cc
     * record : [{"userid":"694","username":"002026","nickname":"Joe"}]
     */

    private int userid;
    private String level;
    private String action;
    private String title;
    private String starttime;
    private String endtime;
    private String teacher;
    private String classroom;
    private int amount;
    private int amountd;
    private String note;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * userid : 694
     * username : 002026
     * nickname : Joe
     */




    private List<RecordBean> record;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountd() {
        return amountd;
    }

    public void setAmountd(int amountd) {
        this.amountd = amountd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        private String userid;
        private String username;
        private String nickname;
        private String chinesename;
        private String englishname;
        private String mobile;

        public String getChinesename() {
            return chinesename;
        }

        public void setChinesename(String chinesename) {
            this.chinesename = chinesename;
        }

        public String getEnglishname() {
            return englishname;
        }

        public void setEnglishname(String englishname) {
            this.englishname = englishname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
