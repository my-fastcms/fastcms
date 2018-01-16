package com.dbumama.market.service.api.weipage.component;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Ads implements Serializable {
	private List<Ad> adList;

	public List<Ad> getAdList() {
		return adList;
	}

	public void setAdList(List<Ad> adList) {
		this.adList = adList;
	}

}
