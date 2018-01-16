package com.dbumama.market.service.api.weipage.component;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ad implements Serializable {
	private String id;
	private String imgPath;
	private String title;
	private String adHref;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdHref() {
		return adHref;
	}
	public void setAdHref(String adHref) {
		this.adHref = adHref;
	}

}
