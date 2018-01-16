package com.dbumama.market.service.api.product;

import java.util.List;

import com.dbumama.market.model.ProductCategory;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @author drs
 *  商品分类
 */
public interface ProductCategoryService {
	
	public List<ProductCategory> list();
	
	Page<ProductCategory> page(Long sellerId, Integer pageNo, Integer pageSize);
	
	ProductCategory save(ProductCategory category) throws ProductException;
	
	ProductCategory findById(Long categoryId);
}
