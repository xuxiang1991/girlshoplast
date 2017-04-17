package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：达人列表
 * 创建人：Dove
 * 创建时间：2016/4/14 18:54
 * 修改人：Dove
 * 修改时间：2016/4/14 18:54
 * 修改备注：
 */
public class darenLIst extends ServiceResult{


    /**
     * id : 10
     * motto : 我是ios工程师
     * nickName : 王大大
     * portraitImage :
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
        private String motto;
        private String nickName;
        private String portraitImage;

        public void setId(int id) {
            this.id = id;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setPortraitImage(String portraitImage) {
            this.portraitImage = portraitImage;
        }

        public int getId() {
            return id;
        }

        public String getMotto() {
            return motto;
        }

        public String getNickName() {
            return nickName;
        }

        public String getPortraitImage() {
            return portraitImage;
        }
    }
}
