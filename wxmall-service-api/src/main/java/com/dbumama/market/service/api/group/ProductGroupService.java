package com.dbumama.market.service.api.group;

import java.util.List;

import com.dbumama.market.model.ProductGroup;
import com.dbumama.market.model.ProductGroupSet;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.jfinal.plugin.activerecord.Page;

public interface ProductGroupService {
    public void save(ProductGroup productGroup,String productIds,Long sellerId) throws GroupException;
    /**
     * 提供分组id获取商品列表
     * @param groupId
     * @return
     * @throws ProductException
     */
    public List<ProductResultDto> getGroupProduct(Long groupId) throws ProductException;
    
    public Page<ProductGroup> page(Long sellerId, Integer pageNo, Integer pageSize);
    
    ProductGroup findById(Long prodGroupId);
    
    List<ProductGroupSet> getProductGroupSetsByGroupId(Long groupId);
}
