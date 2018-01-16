package com.dbumama.market.service.api.weipage;

import java.io.IOException;
import java.util.List;

import com.dbumama.market.model.WeiPage;
import com.dbumama.market.model.WeipageCategory;
import com.dbumama.market.service.api.ump.UmpException;
import com.jfinal.plugin.activerecord.Page;

public interface WeiPageService {
	
	public WeiPage getWeiPageById(Long id) throws UmpException;
	
	public String getContentById(Long id)throws UmpException;
	public WeiPage getWeiPageInfoById(Long id) throws UmpException; 
	
	public Page<WeiPage> list(Long sellerId,WeiPage weipage, Integer pageNo, Integer pageSize);
	
	public boolean  update(WeiPage  weipage) throws UmpException;
	public boolean  save(WeiPage  weipage) throws UmpException;
	
	public String getPageHtml(WeiPage entity, boolean publish) throws UmpException , IOException;
	
	void deleteById(Long weipageId);
	
	WeiPage findByStatus(Long sellerId, Integer status);
	
	//weipage category api
	List<WeipageCategory> getSellerWeipageCategory(Long sellerId);
	WeipageCategory findCategoryById(Long categoryId);
	void deleteCategoryById(Long categoryId);
	Page<WeipageCategory> pageCategory(Long sellerId, Integer pageNo, Integer pageSize);
	Long save(WeipageCategory category);
}
