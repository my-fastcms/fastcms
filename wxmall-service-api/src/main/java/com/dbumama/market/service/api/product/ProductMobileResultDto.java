package com.dbumama.market.service.api.product;

import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class ProductMobileResultDto extends AbstractResultDto{

	private String name;
	private String store;
	private String mainPic;
	private String price;
	private String marketPrice;
	private String id;
	private String brandLogo;
	
	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public ProductMobileResultDto(){
		setName("");
		setMainPic("");
		setStore("0");
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getMainPic() {
		return mainPic;
	}
	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	
}
