package com.dbumama.market.service.api.product;

import java.util.List;

public interface ImageGroupService {
      public List<ImageGroupResultDto> getGroup(Long sellerId); //得到图片分组数据
      
      public String isHaveGroup(Long sellerId);   //是否图片有分组没有则创建
}
