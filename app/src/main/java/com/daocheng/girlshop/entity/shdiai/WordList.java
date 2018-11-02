package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 类名称：词汇
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class WordList extends ServiceResult{


    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * lessonId : 23313
         * title : lesson1
         * num : 9
         */

        private int lessonId;
        private String title;
        private int num;

        public int getLessonId() {
            return lessonId;
        }

        public void setLessonId(int lessonId) {
            this.lessonId = lessonId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
