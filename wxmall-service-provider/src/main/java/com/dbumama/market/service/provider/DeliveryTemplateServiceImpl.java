package com.dbumama.market.service.provider;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.delivery.DeliveryTemplateException;
import com.dbumama.market.service.api.delivery.DeliveryTemplateService;
import com.jfinal.kit.StrKit;
@Service("deliveryTemplateService")
public class DeliveryTemplateServiceImpl implements DeliveryTemplateService{

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
				else ds=DeliverySet.dao.findById(deliverySetId);
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

}
