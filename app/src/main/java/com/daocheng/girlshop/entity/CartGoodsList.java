package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：个人购物车
 * 创建人：Dove
 * 创建时间：2016/4/5 14:26
 * 修改人：Dove
 * 修改时间：2016/4/5 14:26
 * 修改备注：
 */
public class CartGoodsList extends ServiceResult{


    /**
     * commodityId : XXX
     * commodityImg : XXX
     * commodityName : XXX
     * createTime : 2016-03-16 14:20:20
     * amount : 2
     * price : 166.3
     * totalPrice : 1
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String commodityId;
        private String commodityImg;
        private String commodityName;
        private String createTime;
        private int amount;
        private double price;
        private double totalPrice;

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public void setCommodityImg(String commodityImg) {
            this.commodityImg = commodityImg;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public String getCommodityImg() {
            return commodityImg;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public int getAmount() {
            return amount;
        }

        public double getPrice() {
            return price;
        }

        public double getTotalPrice() {
            return totalPrice;
        }
    }
}
