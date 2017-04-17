package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：girlshop
 * 类描述：文章、商品的基类
 * 创建人：Dove
 * 创建时间：2016/3/16 10:46
 * 修改人：Dove
 * 修改时间：2016/3/16 10:46
 * 修改备注：
 */
public class baseObject extends ServiceResult{

    public static int KIND_GOODS=0;
    public static int KIND_ADVERTORIAL=1;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("content")
    public String content;

    @SerializedName("kind")
    public int kind;

    @SerializedName("image")
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
