package com.dbumama.market.service.api.area;

import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class AreaResultDto extends AbstractResultDto{
      private Long id;
      private String name;
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
      
}
