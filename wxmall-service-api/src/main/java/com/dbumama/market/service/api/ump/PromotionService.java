package com.dbumama.market.service.api.ump;

import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.Promotion;
import com.dbumama.market.model.PromotionSet;
import com.dbumama.market.service.api.product.ProductPromotionResultDto;
import com.jfinal.plugin.activerecord.Page;

/**
 * 限时打折
 * @author wangjun
 *
 */
public interface PromotionService {

	/**
	 * 保存限时打折活动
	 * @param promotion
	 * @param promotionSetItems
	 * @throws UmpException
	 */
	public void save(Promotion promotion, Long sellerId, String promotionSetItems) throws UmpException;
	
	/**
	 * 获取现实打折活动信息，包括打折设置明细数据
	 * @param id
	 * @return
	 * @throws UmpException
	 */
	public PromotionResultDto getPromotionInfo(Long id) throws UmpException;
	
	/**
	 * 获取现实打折活动信息，包括打折设置明细数据
	 * @param promotion
	 * @return
	 * @throws UmpException
	 */
	public PromotionResultDto getPromotionInfo(Promotion promotion) throws UmpException;
	
	/**
	 * 获取用户设置的限时打折活动列表
	 * @param promotionDto
	 * @return
	 */
	public Page<PromotionResultDto> list(PromotionParamDto promotionParam) throws UmpException;
	
	/**
	 * 根据商品获取商品的限时打折数据
	 * @param productId
	 * @return
	 * @throws UmpException
	 */
	public ProductPromotionResultDto getProductPromotion(Product product) throws UmpException;
	
	/**
	 * 获取商品限时打折的价格
	 * 多规格返回折后价格区间
	 * @param product
	 * @return
	 * @throws UmpException
	 */
	public String getProductPromotionPriceSection (Product product, PromotionSetResultDto promotionSetParam) throws UmpException;
	
	/**
	 * 获取商品有效时间范围内的打折配置
	 * @param product
	 * @return
	 * @throws UmpException
	 */
	public PromotionSet getProductPromotionSet(Product product) throws UmpException;
	
	/**
	 * 获取商品限时打折的折扣价(商品有多规格)
	 * @param product
	 * @return
	 * @throws UmpException
	 */
	public String getProductPromotionPrice (Product product, ProductSpecItem productSpecItem) throws UmpException;
	
	/**
	 * 获取商品限时打折的折扣价(商品统一规格)
	 * @param product
	 * @return
	 * @throws UmpException
	 */
	public String getProductPromotionPrice (Product product) throws UmpException;
	
}
