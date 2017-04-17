package com.daocheng.girlshop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * logo图片
 * 
 * Dove
 * 2015/07/28
 */
public class Logo extends ServiceResult {
	@SerializedName("ts")
	public String ts;

	@SerializedName("url")
	public String url;

	public String getts() {
		return ts;
	}

	public void setts(String ts) {
		this.ts = ts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
