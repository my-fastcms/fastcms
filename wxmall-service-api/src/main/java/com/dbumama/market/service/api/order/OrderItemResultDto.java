package com.dbumama.market.service.api.order;

import java.math.BigDecimal;
import java.util.List;

import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.product.ProductFullCutResultDto;
import com.dbumama.market.service.common.AbstractResultDto;

/**
 * 订单项
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class OrderItemResultDto extends AbstractResultDto {

	private Long productId;
	private String productName;
	private String productImg;
	private String price;									//多个规格显示区间价 比如 100~350
	private Integer quantity;
	private String sn;
	private List<SpecificationValue> specificationValues;
	private String specificationValueNames;					//多个以逗号分割
	private BigDecimal totalPrice;							//根据规格计算出当前商品对应的价格    商品数量乘以商品单价
	private Boolean isReview;                               //该商品是否评价
	private List<ProductFullCutResultDto> fullCutDtos;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	public String getSpecificationValueNames() {
		return specificationValueNames;
	}
	public void setSpecificationValueNames(String specificationValueNames) {
		this.specificationValueNames = specificationValueNames;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Boolean getIsReview() {
		return isReview;
	}
	public void setIsReview(Boolean isReview) {
		this.isReview = isReview;
	}
	public List<ProductFullCutResultDto> getFullCutDtos() {
		return fullCutDtos;
	}
	public void setFullCutDtos(List<ProductFullCutResultDto> fullCutDtos) {
		this.fullCutDtos = fullCutDtos;
	}
}
