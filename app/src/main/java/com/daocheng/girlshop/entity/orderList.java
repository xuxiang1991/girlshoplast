package com.daocheng.girlshop.entity;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：订单列表
 * 创建人：Dove
 * 创建时间：2016/4/12 14:26
 * 修改人：Dove
 * 修改时间：2016/4/12 14:26
 * 修改备注：
 */
public class orderList extends ServiceResult{


    /**
     * startRow : 0
     * endRow : 10
     * pageNo : 1
     * pageSize : 10
     * totalPages : 1
     * totalNum : 2
     * result : [{"orderStatus":"已付款","totalPrice":199.9,"payType":1,"orderNo":"201604081922452","createTime":"2016-04-08 15:23:32"}]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int startRow;
        private String endRow;
        private int pageNo;
        private int pageSize;
        private int totalPages;
        private int totalNum;
        /**
         * orderStatus : 已付款
         * totalPrice : 199.9
         * payType : 1
         * orderNo : 201604081922452
         * createTime : 2016-04-08 15:23:32
         */

        private List<ResultEntity> result;

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public void setEndRow(String endRow) {
            this.endRow = endRow;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public void setResult(List<ResultEntity> result) {
            this.result = result;
        }

        public int getStartRow() {
            return startRow;
        }

        public String getEndRow() {
            return endRow;
        }

        public int getPageNo() {
            return pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public List<ResultEntity> getResult() {
            return result;
        }

        public static class ResultEntity {
            private String orderStatus;
            private double totalPrice;
            private int payType;
            private String orderNo;
            private String createTime;
            private int status;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public int getPayType() {
                return payType;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public String getCreateTime() {
                return createTime;
            }
        }
    }
}
