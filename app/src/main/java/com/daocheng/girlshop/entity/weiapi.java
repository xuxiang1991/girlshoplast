package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 项目名称：CookeClient
 * 类描述：微信返回类
 * 创建人：Dove
 * 创建时间：2015/11/27 13:18
 * 修改人：Dove
 * 修改时间：2015/11/27 13:18
 * 修改备注：
 */
public class weiapi extends ServiceResult {
    /**
     * appid : wx2e78d6a07c6a2c9e
     * noncestr : 2aad6a85e70f4940859331ee7c642dfe
     * package : prepayid=wx201512171557283d11ba73280552799124
     * sign : 6A6437F822696F0E0238989834ECB472
     * signType : MD5
     * timestamp : 1450339049
     */

    public String sInfo;
    public String Earnest;

    public String wxJsApiParam;

    public String getWxJsApiParam() {
        return wxJsApiParam;
    }

    public void setWxJsApiParam(String wxJsApiParam) {
        this.wxJsApiParam = wxJsApiParam;
    }

    public String getsInfo() {
        return sInfo;
    }

    public void setsInfo(String sInfo) {
        this.sInfo = sInfo;
    }

    public String getEarnest() {
        return Earnest;
    }

    public void setEarnest(String earnest) {
        Earnest = earnest;
    }

    public class wxapiparams implements Serializable {
        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String sign;
        private String signType;
        private String timestamp;
        private String prepayid;

        public String getprepayid() {
            return prepayid;
        }

        public void setprepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public void setappid(String appid) {
            this.appid = appid;
        }

        public void setnoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public void setsign(String sign) {
            this.sign = sign;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public void settimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getappid() {
            return appid;
        }

        public String getnoncestr() {
            return noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public String getsign() {
            return sign;
        }

        public String getSignType() {
            return signType;
        }

        public String gettimestamp() {
            return timestamp;
        }

    }


}
