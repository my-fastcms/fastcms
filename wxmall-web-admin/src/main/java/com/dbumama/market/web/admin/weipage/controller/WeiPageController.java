package com.dbumama.market.web.admin.weipage.controller;


import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.WeiPage;
import com.dbumama.market.model.WeipageCategory;
import com.dbumama.market.service.api.group.ProductGroupService;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.service.api.weipage.WeiPageService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
/**
 * 微页面
 * @author hjf
 *
 */
@RouteBind(path="weipage", viewPath="weipage")
public class WeiPageController extends AdminBaseController<WeiPage> {
	@BY_NAME
	private WeiPageService weiPageService; 
	
	@BY_NAME
	private ProductService productService;
	
	@BY_NAME
	private PromotionService promotionService;
	
	@BY_NAME
	private ProductGroupService productGroupService;
	public void index(){
		setAttrs();
		render("w_index.html");
	}
	
	public void add(){
		setAttrs();
		Long id=getParaToLong("id");
		String action="add";
		if(id!=null){
		   WeiPage entity=weiPageService.getWeiPageById(id);
		   setAttr("entity", entity);
		   action="update";
		}
		 setAttr("action", action);
		render("add.html");
	}
	
	private void setAttrs(){
	    List<WeipageCategory> categoryList=weiPageService.getSellerWeipageCategory(getSellerId());
	    setAttr("categoryList", categoryList);
	}
	
	
	public void list(){
		try {
			WeiPage weipage=new WeiPage();
			weipage.setCategoryId(this.getParaToLong("category_id"));
			weipage.setStatus(this.getParaToInt("status"));
			Page<WeiPage> pages = weiPageService.list(getSellerId(), weipage, getPageNo(), getPageSize());
			rendSuccessJson(pages);//, 
		} catch (UmpException e) {
			rendFailedJson(e.getMessage());
		}
	}
	/**
	 * 显示设计页面
	 */
	public void edit(){
		WeiPage entity=weiPageService.getWeiPageInfoById(getParaToLong("id"));
		if(entity==null){
			throw new UmpException("页面不存在！");
		}
		setAttr("weiPage", entity);
		this.render("edit.html");
	}
	
	public void getContentById(){
		try {
			WeiPage entity=weiPageService.getWeiPageById(getParaToLong("id"));
			if(entity.getSellerId().longValue()!=getSellerId().longValue()){
				throw new UmpException("非法操作！");
			}
			String content=entity.getContent();
			if(StrKit.isBlank(content)){
				content="[]";  //empty array
			}
			List<ProductResultDto> products=null;
			if(StrKit.notBlank(entity.getProducts())){
				//用"#" "#",以便查询
				String productids=entity.getProducts().replace("#","");
				 products=productService.getMarketableByIds(getSellerId(),productids);
			}
			DesignData data =new DesignData();
			data.setContent(content);
			data.setProducts(products);
			
			rendSuccessJson(data);
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("读取失败！"+e.getMessage());
		}
	}
	
	
	
