package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：Dove
 * 创建时间：2016/4/12 17:25
 * 修改人：Dove
 * 修改时间：2016/4/12 17:25
 * 修改备注：
 */
public class orderDetail extends ServiceResult{


    /**
     * createTime : 2016-04-12 10:26:07
     * phone : 13962325335
     * orderNo : 2016041210260797226
     * receiver :
     * status : 2
     * address : 江苏省苏州市常熟市玉山路145号
     * receivePhone :
     * payType : 2
     * commodityList : [{"id":158,"amount":1,"specUnit":"瓶","commodityNo":"1458721847792","name":"拉菲红酒","showImg":"/nzh/upload/image/20160317/1458182309427064625.jpg","unitPrice":0.01,"discount":""}]
     * orderStatus :
     * totalPrice : 0.01
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String createTime;
        private String phone;
        private String orderNo;
        private String receiver;
        private int status;
        private String address;
        private String receivePhone;
        private int payType;
        private String orderStatus;
        private double totalPrice;
        /**
         * id : 158
         * amount : 1
         * specUnit : 瓶
         * commodityNo : 1458721847792
         * name : 拉菲红酒
         * showImg : /nzh/upload/image/20160317/1458182309427064625.jpg
         * unitPrice : 0.01
         * discount :
         */

        private List<CommodityListEntity> commodityList;

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setReceivePhone(String receivePhone) {
            this.receivePhone = receivePhone;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setCommodityList(List<CommodityListEntity> commodityList) {
            this.commodityList = commodityList;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getPhone() {
            return phone;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getReceiver() {
            return receiver;
        }

        public int getStatus() {
            return status;
        }

        public String getAddress() {
            return address;
        }

        public String getReceivePhone() {
            return receivePhone;
        }

        public int getPayType() {
            return payType;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public List<CommodityListEntity> getCommodityList() {
            return commodityList;
        }

        public static class CommodityListEntity {
            private int id;
            private int amount;
            private String specUnit;
            private String commodityNo;
            private String name;
            private String showImg;
            private double unitPrice;
            private String discount;

            public void setId(int id) {
                this.id = id;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public void setSpecUnit(String specUnit) {
                this.specUnit = specUnit;
            }

            public void setCommodityNo(String commodityNo) {
                this.commodityNo = commodityNo;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setShowImg(String showImg) {
                this.showImg = showImg;
            }

            public void setUnitPrice(double unitPrice) {
                this.unitPrice = unitPrice;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public int getId() {
                return id;
            }

            public int getAmount() {
                return amount;
            }

            public String getSpecUnit() {
                return specUnit;
            }

            public String getCommodityNo() {
                return commodityNo;
            }

            public String getName() {
                return name;
            }

            public String getShowImg() {
                return showImg;
            }

            public double getUnitPrice() {
                return unitPrice;
            }

            public String getDiscount() {
                return discount;
            }
        }
    }
}
