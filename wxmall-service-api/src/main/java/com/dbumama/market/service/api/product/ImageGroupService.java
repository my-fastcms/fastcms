package com.dbumama.market.service.api.product;

import java.util.List;

import com.dbumama.market.model.ImageGroup;
import com.dbumama.market.model.SellerImages;
import com.jfinal.plugin.activerecord.Page;

public interface ImageGroupService {
      public List<ImageGroupResultDto> getGroup(Long sellerId); //得到图片分组数据
      
      public String isHaveGroup(Long sellerId);   //是否图片有分组没有则创建
      
      ImageGroup findById(Long id);
      
      SellerImages findSellerImagesById(Long id);
      
      Page<ImagepathResultDto> pageImages(Long sellerId, Long groupId, Integer pageNo, Integer pageSize);
}
