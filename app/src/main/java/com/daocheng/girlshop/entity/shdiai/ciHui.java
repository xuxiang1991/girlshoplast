package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 我的词汇
 * Created by Administrator on 2016/6/22.
 */
public class ciHui extends ServiceResult{


    /**
     * id : 1
     * word : helloword
     */

    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        private int id;
        private String word;
        private String transword;

        public String getTransword() {
            return transword;
        }

        public void setTransword(String transword) {
            this.transword = transword;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
