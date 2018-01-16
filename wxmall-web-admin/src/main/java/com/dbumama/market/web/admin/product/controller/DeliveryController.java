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
import com.dbumama.market.service.api.delivery.DeliveryTemplateService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;
@RouteBind(path = "delivery", viewPath = "delivery")
public class DeliveryController extends AdminBaseController<DeliveryTemplate>{
	
	@BY_NAME
	private AreaService areaService;
	@BY_NAME
	private DeliveryTemplateService deliveryTemplateService;
	public void index() {
		render("delivery_index.html");
	}
	public void add() {
		render("delivery_add.html");
	}
	
	public void list(){
		rendSuccessJson(deliveryTemplateService.list(getSellerId(), getPageNo(), getPageSize()));
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
		DeliveryTemplate dt=deliveryTemplateService.findById(getParaToLong("id"));
		DeliveryTemplateEditResultDto dtResultDto=new DeliveryTemplateEditResultDto();
		if(dt != null){
			dtResultDto.setId(dt.getId());
			dtResultDto.setName(dt.getName());
			dtResultDto.setValuationType(dt.getValuationType());
			List<DeliverySet> dsList=deliveryTemplateService.getDeliverySetByTpl(dt.getId());
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
	
	/**
	 * 删除运费模板
	 */
	public void del(){
		DeliveryTemplate dt=deliveryTemplateService.findById(getParaToLong("ids"));
		try {
			dt.setActive(0);
			dt.update();
            rendSuccessJson(dt);
		} catch (DeliveryTemplateException e) {
			rendFailedJson(e.getMessage());	
		}
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
			DeliveryTemplate dt=deliveryTemplateService.findById(id);
			templateList.add(dt);
		}else{
			templateList=deliveryTemplateService.getSellerUserDelivTemplate(getSellerId());
		}
		renderJson(templateList);
 	}
	
	@Override
	protected Class<DeliveryTemplate> getModelClass() {
		return DeliveryTemplate.class;
	}
}
