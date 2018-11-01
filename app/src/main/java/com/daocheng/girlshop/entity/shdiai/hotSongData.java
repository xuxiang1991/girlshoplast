package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.io.Serializable;
import java.util.List;

/**
 * 类名称：热门歌曲
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class hotSongData extends ServiceResult{


    private List<RecordBean> record;

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean implements Serializable{
        /**
         * id : 1
         * cover : http://file.changshuclub.com/1f0ceea975144f50a33b64a1cf94cc9b.jpg
         * type : 3
         * title : 凉凉
         * introduce : 《凉凉》是张碧晨和杨宗纬演唱的歌曲，由刘畅作词，谭旋作曲 [1]  ，收录于2017年2月18日发行的专辑《三生三世十里桃花 电视剧原声带》中，是电视剧《三生三世十里桃花》的片尾曲 [2]  。
         * mp3url : http://file.changshuclub.com/30bb6b8d2b494b119d0b6366ef46671d.mp3
         * lyric : [{"word":"入夜渐微凉,繁花落地成霜,你在远方眺望","word_ch":"耗尽所有暮光,不思量自难相忘","time":"00:12"},{"word":"夭夭桃花凉","word_ch":"前世你怎舍下","time":"00:25"},{"word":"这一海心茫茫","word_ch":"还故作不痛不痒不牵强,都是假象","time":"00:30"},{"word":"凉凉夜色 为你思念成河","word_ch":"化作春泥 呵护着我","time":"01:01"},{"word":"浅浅岁月 拂满爱人袖","word_ch":"片片芳菲 入水流","time":"01:20"},{"word":"凉凉天意 潋滟一身花色","word_ch":"落入凡尘 伤情着我","time":"01:50"}]
         */

        private int id;
        private String cover;
        private int type;
        private String title;
        private String introduce;
        private String mp3url;
        private List<LyricBean> lyric;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getMp3url() {
            return mp3url;
        }

        public void setMp3url(String mp3url) {
            this.mp3url = mp3url;
        }

        public List<LyricBean> getLyric() {
            return lyric;
        }

        public void setLyric(List<LyricBean> lyric) {
            this.lyric = lyric;
        }

        public static class LyricBean implements Serializable{
            /**
             * word : 入夜渐微凉,繁花落地成霜,你在远方眺望
             * word_ch : 耗尽所有暮光,不思量自难相忘
             * time : 00:12
             */

            private String word;
            private String word_ch;
            private String time;

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public String getWord_ch() {
                return word_ch;
            }

            public void setWord_ch(String word_ch) {
                this.word_ch = word_ch;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
