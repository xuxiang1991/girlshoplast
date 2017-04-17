package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：个人购物车
 * 创建人：Dove
 * 创建时间：2016/4/29 15:04
 * 修改人：Dove
 * 修改时间：2016/4/29 15:04
 * 修改备注：
 */
public class myownshop extends ServiceResult{


    /**
     * articleId : 48
     * articleTitle : XXX
     * commoditys : [{"commodityId":"XXX","commodityImg":"XXX","commodityName":"XXX","createTime":"2016-03-16 14:20:20","amount":2,"price":166.3,"totalPrice":1}]
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private boolean ischeck=true;
        private int articleId;
        private String articleTitle;

        public boolean ischeck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        /**
         * commodityId : XXX
         * commodityImg : XXX
         * commodityName : XXX
         * createTime : 2016-03-16 14:20:20
         * amount : 2
         * price : 166.3
         * totalPrice : 1
         */

        private List<CommoditysEntity> commoditys;

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public void setCommoditys(List<CommoditysEntity> commoditys) {
            this.commoditys = commoditys;
        }

        public int getArticleId() {
            return articleId;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public List<CommoditysEntity> getCommoditys() {
            return commoditys;
        }

        public static class CommoditysEntity {
            private boolean ischeck=true;
            private String commodityId;
            private String commodityImg;
            private String commodityName;
            private String createTime;
            private int amount;
            private double price;
            private double totalPrice;

            public boolean ischeck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

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
}
