package com.dbumama.market.service.api.module;

import com.dbumama.market.service.common.AbstractParamDto;

@SuppressWarnings("serial")
public class GetFunParamDto extends AbstractParamDto{

	Long funId;
	String authAppId;
	Long moduleId;
	
	public GetFunParamDto(Long funId, String authAppId, Long moduleId) {
		super();
		this.funId = funId;
		this.authAppId = authAppId;
		this.moduleId = moduleId;
	}
	
	public Long getFunId() {
		return funId;
	}
	public void setFunId(Long funId) {
		this.funId = funId;
	}
	public String getAuthAppId() {
		return authAppId;
	}
	public void setAuthAppId(String authAppId) {
		this.authAppId = authAppId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	
	
}
