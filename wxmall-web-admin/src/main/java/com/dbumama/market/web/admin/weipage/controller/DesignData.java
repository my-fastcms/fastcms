package com.dbumama.market.web.admin.weipage.controller;

import java.util.List;

import com.dbumama.market.service.api.product.ProductResultDto;

public class DesignData {

	private String content;
	private  List<ProductResultDto> products;
	
	public DesignData(){
		
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<ProductResultDto> getProducts() {
		return products;
	}
	public void setProducts(List<ProductResultDto> products) {
		this.products = products;
	}
}
