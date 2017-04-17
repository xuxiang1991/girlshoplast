package com.daocheng.girlshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：地址
 * 创建人：Dove
 * 创建时间：2016/3/21 16:13
 * 修改人：Dove
 * 修改时间：2016/3/21 16:13
 * 修改备注：
 */
public class Address extends ServiceResult{


    /**
     * id : 123
     * reciver : 女神
     * recivePhone : 18651697320
     * regionId : 110000
     * address : 江苏省苏州市高新区玉山路秀水苑4#101
     * postCode : 215216
     * isDefault : Y
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        private int id;
        private String reciver;
        private String recivePhone;
        private String regionId;
        private String address;
        private String city;
        private String postCode;
        private String isDefault;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setReciver(String reciver) {
            this.reciver = reciver;
        }

        public void setRecivePhone(String recivePhone) {
            this.recivePhone = recivePhone;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public int getId() {
            return id;
        }

        public String getReciver() {
            return reciver;
        }

        public String getRecivePhone() {
            return recivePhone;
        }

        public String getRegionId() {
            return regionId;
        }

        public String getAddress() {
            return address;
        }

        public String getPostCode() {
            return postCode;
        }

        public String getIsDefault() {
            return isDefault;
        }
    }
}
