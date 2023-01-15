package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/24 11:53
 * 修改人：jdd
 * 修改时间：2016/6/24 11:53
 * 修改备注：
 */

public class dataListResult extends ServiceResult{


    /**
     * id : 7
     * updatetime : 2016-06-22 08:56
     * logo : http://7vijhu.com1.z0.glb.clouddn.com/6bcbb2f693ac47debe3e0cf2d11088dd.png
     * title : 1111
     * content : <p>111634564</p><p>6579678</p><p><br/></p>
     * content50 : 1116345646579678
     */

    private String video;

    private String vedio_pic;
    private int score;


    public String getVideo() {
        return video;
    }

    public String getVedio_pic() {
        return vedio_pic;
    }

    public void setVedio_pic(String vedio_pic) {
        this.vedio_pic = vedio_pic;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public static class RecordBean implements Serializable{
        private int id;
        private String updatetime;
        private String logo;
        private String title;
        private String content;
        private String content50;
        private String mp4;
        private String icon;
        private String studyMp4Url;
        private String isSeeVideo;



        //每日一句
        private String mp3;
        private int count;


        public String getStudyMp4Url() {
            return studyMp4Url;
        }

        public void setStudyMp4Url(String studyMp4Url) {
            this.studyMp4Url = studyMp4Url;
        }

        public String getIsSeeVideo() {
            return isSeeVideo;
        }

        public void setIsSeeVideo(String isSeeVideo) {
            this.isSeeVideo = isSeeVideo;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getMp4() {
            return mp4;
        }

        public void setMp4(String mp4) {
            this.mp4 = mp4;
        }

        public String getMp3() {
            return mp3;
        }

        public void setMp3(String mp3) {
            this.mp3 = mp3;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent50() {
            return content50;
        }

        public void setContent50(String content50) {
            this.content50 = content50;
        }
    }
}
