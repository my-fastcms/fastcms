package com.dbumama.market.service.provider;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.SellerAddr;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.SellerAddrSaveParamDto;
import com.dbumama.market.service.api.order.SellerAddrService;
import com.jfinal.kit.StrKit;

@Service("sellerAddrService")
public class SellerAddrServiceImpl implements SellerAddrService{

	@Override
	public SellerAddr getSendAddr(Long sellerId) throws OrderException {
		if(sellerId == null) throw new OrderException("获取发货地址接口参数错误");
		return SellerAddr.dao.findFirst(" select * from " + SellerAddr.table + " where seller_id=?", sellerId);
	}

	@Override
	public SellerAddr saveOrUpdate(SellerAddrSaveParamDto addrSaveParamDto) throws OrderException {
		if(addrSaveParamDto == null || addrSaveParamDto.getSellerId()==null
				|| StrKit.isBlank(addrSaveParamDto.getAddr())
				|| StrKit.isBlank(addrSaveParamDto.getCity())
				|| StrKit.isBlank(addrSaveParamDto.getContactName())
				|| StrKit.isBlank(addrSaveParamDto.getPhone())
				|| StrKit.isBlank(addrSaveParamDto.getProvince())
				|| StrKit.isBlank(addrSaveParamDto.getAreaId().toString()))
			throw new OrderException("更新发货地址接口参数错误");
		SellerAddr sellerAddr = null;
		if(addrSaveParamDto.getAddrId() != null){
			sellerAddr = SellerAddr.dao.findById(addrSaveParamDto.getAddrId());
			sellerAddr.setUpdated(new Date());
		}else{
			sellerAddr = new SellerAddr();
			sellerAddr.setSellerId(addrSaveParamDto.getSellerId());
			sellerAddr.setCreated(new Date());
			sellerAddr.setUpdated(new Date());
		}
		
		sellerAddr.setActive(1);
		sellerAddr.setAddr(addrSaveParamDto.getAddr());
		sellerAddr.setCity(addrSaveParamDto.getCity());
		sellerAddr.setContactName(addrSaveParamDto.getContactName());
		sellerAddr.setCountry(addrSaveParamDto.getCountry());
		sellerAddr.setMemo(addrSaveParamDto.getMemo());
		sellerAddr.setPhone(addrSaveParamDto.getPhone());
		sellerAddr.setProvince(addrSaveParamDto.getProvince());
		sellerAddr.setSellerCompany(addrSaveParamDto.getSellerCompany());
		sellerAddr.setZipCode(addrSaveParamDto.getZipCode());
		sellerAddr.setAreaId(addrSaveParamDto.getAreaId());
		
		if(sellerAddr.getId() == null){
			sellerAddr.save();
		}else{
			sellerAddr.update();
		}
		
		return sellerAddr;
	}

}
