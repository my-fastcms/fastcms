package com.dbumama.market.service.api.specification;

import java.util.List;

import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class SpecificationResultDto extends AbstractResultDto{

	private Specification specification;
	private List<SpecificationValue> specificationValues;
	public Specification getSpecification() {
		return specification;
	}
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	
}
