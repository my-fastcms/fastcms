package com.dbumama.market.service.api.delivery;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class DeliveryTemplateEditResultDto extends AbstractResultDto{
      private Long id;
      private String name;
      private Long valuationType;//计费方式
      private List<DeliverySetResultDto> numList=new ArrayList<DeliverySetResultDto>();//平邮运费设置
      private List<DeliverySetResultDto> weightList=new ArrayList<DeliverySetResultDto>();//快递运费设置
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
	public Long getValuationType() {
		return valuationType;
	}
	public void setValuationType(Long valuationType) {
		this.valuationType = valuationType;
	}
	public List<DeliverySetResultDto> getNumList() {
		return numList;
	}
	public void setNumList(List<DeliverySetResultDto> numList) {
		this.numList = numList;
	}
	public List<DeliverySetResultDto> getWeightList() {
		return weightList;
	}
	public void setWeightList(List<DeliverySetResultDto> weightList) {
		this.weightList = weightList;
	}

	

	
}
