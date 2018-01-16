package com.dbumama.market.service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.service.api.product.ProductCategoryService;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.plugin.activerecord.Page;

@Service("productCategoryService")
public class ProductCategoryServiceImpl extends AbstractServiceImpl implements ProductCategoryService{
	private static final ProductCategory prodCategdao = new ProductCategory().dao();
	@Override
	public List<ProductCategory> list() {
		return prodCategdao.find("select * from " + ProductCategory.table + " where active=1 order by created asc ");
	}

	@Override
	public Page<ProductCategory> page(Long sellerId, Integer pageNo, Integer pageSize) {
		QueryHelper helper = new QueryHelper("select * ", "from " + ProductCategory.table);
		helper.addWhere("seller_id", sellerId)
		.addWhere("active", 1)
		.addOrderBy("asc", "orders").build();
		
		return prodCategdao.paginate(pageNo, pageSize, 
				helper.getSelect(), 
				helper.getSqlExceptSelect(), 
				helper.getParams());
		
	}

	@Override
	public ProductCategory save(ProductCategory category) throws ProductException {
		if(category.getId() == null) 
			category.save();
		else 
			category.update();
		return category;
	}

	@Override
	public ProductCategory findById(Long categoryId) {
		return prodCategdao.findById(categoryId);
	}

}
