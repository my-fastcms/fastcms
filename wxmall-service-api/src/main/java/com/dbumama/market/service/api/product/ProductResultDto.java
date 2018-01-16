package com.dbumama.market.service.api.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dbumama.market.service.api.common.AbstractResultDto;
@SuppressWarnings("serial")
public class ProductResultDto extends AbstractResultDto{
	private Long id;
	private String name;
	private String img;
	private String price;
	private BigDecimal marketPrice;
	private Integer stock;
	private Date startDate;
	private String introduction;
	private Long sales;
	private String wirlessUrl;
	
	private List<String> arrayTitle;
	private List<String> arrayInfor;
	private List<String> arrayInforId;
	private List<String> arraySpec;
	private List<String> arrayGroupSet;
	public ProductResultDto (){
		setWirlessUrl("");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Long getSales() {
		return sales;
	}
	public void setSales(Long sales) {
		this.sales = sales;
	}
	public String getWirlessUrl() {
		return wirlessUrl;
	}
	public void setWirlessUrl(String wirlessUrl) {
		this.wirlessUrl = wirlessUrl;
	}

	public List<String> getArrayTitle() {
		return arrayTitle;
	}

	public void setArrayTitle(List<String> arrayTitle) {
		this.arrayTitle = arrayTitle;
	}

	public List<String> getArrayInfor() {
		return arrayInfor;
	}

	public void setArrayInfor(List<String> arrayInfor) {
		this.arrayInfor = arrayInfor;
	}

	public List<String> getArrayInforId() {
		return arrayInforId;
	}

	public void setArrayInforId(List<String> arrayInforId) {
		this.arrayInforId = arrayInforId;
	}

	public List<String> getArraySpec() {
		return arraySpec;
	}

	public void setArraySpec(List<String> arraySpec) {
		this.arraySpec = arraySpec;
	}

	public List<String> getArrayGroupSet() {
		return arrayGroupSet;
	}

	public void setArrayGroupSet(List<String> arrayGroupSet) {
		this.arrayGroupSet = arrayGroupSet;
	}


	
	

}
