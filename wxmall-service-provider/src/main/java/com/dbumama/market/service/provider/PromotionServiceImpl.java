package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.Promotion;
import com.dbumama.market.model.PromotionSet;
import com.dbumama.market.service.api.product.ProductPromotionResultDto;
import com.dbumama.market.service.api.ump.PromotionParamDto;
import com.dbumama.market.service.api.ump.PromotionResultDto;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.api.ump.PromotionSetResultDto;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@Service("promotionService")
public class PromotionServiceImpl extends AbstractServiceImpl implements PromotionService{

	@Override
	@Transactional(rollbackFor = UmpException.class)
	public void save(Promotion promotion, Long sellerId, String promotionSetItems) throws UmpException {
		if(promotion == null || StrKit.isBlank(promotionSetItems) || sellerId == null)
			throw new UmpException("保存限时折扣出错:参数不全");
		if(promotion.getId() == null){
			promotion.setSellerId(sellerId);
			promotion.setActive(true);
			promotion.save();
		}else{
			promotion.update();
		}
		JSONArray jarr = null;
		try {
			jarr = JSONArray.parseArray(promotionSetItems);
		} catch (Exception e) {
			throw new UmpException(e.getMessage());
		}
		if(jarr == null || jarr.size() <=0) throw new UmpException("未设置活动折扣信息");
		
		//需要添加的折扣设置项
		List<PromotionSet> promotionAddSets = new ArrayList<PromotionSet>();
		//需要更新的折扣设置项
		List<PromotionSet> promotionUpdateSets = new ArrayList<PromotionSet>();
		//需要删除的折扣设置项
		List<PromotionSet> promotionDelSets = new ArrayList<PromotionSet>();
		for(int i=0;i<jarr.size();i++){
			JSONObject jsonObj = jarr.getJSONObject(i);
			if(jsonObj == null 
					|| jsonObj.getLong("productId") == null 
					|| jsonObj.getFloat("jianjia") == null 
					|| jsonObj.getFloat("zhekou") == null
					|| jsonObj.getInteger("ptype") == null
					|| jsonObj.getFloat("promotion") == null 
					|| (!"del".equals(jsonObj.getString("opt")) && jsonObj.getFloat("promotion") <=0)) //新增或修改的时候，打折或减价后商品价格必须大于0
				throw new UmpException("保存折扣活动，折扣设置项值有误，请检查，打折后的价格必须大于0");
			
			Long promotionSetId = jsonObj.getLong("psetId");
			PromotionSet promotionSet = null;
			if(promotionSetId == null){
				promotionSet = new PromotionSet();
				promotionSet.setProductId(jsonObj.getLong("productId"));
				promotionSet.setPromotionId(promotion.getId());
				promotionSet.setCreated(new Date());
				promotionSet.setActive(true);
				promotionSet.setPromotionSetJianjia(jsonObj.getFloat("jianjia"));
				promotionSet.setPromotionSetZhekou(jsonObj.getFloat("zhekou"));
				promotionSet.setPromotionType(jsonObj.getInteger("ptype"));
				promotionSet.setPromotionValue(jsonObj.getFloat("promotion"));
				promotionSet.setUpdated(new Date());
				promotionAddSets.add(promotionSet);
			}else{
				promotionSet = PromotionSet.dao.findById(promotionSetId);
				if(promotionSet == null) throw new UmpException("折扣设置记录不存在");
				if("del".equals(jsonObj.getString("opt"))){
					promotionDelSets.add(promotionSet);
					try {
						promotionSet.delete();	
					} catch (Exception e) {
						throw new UmpException(e.getMessage());
					}
				}
				if("updated".equals(jsonObj.getString("opt"))){
					promotionSet.setPromotionSetJianjia(jsonObj.getFloat("jianjia"));
					promotionSet.setPromotionSetZhekou(jsonObj.getFloat("zhekou"));
					promotionSet.setPromotionType(jsonObj.getInteger("ptype"));
					promotionSet.setPromotionValue(jsonObj.getFloat("promotion"));
					promotionSet.setUpdated(new Date());
					promotionUpdateSets.add(promotionSet);
				}
			}
		}
		
		if(promotionDelSets.size()<=0 && promotionAddSets.size() <=0 && promotionUpdateSets.size()<=0)
			throw new UmpException("没有设置折扣项数据");
		
		try {
			if(promotionAddSets.size() > 0){
				Db.batchSave(promotionAddSets, promotionAddSets.size());	
			}
			if(promotionUpdateSets.size() > 0){
				Db.batchUpdate(promotionUpdateSets, promotionUpdateSets.size());	
			}
		} catch (Exception e) {
			throw new UmpException(e.getMessage());
		}
	}

