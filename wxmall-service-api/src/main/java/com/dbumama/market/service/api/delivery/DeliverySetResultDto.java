package com.dbumama.market.service.api.delivery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.service.api.area.AreaResultDto;
import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class DeliverySetResultDto extends AbstractResultDto{
	private Long id;
	private String areaId;
	private Integer startStandards;
	private BigDecimal startFees;
	private Integer addStandards;
	private BigDecimal addFees;
	private List<AreaResultDto> areaResultDto=new ArrayList<AreaResultDto>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public Integer getStartStandards() {
		return startStandards;
	}
	public void setStartStandards(Integer startStandards) {
		this.startStandards = startStandards;
	}
	public BigDecimal getStartFees() {
		return startFees;
	}
	public void setStartFees(BigDecimal startFees) {
		this.startFees = startFees;
	}
	public Integer getAddStandards() {
		return addStandards;
	}
	public void setAddStandards(Integer addStandards) {
		this.addStandards = addStandards;
	}
	public BigDecimal getAddFees() {
		return addFees;
	}
	public void setAddFees(BigDecimal addFees) {
		this.addFees = addFees;
	}
	public List<AreaResultDto> getAreaResultDto() {
		return areaResultDto;
	}
	public void setAreaResultDto(List<AreaResultDto> areaResultDto) {
		this.areaResultDto = areaResultDto;
	}
	

}
