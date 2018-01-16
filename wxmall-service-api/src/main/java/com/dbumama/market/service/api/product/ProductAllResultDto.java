package com.dbumama.market.service.api.product;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class ProductAllResultDto extends AbstractResultDto{
	/**库存 */
	private List<ProductSpecItem> stocks=new ArrayList<ProductSpecItem>();
	
	private List<ImagepathResultDto> imageList = new ArrayList<ImagepathResultDto>();

	public List<ImagepathResultDto> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImagepathResultDto> imageList) {
		this.imageList = imageList;
	}

	/** 规格 */
	private List<Specification> specifications = new ArrayList<Specification>();

	/** 规格值 */
	private List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();

	public List<ProductSpecItem> getStocks() {
		return stocks;
	}

	public void setStocks(List<ProductSpecItem> stocks) {
		this.stocks = stocks;
	}

	public List<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<Specification> specifications) {
		this.specifications = specifications;
	}

	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	
}
