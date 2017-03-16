package com.dbumama.market.service.base;

import java.util.UUID;

import org.apache.log4j.Logger;
import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.CompTicket;
import com.jfinal.kit.PropKit;

/**
 * 公共服务类
 * @author wangjun
 *
 */
public abstract class AbstractServiceImpl {
	protected Logger logger = Logger.getLogger(getClass());

	protected static final String CACHENAME_COMP_TIKET = "comp_tiket_cache";
	protected static final String CACHENAME_COMP_TIKET_KEY = "key_comp_tiket";
	
	protected synchronized String getTradeNo(){return UUID.randomUUID().toString().replaceAll("-", "");}
	
	protected String getImageDomain(){
		return PropKit.get("img_domain");
	}
	
	protected String getCompTicket(){
		CompTicket compTicket = CompTicket.dao.findFirstByCache(CACHENAME_COMP_TIKET, CACHENAME_COMP_TIKET_KEY, "select * from " + CompTicket.table);
		return compTicket == null ? "" : compTicket.getCompVerifyTicket();
	}
	
	protected AuthUser getUsedAuthUser(Long sellerId){
		return AuthUser.dao.findFirst("select * from " + AuthUser.table + " where seller_id=? and is_used=? and active=1", sellerId, true);
	}
	
}
