package com.dbumama.market.service.api.qiandao;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.common.AbstractPageParamDto;

/**
 * wjun_java@163.com
 * 2016年7月8日
 */
@SuppressWarnings("serial")
public class QiandaoParamDto extends AbstractPageParamDto {

	public QiandaoParamDto(int pageNo) {
		super(pageNo);
	}

	private BuyerUser buyer;
	
	private SellerUser seller;

	public BuyerUser getBuyer() {
		return buyer;
	}

	public void setBuyer(BuyerUser buyer) {
		this.buyer = buyer;
	}

	public SellerUser getSeller() {
		return seller;
	}

	public void setSeller(SellerUser seller) {
		this.seller = seller;
	}
	
}
