package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：girlshop
 * 类描述：获取的订单
 * 创建人：Dove
 * 创建时间：2016/4/7 19:19
 * 修改人：Dove
 * 修改时间：2016/4/7 19:19
 * 修改备注：
 */
public class responseOrder extends ServiceResult{


    /**
     * sign : 52F2529C469EAE6B15179686E2437253
     * partnerid : 1319009801
     * orderNo : 2016040817414320126
     * appId : wx462d77ba9d7383c6
     * timeStamp : 1460108503969
     * prepayid : wx20160408174143c4ffca556b0795228470
     * package : Sign=WXPay
     * nonceStr : 34173cb38f07f89ddbebc2ac9128303f
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String sign;
        private String partnerid;
        private String orderNo;
        private String appid;
        private String timestamp;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private double totalPrice;

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }
    }
}
