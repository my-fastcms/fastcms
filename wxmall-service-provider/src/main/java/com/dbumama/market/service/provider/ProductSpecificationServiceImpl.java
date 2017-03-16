package com.dbumama.market.service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.ProductSpecification;
import com.dbumama.market.model.ProductSpecificationValue;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductSpecificationService;
@Service("productSpecificationService")
public class ProductSpecificationServiceImpl implements ProductSpecificationService{
	
	/*@Override
	public boolean save(Product product) {
		boolean result = false;
		if (!product.getSpecifications4Page().isEmpty()) {
			//ProductSpecificationValue.dao.delete(product.getId());
			for (Specification specification : product.getSpecifications4Page()) {
				ProductSpecification productSpecification = new ProductSpecification();
				productSpecification.setProductId(product.getId());
				productSpecification.setSpecificationId(specification.getId());
				result = productSpecification.save();
			}
		}
		return result;
	}
	
	@Override
	public boolean update(Product product) {
		boolean result = false;
		if (!product.getSpecifications4Page().isEmpty()) {
			delete(product.getId());
			for (Specification specification : product.getSpecifications4Page()) {
				ProductSpecification productSpecification = new ProductSpecification();
				productSpecification.setProductId(product.getId());
				productSpecification.setSpecificationId(specification.getId());
				result = productSpecification.save();
			}
		}
		return result;
	}

	@Override
	public boolean delete(Long productId) {
		return Db.deleteById("t_product_specification", "product_id", productId);
	}
*/
	
	public List<ProductSpecification> getSpecificationsByProduct(Long productId) throws ProductException {
		return ProductSpecification.dao.find("select * from " + ProductSpecification.table + " where product_id=? ", productId);
	}

	@Override
	public List<ProductSpecificationValue> getSpecificationVaulesByProduct(Long productId) throws ProductException {
		return ProductSpecificationValue.dao.find("select * from " + ProductSpecificationValue.table + " where product_id=? ", productId);
	}

}
