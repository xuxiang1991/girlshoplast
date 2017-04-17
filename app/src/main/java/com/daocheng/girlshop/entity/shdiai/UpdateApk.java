package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

/**
 * 项目名称：girlshop
 * 类描述：apk更新
 * 创建人：jdd
 * 创建时间：2016/10/24 15:02
 * 修改人：jdd
 * 修改时间：2016/10/24 15:02
 * 修改备注：
 */

public class UpdateApk extends ServiceResult{

    /**
     * version : 1
     * url : http://7vijhu.com1.z0.glb.clouddn.com/20160927.apk
     */

    private int version;
    private String url;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
