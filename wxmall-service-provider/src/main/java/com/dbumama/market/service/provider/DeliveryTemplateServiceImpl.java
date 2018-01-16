package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.delivery.DeliveryTemplateException;
import com.dbumama.market.service.api.delivery.DeliveryTemplateResultDto;
import com.dbumama.market.service.api.delivery.DeliveryTemplateService;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
@Service("deliveryTemplateService")
public class DeliveryTemplateServiceImpl implements DeliveryTemplateService{

	private static final DeliverySet deliveryDao = new DeliverySet().dao();
	private static final DeliveryTemplate deliveryTpldao = new DeliveryTemplate().dao();
	
	@Autowired
	private AreaService areaService;
	
	@Override
	@Transactional(rollbackFor = DeliveryTemplateException.class)
	public DeliveryTemplate doSave(DeliveryTemplate dt, String items, SellerUser sellerUser)
			throws DeliveryTemplateException {
		if(StrKit.isBlank(items))
			throw new DeliveryTemplateException("运费模板参数出错");
		
		try {
			dt.save();
		} catch (Exception e) {
			throw new DeliveryTemplateException("系统异常，保存出错");
		}
		
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			String data=json.getString("data");
			JSONArray jsonDelivery = JSONArray.parseArray(data);
			for(int j=0;j<jsonDelivery.size();j++){
				JSONObject jsonSet = jsonDelivery.getJSONObject(j);
				DeliverySet ds=new DeliverySet();
				ds.setTemplateId(dt.getId());
				ds.setValuationType(jsonSet.getLong("deliveryType"));
				ds.setAreaId(jsonSet.getString("areaId"));
				ds.setStartStandards(jsonSet.getInteger("startStandards"));
				ds.setStartFees(jsonSet.getBigDecimal("startFees"));
				ds.setAddStandards(jsonSet.getInteger("addStandards"));
				ds.setAddFees(jsonSet.getBigDecimal("addFees"));
				ds.set("active", 1);
				ds.set("created", new Date());
				ds.set("updated", new Date());
				
				try {
					ds.save();
				} catch (Exception e) {
					e.printStackTrace();
					throw new DeliveryTemplateException("系统异常，保存出错");
				}
			}
		}
		return dt;
	}

	@Override
	@Transactional(rollbackFor = DeliveryTemplateException.class)
	public DeliveryTemplate doUpdate(DeliveryTemplate dt, String items, SellerUser sellerUser)
			throws DeliveryTemplateException {
		if(StrKit.isBlank(items))
			throw new DeliveryTemplateException("运费模板参数出错");
		dt.setUpdated(new Date());
		try {
			dt.update();
		} catch (Exception e) {
			throw new DeliveryTemplateException("系统异常，保存出错");
		}
		
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			String data=json.getString("data");
			JSONArray jsonDelivery = JSONArray.parseArray(data);
			for(int j=0;j<jsonDelivery.size();j++){
				DeliverySet ds;
				JSONObject jsonSet = jsonDelivery.getJSONObject(j);
				Long deliverySetId = jsonSet.getLong("id");
				if(deliverySetId ==null) ds=new DeliverySet();
				else ds = deliveryDao.findById(deliverySetId);
				ds.setTemplateId(dt.getId());
				ds.setValuationType(jsonSet.getLong("deliveryType"));
				ds.setAreaId(jsonSet.getString("areaId"));
				ds.setStartStandards(jsonSet.getInteger("startStandards"));
				ds.setStartFees(jsonSet.getBigDecimal("startFees"));
				ds.setAddStandards(jsonSet.getInteger("addStandards"));
				ds.setAddFees(jsonSet.getBigDecimal("addFees"));
				ds.set("active", 1);
				ds.set("created", new Date());
				ds.set("updated", new Date());
				
				try {
					if(ds.getId() == null){
						ds.save();	
					}else{
						ds.update();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					throw new DeliveryTemplateException("系统异常，保存出错");
				}
			}
		}
		return dt;
	}

	@Override
	public List<DeliverySet> getDeliverySetByTpl(Long templateId) {
		return deliveryDao.find("SELECT * FROM "+DeliverySet.table+"  WHERE template_id = ? ORDER BY updated desc", templateId);
	}

	@Override
	public DeliveryTemplate findById(Long templateId) {
		return deliveryTpldao.findById(templateId);
	}

	@Override
	public Page<DeliveryTemplateResultDto> list(Long sellerId, Integer pageNo, Integer pageSize) {
		
		QueryHelper helper = new QueryHelper("select * ", "from " + DeliveryTemplate.table);
		helper.addWhere("seller_id", sellerId)
		.addWhere("active", 1)
		.addOrderBy("desc", "updated");
		
		Page<DeliveryTemplate> deliveryTemplatePage = deliveryTpldao.paginate(pageNo, pageSize, 
				helper.getSelect(), helper.getSqlExceptSelect(), helper.getParams());
		List<DeliveryTemplateResultDto> dtResults= new ArrayList<DeliveryTemplateResultDto>();
		for (DeliveryTemplate dt : deliveryTemplatePage.getList()) {
			DeliveryTemplateResultDto result=new DeliveryTemplateResultDto();
			result.setId(dt.getId());
			result.setName(dt.getName());
			result.setValuationType(dt.getValuationType());
			result.setStartTime((dt.getUpdated()).toString());
			List<DeliverySet> deliverySetList=new ArrayList<DeliverySet>();
			List<DeliverySet> dsList=getDeliverySetByTpl(dt.getId());
			for (DeliverySet deliverySet : dsList) {
				if(!StrKit.isBlank(deliverySet.getAreaId())){
					deliverySet.setAreaId(areaService.getAreaName(deliverySet.getAreaId().split(",")));
				}
				deliverySetList.add(deliverySet);
			}
			result.setDeliverySetList(deliverySetList);
			dtResults.add(result);
		}
		return new Page<DeliveryTemplateResultDto>(dtResults, deliveryTemplatePage.getPageNumber(), deliveryTemplatePage.getPageSize(), deliveryTemplatePage.getTotalPage(), deliveryTemplatePage.getTotalRow());
	}

	@Override
	public List<DeliveryTemplate> getSellerUserDelivTemplate(Long sellerId) {
		return deliveryTpldao.find("select id,name from " + DeliveryTemplate.table + " where seller_id = ? and active=1 ", sellerId);
	}

}
