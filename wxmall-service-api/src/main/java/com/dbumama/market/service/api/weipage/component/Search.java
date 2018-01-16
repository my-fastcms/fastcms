package com.dbumama.market.service.api.weipage.component;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Search implements Serializable {
	private String bgColor;
	private String searchLink;

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getSearchLink() {
		return searchLink;
	}

	public void setSearchLink(String searchLink) {
		this.searchLink = searchLink;
	}

}
