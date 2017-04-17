package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：次屏内容
 * 创建人：Dove
 * 创建时间：2016/4/6 10:34
 * 修改人：Dove
 * 修改时间：2016/4/6 10:34
 * 修改备注：
 */
public class SecondContent extends ServiceResult{


    /**
     * id : 1
     * className : 吃喝美食
     * classCode : CHMS
     * showImg : /nzh/upload/image/20160317/1458182309427064625.jpg
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
        private String className;
        private String classCode;
        private String showImg;
        private int sequence;

        public void setId(int id) {
            this.id = id;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setClassCode(String classCode) {
            this.classCode = classCode;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getId() {
            return id;
        }

        public String getClassName() {
            return className;
        }

        public String getClassCode() {
            return classCode;
        }

        public String getShowImg() {
            return showImg;
        }

        public int getSequence() {
            return sequence;
        }
    }
}
