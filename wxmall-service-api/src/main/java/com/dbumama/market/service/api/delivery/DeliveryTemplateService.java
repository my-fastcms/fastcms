package com.dbumama.market.service.api.delivery;

import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.SellerUser;

public interface DeliveryTemplateService {
	
  public DeliveryTemplate doSave(DeliveryTemplate dt,String items, SellerUser sellerUser) throws DeliveryTemplateException;
  
  public DeliveryTemplate doUpdate(DeliveryTemplate dt,String items, SellerUser sellerUser) throws DeliveryTemplateException;

}
