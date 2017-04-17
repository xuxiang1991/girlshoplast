package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 项目名称：Cooke
 * 类描述：地址信息
 * 创建人：Dove
 * 创建时间：2015/9/18 9:58
 * 修改人：Dove
 * 修改时间：2015/9/18 9:58
 * 修改备注：
 */
public class Location implements Serializable{


    //61 ： GPS定位结果，GPS定位成功。
//        62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
//        63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
//        65 ： 定位缓存的结果。
//        66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
//        67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
//        68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
//        161： 网络定位结果，网络定位定位成功。
//        162： 请求串密文解析失败。
//        167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
//        502： key参数错误，请按照说明文档重新申请KEY。
//        505： key不存在或者非法，请按照说明文档重新申请KEY。
//        601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
//        602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
//        501～700：key验证失败，请按照说明文档重新申请KEY。
//        如果不能定位，请记住这个返回值，并到百度LBS开放平台论坛Andriod定位SDK版块中进行交流http://bbs.lbsyun.baidu.com/forum.php?mod=forumdisplay&fid=10 。若返回值是162~167，请将错误码、imei和定位时间反馈至loc-bugs@baidu.com，以便我们跟进追查问题。
    @SerializedName("TypeCode")
    public int TypeCode;

    @SerializedName("Street")
    public String Street;

    @SerializedName("City")
    public String City;

    @SerializedName("Time")
    public String Time;

    @SerializedName("Result")
    public boolean Result;

    //请求成功的话返回位置语义话信息，失败的话返回错误描述
    @SerializedName("Describe")
    public String Describe;


    @SerializedName("Address")
    public String Address;

    @SerializedName("Distrct")
    public String Distrct;

    @SerializedName("CityCode")
    public String CityCode;

    @SerializedName("Province")
    public String Province;

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }


    public String getDistrct() {
        return Distrct;
    }

    public void setDistrct(String Distrct) {
        this.Distrct = Distrct;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }


    public int getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(int typeCode) {
        TypeCode = typeCode;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }
}


