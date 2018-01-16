package com.dbumama.market.service.api.product;

import java.util.HashMap;
import java.util.List;

import com.dbumama.market.model.Product;
import com.dbumama.market.service.api.order.OrderException;
import com.jfinal.plugin.activerecord.Page;

public interface ProductService {
	public List<ProductResultDto> getMarketableByIds(Long sellerId,String ids) throws ProductException;
	
	public void save(ProductSubmitParamDto productSubmitParamDto) throws ProductException;
	public void update(ProductSubmitParamDto productSubmitParamDto,Long productId) throws ProductException;
	
	public Page<ProductResultDto> list(ProductParamDto productParamDto) throws ProductException;
	
	public ProductAllResultDto findAllResultDto(ProductParamDto productParamDto);
	
	/**
	 * 微信端商品详情页数据接口
	 * @param productParamDto
	 * @return
	 */
	public ProductDetailResultDto getMobieDetail(ProductParamDto productParamDto);
	
	/**
	 * 微信端获取商品列表
	 * @param mobileParamDto
	 * @return
	 * @throws ProductException
	 */
	public List<ProductMobileResultDto> findProducts4Mobile(ProductMobileParamDto mobileParamDto) throws ProductException;

	/**
	 * 微信端获取打折商品列表
	 * @param mobileParamDto
	 * @return
	 * @throws ProductException
	 */
	public Page<ProductMobileResultDto> getMobilePromotionProduct(ProductMobileParamDto mobileParamDto) throws ProductException;
	
	/**
	 * 微信端获取热卖商品列表
	 * @param mobileParamDto
	 * @return
	 * @throws ProductException
	 */
	public List<ProductMobileResultDto> getHotProduct(ProductMobileParamDto mobileParamDto) throws ProductException;
	
	/**
	 * 微信端获取最新上架商品列表
	 * @param mobileParamDto
	 * @return
	 * @throws ProductException
	 */
	public List<ProductMobileResultDto> getNewProduct(ProductMobileParamDto mobileParamDto) throws ProductException;
	
	/**
	 * 微信端获取推荐商品列表
	 * @param mobileParamDto
	 * @return
	 * @throws ProductException
	 */
	public List<ProductMobileResultDto> getRecommendProduct(ProductMobileParamDto mobileParamDto) throws ProductException;
	
	/**
	 * 微信端获取首页商品列表
	 * @param mobileParamDto
	 * @return
	 * @throws ProductException
	 */
	public List<ProductMobileResultDto> getIndexProduct(ProductMobileParamDto mobileParamDto) throws ProductException;
	
	/**
	 * 获取商品不同规格对应的不同价格以及库存，物流重量
	 * 如果商品设置有限时打折，拼团等活动，获取对应的打折价格
	 * key 为规格id值，比如 1,4,8
	 * ProductSpecPriceResultDto 为该规格对应的价格以及库存
	 * @param productId
	 * @return
	 * @throws ProductException
	 */
	public HashMap<String, ProductSpecPriceResultDto> getProductSpecPrice(Long productId) throws ProductException;
	
	/**
	 * 批量获取已选择的商品
	 * @param orderIds
	 * @return
	 * @throws OrderException
	 */
	public List<ProductResultDto> getProducts(String productIds) throws ProductException;
	
	public Long getProductCountByCategroyId(Long categroyId);
	Product findById(Long productId);
}
