package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductGroup;
import com.dbumama.market.model.ProductGroupSet;
import com.dbumama.market.service.api.group.GroupException;
import com.dbumama.market.service.api.group.ProductGroupService;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
@Service("productGroupService")
public class ProductGroupServiceImpl extends AbstractServiceImpl implements ProductGroupService{

	private static final Product productDao = new Product().dao();
	private static final ProductGroup prodGroupdao = new ProductGroup().dao();
	private static final ProductGroupSet prodGroupSetdao = new ProductGroupSet().dao();
	
	@Override
	public void save(ProductGroup productGroup, String products, Long sellerId) throws GroupException {
		if(productGroup == null || sellerId == null || StrKit.isBlank(products))
			throw new GroupException("保存商品分组出错:参数不全");
		if(productGroup.getId() == null){
			productGroup.setSellerId(sellerId);
			productGroup.setActive(true);
			productGroup.save();
			String productIds[]=products.split(",");
			for (int i = 0; i < productIds.length; i++) {
				ProductGroupSet productGroupSet=new ProductGroupSet();
				productGroupSet.setGroupId(productGroup.getId());
				productGroupSet.setProductId(new Long(productIds[i]));
				productGroupSet.setActive(true);
				try {
					productGroupSet.save();
				} catch (Exception e) {
					throw new UmpException(e.getMessage());
				}
			}
		}else{
			productGroup.update();
			try {
				Db.deleteById(ProductGroupSet.table,"group_id",productGroup.getId());
			} catch (Exception e) {
				throw new GroupException(e.getMessage());
			}
			String productIds[]=products.split(",");
			for (int i = 0; i < productIds.length; i++) {
				ProductGroupSet productGroupSet=new ProductGroupSet();
				productGroupSet.setGroupId(productGroup.getId());
				productGroupSet.setProductId(new Long(productIds[i]));
				try {
					productGroupSet.save();
				} catch (Exception e) {
					throw new GroupException(e.getMessage());
				}
			}
		}
	}

	@Override
	public List<ProductResultDto> getGroupProduct(Long groupId) throws ProductException {
		if(groupId == null)
			throw new ProductException ("参数出错");
		List<ProductResultDto> productResults=new ArrayList<ProductResultDto>();
		List<ProductGroupSet> groupSet=prodGroupSetdao.find("select * from "+ProductGroupSet.table+" where group_id=?",groupId);
		for (ProductGroupSet productGroupSet : groupSet) {
			Product product=productDao.findById(productGroupSet.getProductId());
			ProductResultDto productResultDto=new ProductResultDto();
			productResultDto.setId(product.getId());
			productResultDto.setName(product.getName());
			productResultDto.setImg(getImageDomain() + product.getImage());
			productResultDto.setPrice(product.getPrice());
			productResultDto.setStock(product.getStock());
			productResultDto.setStartDate(product.getCreated());
			productResultDto.setSales(product.getSales());
			
			productResults.add(productResultDto);
		}
		return productResults;
	}

	@Override
	public Page<ProductGroup> page(Long sellerId, Integer pageNo, Integer pageSize) {
		QueryHelper helper = new QueryHelper("select * ", "from " + ProductGroup.table);
		helper.addWhere("seller_id", sellerId).addWhere("active", 1).addOrderBy("asc", "updated").build();
		return prodGroupdao.paginate(pageNo, pageSize, 
				helper.getSelect(), 
				helper.getSqlExceptSelect(), 
				helper.getParams());
	}

	@Override
	public ProductGroup findById(Long prodGroupId) {
		return prodGroupdao.findById(prodGroupId);
	}

	@Override
	public List<ProductGroupSet> getProductGroupSetsByGroupId(Long groupId) {
		return prodGroupSetdao.find("select * from " + ProductGroupSet.table + " where group_id=?", groupId);
	}

}
