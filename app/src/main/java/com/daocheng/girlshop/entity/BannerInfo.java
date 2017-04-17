package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：Cooke
 * 类描述：广告页
 * 创建人：Dove
 * 创建时间：2015/10/9 13:46
 * 修改人：Dove
 * 修改时间：2015/10/9 13:46
 * 修改备注：广告页信息
 */
public class BannerInfo extends ServiceResult{


    /**
     * id : 123
     * showImg : /nzh/upload/image/20160317/1458182309427064625.jpg
     * url : www.dig-data.com
     * description : xxx
     * sequence : 1
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int id;
        private String showImg;
        private String url;
        private String description;
        private int sequence;

        public void setId(int id) {
            this.id = id;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getId() {
            return id;
        }

        public String getShowImg() {
            return showImg;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }

        public int getSequence() {
            return sequence;
        }
    }
}
