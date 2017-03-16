package com.dbumama.market.web.admin.product.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.specification.SpecificationException;
import com.dbumama.market.service.api.specification.SpecificationService;
import com.dbumama.market.service.api.specification.SpecificationValueService;
import com.dbumama.market.service.api.specification.SpecificationsResultDto;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

@RouteBind(path = "specification", viewPath = "specification")
@Before(IocInterceptor.class)
public class SpecificationController extends AdminBaseController<Specification>{
	@Inject.BY_NAME
    private SpecificationService specificationService;
	@Inject.BY_NAME
    private SpecificationValueService specificationValueService;
	public void index(){
		/*SpecificationParamDto specificationParamDto = new SpecificationParamDto();
		specificationParamDto.setSellerId(getSellerId());
		setAttr("specificationResultDto", specificationService.findAll(specificationParamDto));*/
		render("specification_index.html");
	}
	
	public void list(){	
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		StringBuilder sqlBuilder = new StringBuilder("from " + Specification.table + " where seller_id is null or seller_id=?");
		sqlBuilder.append(" order by orders asc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		Page<Specification> specificationpage=Specification.dao.paginate(getPageNo(), getPageSize(), 
				"select * ", 
				sqlBuilder.toString(), 
				o);		
		List<SpecificationsResultDto> resultDto=new ArrayList<SpecificationsResultDto>();
		for(Specification s:specificationpage.getList()){
			SpecificationsResultDto sResultDto=new SpecificationsResultDto();
			sResultDto.setId(s.getId());
			sResultDto.setName(s.getName());
			sResultDto.setCreated(s.getCreated());
			String sqlvalue = "SELECT * FROM t_specification_value WHERE   (seller_id is null or seller_id="+getSellerId()+") and `specification_id` ="+s.getId();
			List<SpecificationValue> specificationValues = SpecificationValue.dao.find(sqlvalue);
			sResultDto.setSpecificationValues(specificationValues);
			resultDto.add(sResultDto);
		}
		Page<SpecificationsResultDto> sresultPage=new Page<>(resultDto, getPageNo(), getPageSize(), specificationpage.getTotalPage(), specificationpage.getTotalRow());
		rendSuccessJson(sresultPage);
	}
	
	
     public void add(){
    	 render("specification_add.html");
	}
     
     public void edit(){
    	 if(!StrKit.isBlank(getPara(0))){
    		 Specification specification= Specification.dao.findById(getPara(0));
    		 if(specification !=null){
    			 setAttr("specification", specification);
    			 String sql="SELECT * FROM t_specification_value WHERE   (seller_id is null or seller_id="+getSellerId()+") and  `specification_id` ="+getPara(0)+"  order by orders asc";
    			 List<SpecificationValue> specifitionValue=SpecificationValue.dao.find(sql);
    			 setAttr("specifitionValue", specifitionValue);
    		 }
    	 }
    	 render("specification_add.html");
     }
     
     public void save(){
    	 Specification specification=null;
    	 try {
			specification=getModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 if(specification == null){
 			rendFailedJson("获取签到信息失败！");
 			return;
 		}
    	 
    	 if(specification.getId()==null){
    		 specification.setSellerId(getSellerId());
    		 try {
    		 Specification specificationDto=specificationService.doSave(specification, getPara("items"), getSellerUser());
    		 rendSuccessJson(specificationDto);
    		 } catch (SpecificationException e){
    			 rendFailedJson(e.getMessage());
    		 }
    	 }else{
    		 try {
        		 Specification specificationDto=specificationService.doUpdate(specification, getPara("items"), getSellerUser());
        		 rendSuccessJson(specificationDto);
        		 } catch (SpecificationException e){
        			 rendFailedJson(e.getMessage());
        		 } 
    	 }
     }
	
	@Override
	protected Class<Specification> getModelClass() {
		return Specification.class;
	}

}
