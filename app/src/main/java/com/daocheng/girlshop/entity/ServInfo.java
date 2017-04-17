package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：Cooke
 * 类描述：服务类信息
 * 创建人：Dove
 * 创建时间：2015/10/9 10:13
 * 修改人：Dove
 * 修改时间：2015/10/9 10:13
 * 修改备注：电话，服务须知等
 */
public class ServInfo extends ServiceResult {

    @SerializedName("ID")
    public String ID;

    @SerializedName("Telephone")
    public String Telephone;

    @SerializedName("Email")
    public String Email;

    @SerializedName("Warranty")
    public String Warranty;

    @SerializedName("Notes")
    public String Notes;

    @SerializedName("AboutUs")
    public String AboutUs;

    @SerializedName("Refund")
    public String Refund;

    @SerializedName("TimeStamp")
    public String TimeStamp;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWarranty() {
        return Warranty;
    }

    public void setWarranty(String warranty) {
        Warranty = warranty;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getAboutUs() {
        return AboutUs;
    }

    public void setAboutUs(String aboutUs) {
        AboutUs = aboutUs;
    }

    public String getRefund() {
        return Refund;
    }

    public void setRefund(String refund) {
        Refund = refund;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }


}
