package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：Dove
 * 创建时间：2016/4/5 17:40
 * 修改人：Dove
 * 修改时间：2016/4/5 17:40
 * 修改备注：
 */
public class CartGood implements Serializable{

    @SerializedName("commodityId")
    public int commodityId;

    @SerializedName("amount")
    public int amount;


    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
