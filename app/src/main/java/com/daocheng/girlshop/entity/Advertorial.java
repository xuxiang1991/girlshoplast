package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：软文
 * 创建人：Dove
 * 创建时间：2016/3/16 11:25
 * 修改人：Dove
 * 修改时间：2016/3/16 11:25
 * 修改备注：
 */
public class Advertorial implements Serializable{

    @SerializedName("image")
    public String image;

    @SerializedName("content")
    public String content;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
