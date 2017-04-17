package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 我的课程
 * Created by Administrator on 2016/6/21.
 */
public class mykeSource extends ServiceResult{


    /**
     * userid : 720
     * level : 游客
     * starttime : 2016-02-21 21:16:00
     * endtime : 2016-10-28 03:00:00
     * number56 : 5
     * number87 : 2
     * spareDay : 128
     * record : [{"title":"3.Difference between English and American","time":"1月9日 周六","start":"18:30","end":"20:30","teacher":"Nicole","classroom":"Sydney"},{"title":"3.Difference between English and American","time":"2月21日 周日","start":"18:30","end":"20:30","teacher":"Naomi","classroom":"Sydney"},{"title":"3.Difference between English and American","time":"5月12日 周四","start":"18:30","end":"20:30","teacher":"Karen","classroom":"Sydney"},{"title":"3.Difference between English and American","time":"1月9日 周六","start":"18:30","end":"20:30","teacher":"Nicole","classroom":"Sydney"},{"title":"3.Difference between English and American","time":"2月21日 周日","start":"18:30","end":"20:30","teacher":"Naomi","classroom":"Sydney"},{"title":"3.Difference between English and American","time":"5月12日 周四","start":"18:30","end":"20:30","teacher":"Karen","classroom":"Sydney"}]
     */

    private int userid;
    private String level;
    private String starttime;
    private String endtime;
    private int number56;
    private int number87;
    private int spareDay;
    /**
     * title : 3.Difference between English and American
     * time : 1月9日 周六
     * start : 18:30
     * end : 20:30
     * teacher : Nicole
     * classroom : Sydney
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

    public int getNumber56() {
        return number56;
    }

    public void setNumber56(int number56) {
        this.number56 = number56;
    }

    public int getNumber87() {
        return number87;
    }

    public void setNumber87(int number87) {
        this.number87 = number87;
    }

    public int getSpareDay() {
        return spareDay;
    }

    public void setSpareDay(int spareDay) {
        this.spareDay = spareDay;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        private int id;
        private String title;
        private String time;
        private String start;
        private String end;
        private String teacher;
        private String classroom;

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
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
    }
}
