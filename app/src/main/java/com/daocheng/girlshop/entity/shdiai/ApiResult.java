package com.daocheng.girlshop.entity.shdiai;

import java.io.Serializable;

/**
 * Created by XX on 2016/6/19.
 */
public class ApiResult implements Serializable{

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
