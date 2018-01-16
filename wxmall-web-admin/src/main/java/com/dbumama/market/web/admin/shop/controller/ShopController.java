package com.dbumama.market.web.admin.shop.controller;

import com.dbumama.market.model.Area;
import com.dbumama.market.model.SellerAddr;
import com.dbumama.market.model.Shop;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.authuser.AuthUserConfigService;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.SellerAddrSaveParamDto;
import com.dbumama.market.service.api.order.SellerAddrService;
import com.dbumama.market.service.api.shop.ShopService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;

@RouteBind(path="shop")
public class ShopController extends AdminBaseController<Shop>{

	@BY_NAME
	private SellerAddrService sellerAddrService;
	@BY_NAME
	private AreaService areaService;
	@BY_NAME
	private ShopService shopService;
	@BY_NAME
	private AuthUserConfigService authUserConfigService;
	
	public void index(){
		Shop shop=shopService.findBySeller();
		setAttr("shop", shop);
		setAttr("authConfig", authUserConfigService.getAuthConfig());
		render("/shop/shop_index.html");
	}
	
	public void saveShop(){
		try {
			Shop shop = getModel();
			shop.setSellerId(getSellerId());
			shop.setActive(true);
			shopService.save(shop);
			rendSuccessJson(shop);
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson(e.getMessage());
		}
		
	}
	
	public void sendaddr(){
		SellerAddr sellerAddr = sellerAddrService.getSendAddr(getSellerId());
		if(sellerAddr != null){
			Area area = areaService.findById(sellerAddr.getAreaId());
			setAttr("areaPath", area.getTreePath());
		}
		
		setAttr("sendAddr", sellerAddr);
		render("/shop/send_addr.html");
	}
	
	public void saveaddr(){
		final Long areaId=getParaToLong("areaId");
		final String contactName = getPara("contactName");
		final String city = getPara("city");
		final String country = getPara("country");
		final String province = getPara("province");
		final String addr = getPara("addr");
		final String memo = getPara("memo");
		final String phone = getPara("phone");
		final String sellerCompany = getPara("sellerCompany");
		final String zipCode = getPara("zipCode");
		final Long addrId = getParaToLong("addrId");
		
		SellerAddrSaveParamDto addrSaveParamDto = new SellerAddrSaveParamDto(
				getSellerId(), contactName, city, areaId, province, addr, phone);
		if(StrKit.notBlank(country)) addrSaveParamDto.setCountry(country);
		if(StrKit.notBlank(memo)) addrSaveParamDto.setMemo(memo);
		if(StrKit.notBlank(sellerCompany)) addrSaveParamDto.setSellerCompany(sellerCompany);
		if(StrKit.notBlank(zipCode)) addrSaveParamDto.setZipCode(zipCode);
		if(addrId != null) addrSaveParamDto.setAddrId(addrId);
		if(areaId !=null) addrSaveParamDto.setAreaId(areaId);
		try {
			sellerAddrService.saveOrUpdate(addrSaveParamDto);	
			rendSuccessJson(addrSaveParamDto);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
		
	}
	
	@Override
	protected Class<Shop> getModelClass() {
		return Shop.class;
	}

}
