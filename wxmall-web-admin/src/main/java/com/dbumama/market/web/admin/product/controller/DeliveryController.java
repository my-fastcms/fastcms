package com.dbumama.market.web.admin.product.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.Area;
import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.delivery.DeliverySetResultDto;
import com.dbumama.market.service.api.delivery.DeliveryTemplateEditResultDto;
import com.dbumama.market.service.api.delivery.DeliveryTemplateException;
import com.dbumama.market.service.api.delivery.DeliveryTemplateResultDto;
import com.dbumama.market.service.api.delivery.DeliveryTemplateService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
@RouteBind(path = "delivery", viewPath = "delivery")
@Before(IocInterceptor.class)
public class DeliveryController extends AdminBaseController<DeliveryTemplate>{
	
	@Inject.BY_NAME
	private AreaService areaService;
	@Inject.BY_NAME
	private DeliveryTemplateService deliveryTemplateService;
	public void index() {
		render("delivery_index.html");
	}
	public void add() {
		render("delivery_add.html");
	}
	
	public void list(){
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		StringBuilder sqlBuilder = new StringBuilder("from " + DeliveryTemplate.table + " where seller_id = ? and active=1 ");
		sqlBuilder.append(" order by updated desc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		Page<DeliveryTemplate> deliveryTemplatePage = DeliveryTemplate.dao.paginate(getPageNo(), getPageSize(), 
				"select * ", 
				sqlBuilder.toString(), 
				o);
		List<DeliveryTemplateResultDto> dtResults= new ArrayList<DeliveryTemplateResultDto>();
		for (DeliveryTemplate dt : deliveryTemplatePage.getList()) {
			DeliveryTemplateResultDto result=new DeliveryTemplateResultDto();
			result.setId(dt.getId());
			result.setName(dt.getName());
			result.setValuationType(dt.getValuationType());
			result.setStartTime((dt.getUpdated()).toString());
			String sql = "SELECT * FROM "+DeliverySet.table+"  WHERE template_id = ? ORDER BY updated desc";
			List<DeliverySet> deliverySetList=new ArrayList<DeliverySet>();
			List<DeliverySet> dsList=DeliverySet.dao.find(sql, dt.getId());
			for (DeliverySet deliverySet : dsList) {
				if(!StrKit.isBlank(deliverySet.getAreaId())){
					deliverySet.setAreaId(areaService.getAreaName(deliverySet.getAreaId().split(",")));
				}
				deliverySetList.add(deliverySet);
			}
			result.setDeliverySetList(deliverySetList);
			dtResults.add(result);
		}
		Page<DeliveryTemplateResultDto> resultDto=new Page<DeliveryTemplateResultDto>(dtResults, getPageNo(), getPageSize(), deliveryTemplatePage.getTotalPage(), deliveryTemplatePage.getTotalRow());
		rendSuccessJson(resultDto);
	}
	
	public void save() {
		DeliveryTemplate deliveryTemplate=null;
		try {
			deliveryTemplate = getModel();
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("获取运费模板信息失败！");
			return;
		}
		
		if(deliveryTemplate == null){
			rendFailedJson("获取运费模板信息失败！");
			return;
		}
		
		if(deliveryTemplate.getId() == null){
			deliveryTemplate.setSellerId(getSellerId());
			try {
			  DeliveryTemplate dt=deliveryTemplateService.doSave(deliveryTemplate, getPara("items"), getSellerUser());
			  rendSuccessJson(dt);
			} catch (DeliveryTemplateException e) {
				rendFailedJson(e.getMessage());	
			}
		}else{
			try {
			  DeliveryTemplate dt=deliveryTemplateService.doUpdate(deliveryTemplate, getPara("items"), getSellerUser());
			  rendSuccessJson(dt);
			} catch (DeliveryTemplateException e) {
				rendFailedJson(e.getMessage());	
			}
		}
	}
	
	public void edit(){
		Long id=getParaToLong("id");
		DeliveryTemplate dt=DeliveryTemplate.dao.findById(id);
		DeliveryTemplateEditResultDto dtResultDto=new DeliveryTemplateEditResultDto();
		if(dt != null){
			dtResultDto.setId(dt.getId());
			dtResultDto.setName(dt.getName());
			dtResultDto.setValuationType(dt.getValuationType());
			String sql = "SELECT * FROM "+DeliverySet.table+"  WHERE template_id = ? ORDER BY updated desc";
			List<DeliverySet> dsList=DeliverySet.dao.find(sql, dt.getId());
			List<DeliverySetResultDto> numList=new ArrayList<DeliverySetResultDto>();
			List<DeliverySetResultDto> weightList=new ArrayList<DeliverySetResultDto>();
            for (DeliverySet deliverySet : dsList) {
            	DeliverySetResultDto setResultDto=new DeliverySetResultDto();
            	setResultDto.setId(deliverySet.getId());
            	setResultDto.setAreaId(deliverySet.getAreaId());
            	setResultDto.setStartStandards(deliverySet.getStartStandards());
            	setResultDto.setStartFees(deliverySet.getStartFees());
            	setResultDto.setAddStandards(deliverySet.getAddStandards());
            	setResultDto.setAddFees(deliverySet.getAddFees());
            	if(!StrKit.isBlank(deliverySet.getAreaId())){
            	String[] areaId=deliverySet.getAreaId().split(",");
            	setResultDto.setAreaResultDto(areaService.getAreaResultDto(areaId));	
            	}
				if(deliverySet.getValuationType()==1){
					numList.add(setResultDto);
					dtResultDto.setNumList(numList);
				}
				if(deliverySet.getValuationType()==2){
					weightList.add(setResultDto);
					dtResultDto.setWeightList(weightList);
				}
			}
		}
		setAttr("dtResultDto", dtResultDto);
		render("delivery_edit.html");
	}
	public void area() {
		List<Area> listRoot=areaService.findRoots();
		JSONArray jsonArray = new JSONArray();
		
		for (Area area : listRoot) {
			JSONArray jsonArrayChild = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("id", area.getId());
			json.put("name", area.getName());
			List<Area> listChild=areaService.getChildren(area.getId());
			
			for (Area areaChild : listChild) {
				JSONObject jsonChild = new JSONObject();
				jsonChild.put("id", areaChild.getId());
				jsonChild.put("name", areaChild.getName());
				jsonArrayChild.add(jsonChild);
				
			}
			json.put("sub", jsonArrayChild);
			jsonArray.add(json);
			
		}
		
		renderJson(jsonArray);
	}
	
	//通过配送方式获取运费模板
	public void getDeliveryMemoName(){
		Long id=getParaToLong("id");
		List<DeliveryTemplate> templateList=new ArrayList<DeliveryTemplate>();
		if(id!=null){
			DeliveryTemplate dt=DeliveryTemplate.dao.findById(id);
			templateList.add(dt);
		}else{
		String sql="select id,name from " + DeliveryTemplate.table + " where seller_id = ? and active=1 ";
		templateList=DeliveryTemplate.dao.find(sql, getSellerId());
		}
		renderJson(templateList);
 	}
	
	@Override
	protected Class<DeliveryTemplate> getModelClass() {
		return DeliveryTemplate.class;
	}
}
