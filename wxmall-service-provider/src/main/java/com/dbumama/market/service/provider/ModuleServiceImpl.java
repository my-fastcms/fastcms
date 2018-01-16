package com.dbumama.market.service.provider;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.Module;
import com.dbumama.market.model.ModuleFun;
import com.dbumama.market.service.api.module.FuncResultDto;
import com.dbumama.market.service.api.module.GetFunParamDto;
import com.dbumama.market.service.api.module.ModuleException;
import com.dbumama.market.service.api.module.ModuleService;
import com.jfinal.core.JFinal;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

	private static final ModuleFun moduleFundao = new ModuleFun().dao();
	private static final Module moduledao = new Module().dao();
	
	@Override
	public FuncResultDto getModuleFun(GetFunParamDto funParamDto) throws ModuleException {
		if(funParamDto == null || funParamDto.getAuthAppId() == null 
				|| funParamDto.getFunId() == null || funParamDto.getModuleId() == null)
			throw new ModuleException("参数错误");
		
		Module module = moduledao.findById(funParamDto.getModuleId());
		if(module == null || module.getActive() != 1)
			throw new ModuleException("模块不存在");
		
		if(StrKit.isBlank(module.getClassName()))
			throw new ModuleException("请设置模块className");
		
		ModuleFun moduleFun = moduleFundao.findFirst(
				"select * from " + ModuleFun.table + " where module_id=? and module_fun_id=? ", 
				funParamDto.getModuleId(), funParamDto.getFunId());
		
		if(moduleFun ==null){
			//查找功能
			Class<?> c = null;
			try {
				 c = Class.forName(module.getClassName());
			} catch (ClassNotFoundException e) {
				throw new ModuleException(e.getMessage());
			}
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Table t = TableMapping.me().getTable((Class<? extends Model>) c);
			@SuppressWarnings("rawtypes")
			Model m = null;
			try {
				m = t.getModelClass().newInstance().findById(funParamDto.getFunId());
			} catch (InstantiationException e) {
				throw new ModuleException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new ModuleException(e.getMessage());
			}
			
			moduleFun = new ModuleFun();
			moduleFun.setModuleId(funParamDto.getModuleId());
			moduleFun.setModuleFunId(funParamDto.getFunId());
			moduleFun.setFunName(m.getStr(module.getTableName()+"_name")==null ? "未知名称" : m.getStr(module.getTableName()+"_name"));
			String funUrl = module.getModuleUrl() + "/?" + module.getParamName() + "=" + funParamDto.getFunId();
			moduleFun.setFunUrl(funUrl);
			moduleFun.setActive(1);
			moduleFun.setCreated(new Date());
			moduleFun.setUpdated(new Date());
			moduleFun.save();
		}
		
		FuncResultDto funcResultDto = new FuncResultDto();
		funcResultDto.setFuncUrl("http://"+funParamDto.getAuthAppId()+".dbumama.com" + moduleFun.getFunUrl());
		funcResultDto.setQrcode(JFinal.me().getContextPath()+"/qrcode/genio?url="+"http://"+funParamDto.getAuthAppId()+".dbumama.com" + moduleFun.getFunUrl());
		return funcResultDto;
	}

}
