package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 类名称：单词等级
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class WordLevel extends ServiceResult{


    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * levelname : EL
         * pass : 1
         */

        private String levelname;
        private int pass;

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public int getPass() {
            return pass;
        }

        public void setPass(int pass) {
            this.pass = pass;
        }
    }
}
