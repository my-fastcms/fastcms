package com.dbumama.market.service.api.delivery;

import java.util.List;

import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.SellerUser;
import com.jfinal.plugin.activerecord.Page;

public interface DeliveryTemplateService {
	
  public DeliveryTemplate doSave(DeliveryTemplate dt,String items, SellerUser sellerUser) throws DeliveryTemplateException;
  
  public DeliveryTemplate doUpdate(DeliveryTemplate dt,String items, SellerUser sellerUser) throws DeliveryTemplateException;

  List<DeliverySet> getDeliverySetByTpl(Long templateId);
  
  DeliveryTemplate findById(Long templateId);
  
  Page<DeliveryTemplateResultDto> list(Long sellerId, Integer pageNo, Integer pageSize);
  
  List<DeliveryTemplate> getSellerUserDelivTemplate(Long sellerId);
}
