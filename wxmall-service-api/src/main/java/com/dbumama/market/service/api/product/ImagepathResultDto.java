package com.dbumama.market.service.api.product;

import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class ImagepathResultDto extends AbstractResultDto{
	 private Long id;
     private String imgPath;
     private String imgUrl;
     
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
     
}
