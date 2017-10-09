package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 类名称：书面作业
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class SmzyBean extends ServiceResult{


    /**
     * record : [{"id":1,"content":"Well, here is[BlankArea] just explained an authentic English phrase \u2013 job done.","answer":"list","pass":0},{"id":2,"content":"Hmmm, let me [BlankArea]. Shall [BlankArea] take 'pot luck'?","answer":"see,we","pass":0},{"id":3,"content":"[BlankArea] didn't know restaurant to eat at, so[BlankArea]  took pot luck and  the one recommended in the guide book.","answer":"We,which,chose","pass":0},{"id":4,"content":"[BlankArea]  that's not pot luck. [BlankArea]  bad luck \u2013 is that on your list of phrases?","answer":"True,That's","pass":0}]
     * friends : list see,we We,which,chose True,That's
     */

    private String friends;
    private List<RecordBean> record;

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * id : 1
         * content : Well, here is[BlankArea] just explained an authentic English phrase – job done.
         * answer : list
         * pass : 0
         */

        private int id;
        private String content;
        private String answer;
        private int pass;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getPass() {
            return pass;
        }

        public void setPass(int pass) {
            this.pass = pass;
        }
    }
}
