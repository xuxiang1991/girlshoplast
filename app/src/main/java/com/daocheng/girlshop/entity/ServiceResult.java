package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 请求结果
 * Dove
 * 2015/07/28
 */
public class ServiceResult implements Serializable {
    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * errcode : E10007
     * errmsg : 账号或者密码错误
     */

    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
