package com.dbumama.market.service.api.module;

public interface ModuleService {

	/**
	 * 获取模块下对于的功能明细数据
	 * @param funParamDto
	 * @return
	 * @throws ModuleException
	 */
	public FuncResultDto getModuleFun(GetFunParamDto funParamDto) throws ModuleException;
	
}
