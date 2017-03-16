package com.dbumama.market.service.api.cart;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.product.ProductFullCutResultDto;
import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class CartItemResultDto extends AbstractResultDto {

	private Long id;
	private String goodId;
    private String goodImg;
    private String goodName;
    private String goodPrice;
    private Integer quantity;
    private List<SpecificationValue> specificationValues;
    private List<ProductFullCutResultDto> fullCutResultDtos; 	//满减送
    
	public CartItemResultDto(){
    	setSpecificationValues(new ArrayList<SpecificationValue>());
    }
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getGoodImg() {
		return goodImg;
	}
	public void setGoodImg(String goodImg) {
		this.goodImg = goodImg;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public List<ProductFullCutResultDto> getFullCutResultDtos() {
		return fullCutResultDtos;
	}
	public void setFullCutResultDtos(List<ProductFullCutResultDto> fullCutResultDtos) {
		this.fullCutResultDtos = fullCutResultDtos;
	}
	
}