	@Override
	public PromotionResultDto getPromotionInfo(Long id) throws UmpException {
		if(id == null) throw new UmpException("获取折扣信息参数错误");
		Promotion promotion = Promotion.dao.findById(id);
		if(promotion == null) throw new UmpException("折扣信息不存在");
		return getPromotionInfo(promotion);
	}

	@Override
	public PromotionResultDto getPromotionInfo(Promotion promotion) throws UmpException {
		PromotionResultDto promotionDto = new PromotionResultDto();
		promotionDto.setPromotionId(promotion.getId());
		promotionDto.setPromotionName(promotion.getPromotionName());
		promotionDto.setPromotionTag(promotion.getPromotionTag());
		promotionDto.setStartDate(promotion.getStartDate());
		promotionDto.setEndDate(promotion.getEndDate());
		List<Record> promotionSets = Db.find("select ps.*, p.id as product_id, p.name, p.image, p.price from " + PromotionSet.table + " ps " 
				+ " left join " + Product.table + " p on ps.product_id=p.id "
				+ " where promotion_id=? ", promotion.getId());
		List<PromotionSetResultDto> setResultDtos = new ArrayList<PromotionSetResultDto>();
		for(Record record : promotionSets){
			PromotionSetResultDto psrDto = new PromotionSetResultDto();
			psrDto.setId(record.getLong("id"));
			psrDto.setProductId(record.getLong("product_id"));
			psrDto.setPromotinId(promotion.getId());
			psrDto.setProductName(record.getStr("name"));
			psrDto.setProductImg(getImageDomain() + record.getStr("image"));
			psrDto.setProductPrice(record.getStr("price"));
			psrDto.setType(record.getInt("promotion_type"));
			psrDto.setJianjia(new BigDecimal(record.getFloat("promotion_set_jianjia")).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
			psrDto.setZhekou(new BigDecimal(record.getFloat("promotion_set_zhekou")).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
			psrDto.setPromotionValue(new BigDecimal(record.getFloat("promotion_value")).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
			setResultDtos.add(psrDto);
		}
		promotionDto.setPromotionSets(setResultDtos);
		return promotionDto;
	}
	
	@Override
	public Page<PromotionResultDto> list(PromotionParamDto promotionParam) throws UmpException{
		if(promotionParam == null || promotionParam.getSellerId() == null)
			throw new UmpException("获取限时打折列表数据参数错误");
		
		Page<Promotion> pages = Promotion.dao.paginate(promotionParam.getPageNo(), promotionParam.getPageSize(),
				"select * ", 
				" from " + Promotion.table + " where seller_id=? ", promotionParam.getSellerId());
		
		List<PromotionResultDto> promotionDtos = new ArrayList<PromotionResultDto>();
		
		for(Promotion promotion : pages.getList()){
			PromotionResultDto promotionDto = new PromotionResultDto();
			promotionDto.setPromotionId(promotion.getId());
			promotionDto.setPromotionName(promotion.getPromotionName());
			promotionDto.setPromotionTag(promotion.getPromotionTag());
			promotionDto.setStartDate(promotion.getStartDate());
			promotionDto.setEndDate(promotion.getEndDate());
			promotionDtos.add(promotionDto);
		}
		
		return new Page<PromotionResultDto> (promotionDtos, promotionParam.getPageNo(), promotionParam.getPageSize(), pages.getTotalPage(), pages.getTotalRow());
	}

	@Override
	public ProductPromotionResultDto getProductPromotion(Product product) throws UmpException {
		//查询卖家有效时间范围内的促销活动
        List<Promotion> promotions = Promotion.dao.find(
        		"select * from " + Promotion.table + " where seller_id=? and start_date<=? and end_date>=? ",
        		product.getSellerId(), new Date(), new Date());
        for(Promotion promo : promotions){
        	PromotionResultDto promotionResultDto=getPromotionInfo(promo);
        	List<PromotionSetResultDto> promotionSets = promotionResultDto.getPromotionSets();
        	for(PromotionSetResultDto psrDto : promotionSets){
        		if(psrDto.getProductId() == product.getId()){
        			//获取该商品对应的打折数据
        			ProductPromotionResultDto prodprom = new ProductPromotionResultDto();
        			prodprom.setPromotionTag(promo.getPromotionTag());	//活动标签
        			prodprom.setPromotionPrice(psrDto.getPromotionValue().toString());//打折后的价格
        			if(psrDto.getType() == 1){
        				prodprom.setPromotionInfo(psrDto.getZhekou() + "折 ");
        			}else{
        				prodprom.setPromotionInfo("减￥" + new BigDecimal(psrDto.getJianjia()).setScale(2, BigDecimal.ROUND_HALF_UP));
        			}
        			if(promo.getStartDate().after(new Date())){
        				prodprom.setPromotionTime("还差" + DateTimeUtil.compareDay(promo.getStartDate(), new Date()) + "天开始");        				
        			}else {
        				prodprom.setPromotionTime("剩余" + DateTimeUtil.compareDay(promo.getEndDate(), new Date()) + "天结束");
        			}
        			//如果商品是多规格的话，折扣价也是一个范围
        			String promotionPrice = getProductPromotionPriceSection(product, psrDto);
        			if(StrKit.notBlank(promotionPrice)) prodprom.setPromotionPrice(promotionPrice);
        			return prodprom;
        		}
        	}
        }
		return null;
	}

	@Override
	public String getProductPromotionPriceSection(Product product, PromotionSetResultDto promotionSetParam) throws UmpException {
		List<ProductSpecItem> productSpecItems = ProductSpecItem.dao.find(" select * from "
				+ ProductSpecItem.table + " where product_id=? ", product.getId());
		ProductSpecItem prodSpecFirst = null;
		if(productSpecItems !=null && productSpecItems.size()>0){
			prodSpecFirst = productSpecItems.get(0);
			BigDecimal min, max; min = max = prodSpecFirst.getPrice();
			for(ProductSpecItem productSpecItem : productSpecItems){
		          BigDecimal bprice = productSpecItem.getPrice();
		          if(bprice.compareTo(max)==1){
		        	  max= bprice;
		          }
		          if(bprice.compareTo(min)==-1){
		        	  min= bprice;
		          }
		    }
			BigDecimal minProm, maxProm; String promotionPrice = "";
			if(max.compareTo(min)==0) {
				if(promotionSetParam.getType() == 1) {
					//打折 保留两位小数，四舍五入
					minProm = min.multiply(new BigDecimal(promotionSetParam.getZhekou()/10)).setScale(2, BigDecimal.ROUND_HALF_UP);
				}else {
					//减价
					minProm = min.subtract(new BigDecimal(promotionSetParam.getJianjia())).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				promotionPrice = minProm.toString();
			}else {  
				if(promotionSetParam.getType() ==1) {
					//打折
					minProm = min.multiply(new BigDecimal(promotionSetParam.getZhekou()/10)).setScale(2, BigDecimal.ROUND_HALF_UP);
					maxProm = max.multiply(new BigDecimal(promotionSetParam.getZhekou()/10)).setScale(2, BigDecimal.ROUND_HALF_UP);
				}else {
					//减价
					minProm = min.subtract(new BigDecimal(promotionSetParam.getJianjia())).setScale(2, BigDecimal.ROUND_HALF_UP);
					maxProm = max.subtract(new BigDecimal(promotionSetParam.getJianjia())).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				promotionPrice = minProm.toString() + " ~ " + maxProm.toString();
			}
			return promotionPrice;
		}
		return null;
	}

	@Override
	public PromotionSet getProductPromotionSet(Product product) throws UmpException {
		List<Promotion> promotions = Promotion.dao.find(
        		"select * from " + Promotion.table + " where seller_id=? and start_date<=? and end_date>=? ",
        		product.getSellerId(), new Date(), new Date());
		for(Promotion promo : promotions){
			List<PromotionSet> promotionSets = PromotionSet.dao.find("select * from " + PromotionSet.table + " where promotion_id=? ", promo.getId());
			for(PromotionSet ps : promotionSets){
				if(ps.getProductId() == product.getId())
					return ps;
			}
		}
		return null;
	}

	@Override
	public String getProductPromotionPrice(Product product, ProductSpecItem specItem) throws UmpException {
		PromotionSet promotionSet = getProductPromotionSet(product);
		if(promotionSet !=null){
			BigDecimal promoPrice = null;
			BigDecimal oldPrice = (specItem != null) ? specItem.getPrice() : new BigDecimal(product.getPrice());
			if(promotionSet.getPromotionType() == 1){//打折
				promoPrice = oldPrice.multiply(new BigDecimal(promotionSet.getPromotionSetZhekou()/10));
			}else{
				//减价
				promoPrice = oldPrice.subtract(new BigDecimal(promotionSet.getPromotionSetJianjia()));
			}
			if(promoPrice != null) {
				promoPrice = promoPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				return promoPrice.toString();
			}
		}
		return null;
	}

	@Override
	public String getProductPromotionPrice(Product product) throws UmpException {
		return getProductPromotionPrice(product, null);
	}

}
