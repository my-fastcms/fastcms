package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.Cart;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.cart.CartItemResultDto;
import com.dbumama.market.service.api.cart.CartService;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.ump.PromotionService;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * wjun_java@163.com
 * 2016年5月15日
 */
@Service("cartService")
public class CartServiceImpl implements CartService{
	
	@Autowired
	private PromotionService promotionService;
	
	/* (non-Javadoc)
	 * @see com.jfinalshop.service.CartService#add(java.lang.String, int)
	 */
	@Override
	public void add(Long buyerId, Long productId, int quantity, String speci) throws MarketBaseException {
		if(productId == null){
        	throw new MarketBaseException("请传入商品id");
        }
        
        Product product = Product.dao.findById(productId);
        if(product == null){
        	throw new MarketBaseException("商品不存在");
        }
        
        if(product.getIsMarketable() == false){
        	throw new MarketBaseException("商品已下架");
        }
        
        if(product.getStock() <=0){
        	throw new MarketBaseException("商品库存不够");
        }
        
        Cart cart = Cart.dao.findFirst("select * from "+Cart.table+" where buyer_id=? and product_id=? ", buyerId, productId);
        if(cart == null){
        	cart = new Cart();
        	cart.setCreated(new Date());
        	cart.setUpdated(new Date());
        	cart.setQuantity(quantity);
        	cart.setProductId(productId);
        	cart.setBuyerId(buyerId);
        	cart.setActive(1);
        	cart.setSpecification(speci);
        	cart.save();
        }else {
        	cart.setQuantity(cart.getQuantity() + quantity);
        	cart.setSpecification(speci);
        	cart.setUpdated(new Date());
        	cart.update();
        }		
	}

	@Override
	public List<CartItemResultDto> getCartsByBuyer(Long buyerId) throws MarketBaseException {
		if(buyerId == null) throw new MarketBaseException("获取购物车失败，请检查参数");
		//查找购物车
    	List<Record> records = Db.find(
    			"select c.id as item_id, c.quantity, c.specification, p.`name`, p.price, p.image, p.id as p_id from "+Cart.table+" c "
    			+ "left JOIN "+Product.table+" p on c.product_id=p.id where c.buyer_id=? ", buyerId);
    	
    	List<CartItemResultDto> items = new ArrayList<CartItemResultDto>();
    	for(Record record : records){
    		//获取商品限时打折信息
    		Product product = Product.dao.findById(record.getLong("p_id"));
    		CartItemResultDto cv = new CartItemResultDto();
    		cv.setGoodPrice(record.getStr("price"));	
			cv.setId(record.getLong("item_id"));
			cv.setGoodId(record.getLong("p_id").toString());
			cv.setGoodName(record.getStr("name").length()>12 ? record.getStr("name").substring(0, 12).concat("...") : record.getStr("name"));
			cv.setGoodImg(record.getStr("image"));
			cv.setQuantity(record.getInt("quantity"));
    		if(StrKit.isBlank(record.getStr("specification"))){
    			//统一规格下，获取商品限时打折
    			String promoPrice = promotionService.getProductPromotionPrice(product);
    			if(StrKit.notBlank(promoPrice)) cv.setGoodPrice(promoPrice);
    		}else{
    			//多规格
    			String sfvalue="";
    			List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();
        		JSONArray jsonArr = JSON.parseArray(record.getStr("specification"));
        		for(int i=0;i<jsonArr.size();i++){
        			JSONObject json = jsonArr.getJSONObject(i);
        			Long spvid = json.getLong("spvId");
        			sfvalue+=json.getString("spvId")+",";
        			specificationValues.add(SpecificationValue.dao.findById(spvid));
        		}
        		sfvalue=sfvalue.substring(0,sfvalue.length()-1);
        		cv.setSpecificationValues(specificationValues);
        		ProductSpecItem stock = ProductSpecItem.dao.findFirst(
        				"select * FROM "+ProductSpecItem.table+" WHERE product_id = ? and specification_value = ?", record.getLong("p_id"), sfvalue);
        		if(stock != null){
        			String price=stock.getPrice().toString();
            		if(StrKit.notBlank(price)){
            			cv.setGoodPrice(price);
            		}	
        		}
        		//获取商品促销打折价
        		String promoPrice = promotionService.getProductPromotionPrice(product, stock);
        		if(StrKit.notBlank(promoPrice)){
        			cv.setGoodPrice(promoPrice);
        		}
    		}
    		items.add(cv);
    	}
    	return items;
	}

	@Override
	public Long getCartItemCountByBuyer(Long buyerId) throws MarketBaseException {
		return Db.queryLong("select count(id) from " + Cart.table + " t where t.buyer_id=?", buyerId);
	}

}
