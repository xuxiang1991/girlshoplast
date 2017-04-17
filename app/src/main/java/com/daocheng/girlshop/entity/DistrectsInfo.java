package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：Cooke
 * 类描述：乡镇列表
 * 创建人：Dove
 * 创建时间：2015/10/10 16:30
 * 修改人：Dove
 * 修改时间：2015/10/10 16:30
 * 修改备注：
 */
public class DistrectsInfo extends ServiceResult{

    @SerializedName("result")
    public List<District> result;
    @SerializedName("ts")
    public String ts;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public List<District> getResult() {
        return result;
    }

    public void setResult(List<District> result) {
        this.result = result;
    }


    public class District implements Serializable {

        @SerializedName("ID")
        public String ID;

        @SerializedName("Name")
        public String Name;
        public District()
        {

        }

        public District(String id,String Name)
        {
            this.ID=id;
            this.Name=Name;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
