package com.daocheng.girlshop.entity;

/**
 * 项目名称：girlshop
 * 类描述：上传图片
 * 创建人：Dove
 * 创建时间：2016/3/28 15:10
 * 修改人：Dove
 * 修改时间：2016/3/28 15:10
 * 修改备注：
 */
public class UploadImage extends ServiceResult{


    /**
     * data : /nzh/upload/mobile/deal1459191683627494d475f32303136303331305f3131333033315f414f5f4844522e6a7067.jpg
     */

    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
