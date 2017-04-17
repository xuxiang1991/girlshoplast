package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/29 19:55
 * 修改人：jdd
 * 修改时间：2016/6/29 19:55
 * 修改备注：
 */

public class Vrecoder extends ServiceResult{

    /**
     * id : 1
     * content : ccc
     * url : null
     * type : txt
     * length : null
     * updatetime : 1970-01-01 08:00
     * zan : 0
     * userid : 713
     * head : null
     * nickname : 孔妍希cathy
     */

    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean implements Serializable{
        private int id;
        private String content;
        private String url;
        private String type;
        private int length;
        private String updatetime;
        private int zan;
        private int userid;
        private String head;
        private String nickname;
        private boolean iszan;
        private int sub;


        public boolean iszan() {
            return iszan;
        }

        public void setIszan(boolean iszan) {
            this.iszan = iszan;
        }

        public int getSub() {
            return sub;
        }

        public void setSub(int sub) {
            this.sub = sub;
        }

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
