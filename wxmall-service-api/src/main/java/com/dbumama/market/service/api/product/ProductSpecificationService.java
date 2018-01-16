package com.dbumama.market.service.api.product;

import java.util.List;

import com.dbumama.market.model.ProductSpecification;
import com.dbumama.market.model.ProductSpecificationValue;

public interface ProductSpecificationService {
	/*public boolean save(Product product);
	public boolean update(Product product);
	public boolean delete(Long products);*/
	
	public List<ProductSpecification> getSpecificationsByProduct(Long productId) throws ProductException;
	public List<ProductSpecificationValue> getSpecificationVaulesByProduct(Long productId) throws ProductException;
}
