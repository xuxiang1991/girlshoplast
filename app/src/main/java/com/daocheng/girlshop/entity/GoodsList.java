package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：打包商品
 * 创建人：Dove
 * 创建时间：2016/4/1 10:50
 * 修改人：Dove
 * 修改时间：2016/4/1 10:50
 * 修改备注：
 */
public class GoodsList extends ServiceResult{


    /**
     * amount : 1
     * commodityNo : 1458721847792
     * discount :
     * id : 7
     * name : 拉菲红酒
     * showImg : /nzh/upload/image/20160317/1458182309427064625.jpg
     * specUnit : 瓶
     * unitPrice : 1999
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int amount;
        private String commodityNo;
        private String discount;
        private int id;
        private String name;
        private String showImg;
        private String specUnit;
        private double unitPrice;

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setCommodityNo(String commodityNo) {
            this.commodityNo = commodityNo;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public void setSpecUnit(String specUnit) {
            this.specUnit = specUnit;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getAmount() {
            return amount;
        }

        public String getCommodityNo() {
            return commodityNo;
        }

        public String getDiscount() {
            return discount;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShowImg() {
            return showImg;
        }

        public String getSpecUnit() {
            return specUnit;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }
}
