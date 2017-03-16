package com.dbumama.market.service.api.product;

/**
 * 商品不同规格对用的不同价格跟库存以及物流重量
 * @author wangjun
 *
 */
public class ProductSpecPriceResultDto {

	private String price;	
	private String stock;
	private String weight;
	private String promPrice;	//优惠价
	
	public String getPromPrice() {
		return promPrice;
	}
	public void setPromPrice(String promPrice) {
		this.promPrice = promPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
}
