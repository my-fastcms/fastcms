package com.dbumama.market.service.api.pay;

import java.util.List;

import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class TempOrderResultDto extends AbstractResultDto{

	private String productId;			//商品id
	private String price;				//商品价格
	private String name;				//商品名称
	private String pcount;				//商品数量
	private String pimage;				//商品缩略图
	private String productSn;			//商品编码
	private String specivalues;			//String 规格值
	private List<SpecificationValue> specificationValues;
	
	public TempOrderResultDto(){
		setPcount("");
	}
	
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	public String getProductSn() {
		return productSn;
	}
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPcount() {
		return pcount;
	}
	public void setPcount(String pcount) {
		this.pcount = pcount;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public String getSpecivalues() {
		return specivalues;
	}
	public void setSpecivalues(String specivalues) {
		this.specivalues = specivalues;
	}	
}