	/**
	 *  保存“设计”
	 */
	public void save(){
		WeiPage weipage = null;
		try {
			
			weipage = getModel();
			WeiPage entity=weiPageService.getWeiPageById(weipage.getId());
			if(entity.getSellerId().longValue()!=getSellerId().longValue()){
				throw new UmpException("非法操作！");
			}
			if(StrKit.notBlank(weipage.getContent())){
				JSONArray components=JSONArray.parseArray(weipage.getContent());
				
				HashSet<Long> goodsidset=new HashSet<Long>();
				for(int i=0;i<components.size();i++){
					JSONObject com=components.getJSONObject(i);
					String type=com.getString("type");
					//如果是商品控件，记录涉及的商品，以便当商品信息更新时，同步重新生成、更新 HTML
					if(type.equals("goods")||type.equals("activity")){
						
						JSONObject data=com.getJSONObject("data");
						JSONArray goodses=data.getJSONArray("goodsList");
						if(goodses!=null){
						    for(int s=0;s<goodses.size();s++){
						    	Long goodid=goodses.getLong(s);
						    	goodsidset.add(goodid);
						    }
						}
						
					}
						
				}
				
				String goodsstr="";
				for(Long goodid:goodsidset){
					goodsstr+="#"+goodid+"#,";//用"#" "#",以便查询
				}
				if(goodsstr.endsWith(",")){
					goodsstr=goodsstr.substring(0,goodsstr.length()-1);
				}
				
				weipage.setProducts(goodsstr);  //set
				
				
			}
			
			weiPageService.update(weipage);
			rendSuccessJson("保存成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("保存失败！"+e.getMessage());
		}
	}
	/**
	 * 新建、修改页面
	 */
	public void create(){
		WeiPage weipage = null;
		try {
			weipage = getModel();
			if(weipage.getId()!=null){
				
				WeiPage entity=weiPageService.getWeiPageById(weipage.getId());
				if(entity.getSellerId().longValue()!=getSellerId().longValue()){
					throw new UmpException("非法操作！");
				}

				Date now=new Date();
				weipage.setUpdated(now);
				weiPageService.update(weipage);
			}else {
				weipage.setSellerId(getSellerId());
				weipage.setStatus(0);
				
				Date now=new Date();
				weipage.setCreated(now);
				weipage.setUpdated(now);
				weiPageService.save(weipage);
			}
			rendSuccessJson("操作成功！");
		} catch (Exception e) {
			//e.printStackTrace();
			rendFailedJson("保存失败！");
		}
	}
	
	/**
	 * 删除页面
	 */
	public void del(){
		Long id = getParaToLong("id");
		WeiPage entity=weiPageService.getWeiPageById(id);
		if(entity.getSellerId().longValue()!=getSellerId().longValue()){
			throw new UmpException("非法操作！");
		}
		weiPageService.deleteById(id);
		rendSuccessJson("操作成功！");
	}
	
	@Override
	protected Class<WeiPage> getModelClass() {
		return WeiPage.class;
	}
	
	
	//==================================================================
	/**
	 * 预览
	 */
	public void preview(){
		Long id = getParaToLong("id");
		WeiPage entity=weiPageService.getWeiPageInfoById(id);
		setAttr("entity", entity);
		
		render("preview.html");
	}
	
	public void getPreviewHtml(){
		Long id = getParaToLong("id");
		
		WeiPage entity=weiPageService.getWeiPageById(id);
		if(entity.getSellerId().longValue()!=getSellerId().longValue()){
			throw new UmpException("非法操作！");
		}
		try{
			String html=getPageHtml(entity,false);
			rendSuccessJson(html); 
		}catch(Exception ex){
			ex.printStackTrace();
			rendFailedJson("生成预览失败！");
		}
		
	}
	
	
	
	/**
	 * 发布  
	 */
	public void publish(){
        Long id = getParaToLong("id");
        WeiPage entity=weiPageService.getWeiPageById(id);
		if(entity.getSellerId().longValue()!=getSellerId().longValue()){
			throw new UmpException("非法操作！");
		}
		String jsonstr=entity.getContent();
		if(StrKit.isBlank(jsonstr)){
			rendFailedJson("微页面是空的，发布失败！");
			return;
		}
		try{
				//String html=getPageHtml(entity,true);
				
				//进一处理，放在文件或数据表上？？？？？？？？？？？？？？
				
				//改变状态
				Date now=new Date();
				entity.setUpdated(now);
				entity.setStatus(1);
				entity.setPublished(now);
				weiPageService.update(entity);
				rendSuccessJson(true);
		}catch(Exception ex){
			rendFailedJson("发布失败！");
		}
	}
	
	public void outPublish(){
        Long id = getParaToLong("id");
        WeiPage entity=weiPageService.getWeiPageById(id);
		if(entity.getSellerId().longValue()!=getSellerId().longValue()){
			throw new UmpException("非法操作！");
		}
		String jsonstr=entity.getContent();
		if(StrKit.isBlank(jsonstr)){
			rendFailedJson("微页面是空的，取消发布失败！");
			return;
		}
		try{
				Date now=new Date();
				entity.setUpdated(now);
				entity.setStatus(0);
				entity.setPublished(now);
				weiPageService.update(entity);
				rendSuccessJson(true);
		}catch(Exception ex){
			rendFailedJson("取消发布失败！");
		}
	}
	
	/**
	 * 
	 * @param jsonstr
	 * @param publish  是发布操作？ （或预览）
	 * @return
	 */
	private String getPageHtml(WeiPage entity, boolean publish) throws Exception{

		return weiPageService.getPageHtml(entity, publish);
			
	}

}
