package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by XX on 2016/9/1.
 */
public class MyClass extends ServiceResult {


    /**
     * userid : 720
     * level : 游客
     * head : http://7vijhu.com1.z0.glb.clouddn.com/53351466405398782
     * name : dove
     * number : 13962325335
     * point : 0
     * number56 : 48
     * number87 : 3
     * starttime : 2016-02-21 21:16:00
     * endtime : 2016-10-28 03:00:00
     * spareDay : 56
     * record : [{"id":2631,"title":"abcdefg","time":"8月29日 周一","start":"16:06","end":"16:06","teacher":"Lynn","classroom":"Las Vegas"},{"id":2628,"title":"cccc","time":"7月27日 周三","start":"14:31","end":"14:31","teacher":"Yolanda","classroom":"Paris"},{"id":2625,"title":"本周课程","time":"7月6日 周三","start":"06:30","end":"08:30","teacher":"Nicole","classroom":"Las Vegas"},{"id":2456,"title":"基础1234","time":"6月15日 周三","start":"18:30","end":"20:30","teacher":"Nicole","classroom":"LonDon"},{"id":2457,"title":"念书23","time":"6月15日 周三","start":"18:30","end":"20:30","teacher":"Chitty","classroom":"Paris"},{"id":2245,"title":"3.Difference between English and American","time":"5月12日 周四","start":"18:30","end":"20:30","teacher":"Karen","classroom":"Sydney"},{"id":2245,"title":"3.Difference between English and American","time":"5月12日 周四","start":"18:30","end":"20:30","teacher":"Karen","classroom":"Sydney"},{"id":2245,"title":"3.Difference between English and American","time":"5月12日 周四","start":"18:30","end":"20:30","teacher":"Karen","classroom":"Sydney"},{"id":2245,"title":"3.Difference between English and American","time":"5月12日 周四","start":"18:30","end":"20:30","teacher":"Karen","classroom":"Sydney"}]
     */

    private int userid;
    private String level;
    private String head;
    private String name;
    private String number;
    private int point;
    private int number56;
    private int number87;
    private String starttime;
    private String endtime;
    private int spareDay;
    private int total56;
    private int total87;
    private String levelscope;

    public String getLevelscope() {
        return levelscope;
    }

    public void setLevelscope(String levelscope) {
        this.levelscope = levelscope;
    }

    public int getTotal87() {
        return total87;
    }

    public void setTotal87(int total87) {
        this.total87 = total87;
    }

    public int getTotal56() {
        return total56;
    }

    public void setTotal56(int total56) {
        this.total56 = total56;
    }

    /**
     * id : 2631
     * title : abcdefg
     * time : 8月29日 周一
     * start : 16:06
     * end : 16:06
     * teacher : Lynn
     * classroom : Las Vegas
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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
        private long starttime;
        private long endtime;

        public long getStarttime() {
            return starttime;
        }

        public void setStarttime(long starttime) {
            this.starttime = starttime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

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
