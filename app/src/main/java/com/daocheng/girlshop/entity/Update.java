package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 更新
 * 
 * Dove
 * 2015/07/29
 */
public class Update extends ServiceResult {

	@SerializedName("DOWN_URLS")
	public String DOWN_URLS;

	@SerializedName("VER_CODE")
	public String VER_CODE;

	@SerializedName("VER_NAME")
	public String VER_NAME;

	@SerializedName("DESCRIPTION")
	public String DESCRIPTION;

	public String getDOWN_URLS() {
		return DOWN_URLS;
	}

	public void setDOWN_URLS(String DOWN_URLS) {
		this.DOWN_URLS = DOWN_URLS;
	}

	public String getVER_CODE() {
		return VER_CODE;
	}

	public void setVER_CODE(String VER_CODE) {
		this.VER_CODE = VER_CODE;
	}

	public String getVER_NAME() {
		return VER_NAME;
	}

	public void setVER_NAME(String VER_NAME) {
		this.VER_NAME = VER_NAME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String DESCRIPTION) {
		this.DESCRIPTION = DESCRIPTION;
	}
}
