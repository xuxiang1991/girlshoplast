package com.daocheng.girlshop.entity;

/**
 * 项目名称：girlshop
 * 类描述：订单确认
 * 创建人：Dove
 * 创建时间：2016/4/8 10:38
 * 修改人：Dove
 * 修改时间：2016/4/8 10:38
 * 修改备注：
 */
public class comfirmOrder extends ServiceResult{


    /**
     * orderStatus : 已付款
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String orderStatus;

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatus() {
            return orderStatus;
        }
    }
}
