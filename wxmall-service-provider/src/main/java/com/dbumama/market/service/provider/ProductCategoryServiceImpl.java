package com.dbumama.market.service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.service.api.product.ProductCategoryService;

@Service("productCategoryService")
public class ProductCategoryServiceImpl extends AbstractActivityService implements ProductCategoryService{

	@Override
	public List<ProductCategory> findRoots() {
		return ProductCategory.dao.findRoots(null);
	}

	@Override
	public ProductCategory find(Long productCategoryId) {
		return ProductCategory.dao.findById(productCategoryId);
	}

}
