package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.io.Serializable;
import java.util.List;

/**
 * 类名称：词汇详情
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class WordDetailBean extends ServiceResult{


    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean implements Serializable{
        /**
         * id : 6
         * word_name : good
         * ph_en : gʊd
         * ph_en_mp3 : http://res.iciba.com/resource/amp3/oxford/0/28/a2/28a24294fed307cf7e65361b8da4f6e5.mp3
         * ph_am : ɡʊd
         * ph_am_mp3 : http://res.iciba.com/resource/amp3/1/0/75/5f/755f85c2723bb39381c7379a604160d8.mp3
         * parts : [{"part":"adj.","means":["好的","优秀的","有益的","漂亮的，健全的"]},{"part":"n.","means":["好处，利益","善良","善行","好人"]},{"part":"adv.","means":["同well"]}]
         */

        private int id;
        private String word_name;
        private String ph_en;
        private String ph_en_mp3;
        private String ph_am;
        private String ph_am_mp3;
        private List<PartsBean> parts;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWord_name() {
            return word_name;
        }

        public void setWord_name(String word_name) {
            this.word_name = word_name;
        }

        public String getPh_en() {
            return ph_en;
        }

        public void setPh_en(String ph_en) {
            this.ph_en = ph_en;
        }

        public String getPh_en_mp3() {
            return ph_en_mp3;
        }

        public void setPh_en_mp3(String ph_en_mp3) {
            this.ph_en_mp3 = ph_en_mp3;
        }

        public String getPh_am() {
            return ph_am;
        }

        public void setPh_am(String ph_am) {
            this.ph_am = ph_am;
        }

        public String getPh_am_mp3() {
            return ph_am_mp3;
        }

        public void setPh_am_mp3(String ph_am_mp3) {
            this.ph_am_mp3 = ph_am_mp3;
        }

        public List<PartsBean> getParts() {
            return parts;
        }

        public void setParts(List<PartsBean> parts) {
            this.parts = parts;
        }

        public static class PartsBean {
            /**
             * part : adj.
             * means : ["好的","优秀的","有益的","漂亮的，健全的"]
             */

            private String part;
            private List<String> means;

            public String getPart() {
                return part;
            }

            public void setPart(String part) {
                this.part = part;
            }

            public List<String> getMeans() {
                return means;
            }

            public void setMeans(List<String> means) {
                this.means = means;
            }
        }
    }
}
