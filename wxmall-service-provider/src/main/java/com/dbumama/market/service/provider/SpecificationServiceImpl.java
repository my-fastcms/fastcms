package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.specification.SpecificationException;
import com.dbumama.market.service.api.specification.SpecificationParamDto;
import com.dbumama.market.service.api.specification.SpecificationResultDto;
import com.dbumama.market.service.api.specification.SpecificationService;
import com.jfinal.kit.StrKit;
@Service("specificationService")
public class SpecificationServiceImpl implements SpecificationService{

	@Override
	public List<SpecificationResultDto> findAll(SpecificationParamDto specificationParamDto) {
		
		if(specificationParamDto == null)
			throw new MarketBaseException("参数错误");
		
		List<SpecificationResultDto> results = new ArrayList<SpecificationResultDto>();
		String sql = "SELECT * FROM t_specification where seller_id IS NULL or seller_id="+specificationParamDto.getSellerId();
		List<Specification> specifications = Specification.dao.find(sql);
		for(Specification specifcation : specifications){
			SpecificationResultDto sdto = new SpecificationResultDto();
			sdto.setSpecification(specifcation);
			String sqlvalue = "SELECT * FROM t_specification_value WHERE   (seller_id is null or seller_id="+specificationParamDto.getSellerId()+") and `specification_id` ="+specifcation.getId();
			//String sqlvalue = "SELECT * FROM t_specification_value WHERE `specification_id` ="+specifcation.getId();
			List<SpecificationValue> specificationValues = SpecificationValue.dao.find(sqlvalue);
			sdto.setSpecificationValues(specificationValues);
			results.add(sdto);
		}
		return results;
	}

	@Override
	public Specification find(Long specificationIds) {
		return Specification.dao.findById(specificationIds);
	}

	@Override
	@Transactional(rollbackFor = SpecificationException.class)
	public Specification doSave(Specification specification, String items, SellerUser sellerUser) {
		if(StrKit.isBlank(items))
			throw new SpecificationException("规格值参数异常出错");
		try {
			specification.set("active", 1);
			specification.set("type", 1);
		specification.save();
		}catch (Exception e) {
			throw new SpecificationException("系统异常，保存出错");
		}
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			SpecificationValue values=new SpecificationValue(); 
			values.setSpecificationId(specification.getId());
			values.set("name",json.getString("name"));
			values.set("orders",json.getString("orders"));
			values.set("image",null);
			values.setSellerId(sellerUser.getId());
			values.set("active", 1);
			values.set("created", new Date());
			values.set("updated", new Date());
			try {	
			values.save();
				}catch (Exception e) {
					throw new SpecificationException("系统异常，保存出错");
			}
		}
		return specification;
	}

	@Override
	@Transactional(rollbackFor = SpecificationException.class)
	public Specification doUpdate(Specification specification, String items, SellerUser sellerUser) {
		if(StrKit.isBlank(items))
			throw new SpecificationException("规格值参数异常出错");
		try {
		specification.update();
		}catch (Exception e) {
			throw new SpecificationException("系统异常，保存出错");
		}
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			if(json.getLong("itemId") == null){
			SpecificationValue values=new SpecificationValue(); 
			values.setSpecificationId(specification.getId());
			values.set("name",json.getString("name"));
			values.set("orders",json.getString("orders"));
			values.set("image",null);
			values.setSellerId(sellerUser.getId());
			values.set("active", 1);
			values.set("created", new Date());
			values.set("updated", new Date());
			try {	
			values.save();
				}catch (Exception e) {
					throw new SpecificationException("系统异常，保存出错");
			}
			}else{
				SpecificationValue values=new SpecificationValue(); 
				values.setSpecificationId(specification.getId());
				values.set("id",json.getString("itemId"));
				values.set("name",json.getString("name"));
				values.set("orders",json.getString("orders"));
				values.set("image",null);
				values.setSellerId(sellerUser.getId());
				values.set("active", 1);
				values.set("created", new Date());
				values.set("updated", new Date());
				try {	
				values.update();
					}catch (Exception e) {
						throw new SpecificationException("系统异常，保存出错");
				}
			}
		}
		return specification;
	}

}
