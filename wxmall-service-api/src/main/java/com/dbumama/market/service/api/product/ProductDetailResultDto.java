package com.dbumama.market.service.api.product;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.Product;
import com.dbumama.market.service.api.specification.SpecificationResultDto;
import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class ProductDetailResultDto extends AbstractResultDto{
	private List<String> imageList = new ArrayList<String>();
	private Product product;
	private List<SpecificationResultDto> specifications;
	private ProductPromotionResultDto promotionInfo;
	private List<ProductFullCutResultDto> fullCutInfo;
	private String wirlessUrl;
	
	public String getWirlessUrl() {
		return wirlessUrl;
	}
	public void setWirlessUrl(String wirlessUrl) {
		this.wirlessUrl = wirlessUrl;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<SpecificationResultDto> getSpecifications() {
		return specifications;
	}
	public void setSpecifications(List<SpecificationResultDto> specifications) {
		this.specifications = specifications;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public ProductPromotionResultDto getPromotionInfo() {
		return promotionInfo;
	}
	public void setPromotionInfo(ProductPromotionResultDto promotionInfo) {
		this.promotionInfo = promotionInfo;
	}
	public List<ProductFullCutResultDto> getFullCutInfo() {
		return fullCutInfo;
	}
	public void setFullCutInfo(List<ProductFullCutResultDto> fullCutInfo) {
		this.fullCutInfo = fullCutInfo;
	}
	
	
}
