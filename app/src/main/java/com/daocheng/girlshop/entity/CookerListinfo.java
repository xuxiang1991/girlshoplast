package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：CookeClient
 * 类描述：厨师列表数据
 * 创建人：Dove
 * 创建时间：2015/10/12 16:38
 * 修改人：Dove
 * 修改时间：2015/10/12 16:38
 * 修改备注：
 */
public class CookerListinfo extends ServiceResult {

    @SerializedName("result")
    public List<CookerItem> result;

    public List<CookerItem> getResult() {
        return result;
    }

    public void setResult(List<CookerItem> result) {
        this.result = result;
    }


    public class CookerItem extends ServiceResult {

        @SerializedName("ID")
        public String ID;

        @SerializedName("ChefID")
        public String ChefID;

        @SerializedName("Name")
        public String Name;

        @SerializedName("star")
        public String star;

        @SerializedName("level")
        public String level;

        @SerializedName("TableCount")
        public String TableCount;

        @SerializedName("DishUrl")
        public String DishUrl;

        public String getCOMMENTS() {
            return COMMENTS;
        }


        @SerializedName("HeadUrl")
        public String HeadUrl;

        @SerializedName("ServiceArea")
        public List<DistrectsInfo.District> ServiceArea;

        @SerializedName("Ad")
        public List<BannerInfo.DataEntity> Ad;

        @SerializedName("LaborCosts")
        public String LaborCosts;

        @SerializedName("Desc")
        public String Desc;

        @SerializedName("COMMENTS")
        public String COMMENTS;

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        @SerializedName("Type")
        public String Type;

        @SerializedName("ty")
        public String ty;

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getIDCard() {
            return IDCard;
        }

        public void setIDCard(String IDCard) {
            this.IDCard = IDCard;
        }

        @SerializedName("Team")
        public String Team;

        @SerializedName("IDCard")
        public String IDCard;

        @SerializedName("Status")
        public String Status;

        @SerializedName("Favorites")
        public String Favorites;//收藏

        public String getChefID() {
            return ChefID;
        }

        public void setChefID(String chefID) {
            ChefID = chefID;
        }

        public void setCOMMENTS(String COMMENTS) {
            this.COMMENTS = COMMENTS;
        }

        public List<BannerInfo.DataEntity> getAd() {
            return Ad;
        }

        public void setAd(List<BannerInfo.DataEntity> ad) {
            Ad = ad;
        }

        public String getLaborCosts() {
            return LaborCosts;
        }

        public void setLaborCosts(String laborCosts) {
            LaborCosts = laborCosts;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String desc) {
            Desc = desc;
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

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTableCount() {
            return TableCount;
        }

        public void setTableCount(String tableCount) {
            TableCount = tableCount;
        }

        public String getDishUrl() {
            return DishUrl;
        }

        public void setDishUrl(String dishUrl) {
            DishUrl = dishUrl;
        }

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String headUrl) {
            HeadUrl = headUrl;
        }

        public List<DistrectsInfo.District> getServiceArea() {
            return ServiceArea;
        }

        public void setServiceArea(List<DistrectsInfo.District> serviceArea) {
            ServiceArea = serviceArea;
        }

        public String getTeam() {
            return Team;
        }

        public void setTeam(String team) {
            Team = team;
        }

        public String getFavorites() {
            return Favorites;
        }

        public void setFavorites(String favorites) {
            Favorites = favorites;
        }



        public String getTy() {
            return ty;
        }

        public void setTy(String ty) {
            this.ty = ty;
        }
    }


}
