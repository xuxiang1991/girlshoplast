package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * Created by XX on 2016/6/23.
 */
public class fuxiList extends ServiceResult{


    /**
     * id : 3
     * updatetime : 2016-06-22 07:25
     * content : hhh
     wqdqdq
     dshd
     * content1 : 呵呵哈哈哈
     * score : 0
     */

    private int score;
    private String type15flag;

    public String getType15flag() {
        return type15flag;
    }

    public void setType15flag(String type15flag) {
        this.type15flag = type15flag;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        private int id;
        private String updatetime;
        private String content;
        private String content1;
        private int score;
        private String pic;
        private String part;




        public String getPart() {
            return part;
        }

        public void setPart(String part) {
            this.part = part;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent1() {
            return content1;
        }

        public void setContent1(String content1) {
            this.content1 = content1;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
