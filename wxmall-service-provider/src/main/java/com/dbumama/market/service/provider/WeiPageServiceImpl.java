package com.dbumama.market.service.provider;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.ProductGroup;
import com.dbumama.market.model.Promotion;
import com.dbumama.market.model.WeiPage;
import com.dbumama.market.model.WeipageCategory;
import com.dbumama.market.service.api.group.ProductGroupService;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.service.api.ump.PromotionResultDto;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.api.ump.PromotionSetResultDto;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.service.api.weipage.WeiPageService;
import com.dbumama.market.service.api.weipage.component.Ad;
import com.dbumama.market.service.api.weipage.component.Ads;
import com.dbumama.market.service.api.weipage.component.Goods;
import com.dbumama.market.service.api.weipage.component.Line;
import com.dbumama.market.service.api.weipage.component.Product;
import com.dbumama.market.service.api.weipage.component.Search;
import com.dbumama.market.service.api.weipage.component.TextHref;
import com.dbumama.market.service.api.weipage.component.Title;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.dbumama.market.service.utils.FreeMarkUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import freemarker.template.Template;
import freemarker.template.TemplateException;
@Service("weiPageService")
public class WeiPageServiceImpl extends AbstractServiceImpl implements WeiPageService{
	@Autowired
	private ProductService productService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private ProductGroupService productGroupService;
	private static final String notblobsql="select id,seller_id,title,status,created,updated,published,category_id ";
	
	private static final ProductGroup prodGroupdao = new ProductGroup().dao();
	private static final Promotion promotiondao = new Promotion().dao();
	private static final WeiPage weiPagedao = new WeiPage().dao();
	private static final WeipageCategory weiPageCategoryDao = new WeipageCategory().dao();
	private static final AuthUserConfig authConfigDao = new AuthUserConfig().dao();
	
	@Override
	public WeiPage getWeiPageById(Long id) throws UmpException {
		if(id == null) throw new UmpException("获取参数错误");
		WeiPage w = weiPagedao.findById(id);
		return w;
	}
	
	@Override
	public WeiPage getWeiPageInfoById(Long id) throws UmpException {
		if(id == null) throw new UmpException("获取参数错误");
		WeiPage w = weiPagedao.findFirst(notblobsql+" from " + WeiPage.table+" where id=?", id);
		return w;
	}
	
	@Override
	public String getContentById(Long id)throws UmpException {
		if(id == null) throw new UmpException("获取参数错误");
		String  w = Db.queryStr("select content "  +" from " + WeiPage.table + " where id=?", id);
		return w;
	}
	
	@Override
	@Transactional
	public boolean  update(WeiPage  weipage) throws UmpException {
		return weipage.update();
		
	}
	
	@Override
	@Transactional
	public boolean  save(WeiPage  weipage) throws UmpException {
		return weipage.save();
	}
	
	@Override
	public Page<WeiPage> list(Long sellerId,WeiPage weipage, Integer pageNo, Integer pageSize){
		String sql=" from " + WeiPage.table + " where seller_id=? ";
		
		ArrayList<Object> params=new ArrayList<Object>();
		params.add(sellerId);
		
		if(weipage.getStatus()!=null){
		   sql+=" and status=? ";
		   params.add(weipage.getStatus());
		}
		if(weipage.getCategoryId()!=null){
		   sql+=" and category_id=? ";
		   params.add(weipage.getCategoryId());
		}
		sql+=" order by id desc ";
		Page<WeiPage> pages = weiPagedao.paginate(pageNo, pageSize,
				notblobsql, 
				sql, params.toArray());
		
		return pages;
	}

	@Override
	public String getPageHtml(WeiPage entity, boolean publish) throws UmpException, IOException {
		String jsonstr=entity.getContent();
		AuthUserConfig authConfig = authConfigDao.findFirst("select * from " + AuthUserConfig.table);
		StringBuffer buffer=new StringBuffer();  
		if(StrKit.notBlank(jsonstr)){
			List<ProductResultDto> refProducts=null;
			if(StrKit.notBlank(entity.getProducts())){
				//用"#" "#",以便查询
				String productids=entity.getProducts().replace("#","");
				refProducts=productService.getMarketableByIds(entity.getSellerId(),productids);
			}
			
			JSONArray components=JSONArray.parseArray(jsonstr);
			for(int i=0;i<components.size();i++){
				JSONObject com=components.getJSONObject(i);
				String type=com.getString("type");
				Writer out=new StringWriter(2048);
				if(type.equals("title")){
					
					Template template=FreeMarkUtil.getTemplate("title.ftl");
					Title title=JSONObject.parseObject(com.getString("data"), Title.class);
					try {
						template.process(title, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
					//System.out.println(out.toString());
				}else if(type.equals("richtext")){
					Template template=FreeMarkUtil.getTemplate("richtext.ftl");
					Title title=JSONObject.parseObject(com.getString("data"), Title.class);
					try {
						template.process(title, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
					//System.out.println(out.toString());
				}else if(type.equals("search")){
					Template template=FreeMarkUtil.getTemplate("search.ftl");
					Search obj=JSONObject.parseObject(com.getString("data"), Search.class);
					if(publish){
						   obj.setSearchLink(authConfig.getWxDomain() + "/product/list");
				    }else {
					   obj.setSearchLink("javascript:void(0)");
					   
				    }
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
				}else if(type.equals("line")){
					Template template=FreeMarkUtil.getTemplate("line.ftl");
					Line obj=JSONObject.parseObject(com.getString("data"), Line.class);
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
				}else  if(type.equals("text-href")){
					Template template=FreeMarkUtil.getTemplate("text_href.ftl");
					String realLink="javascript:void(0)";
					TextHref obj=JSONObject.parseObject(com.getString("data"), TextHref.class);
					if(StrKit.notBlank(obj.getLink())){
						if(obj.getLink().equals("1")){
							/// realLink=？
						}else if(obj.getLink().equals("2")){
							/// realLink=？
						}
						else if(obj.getLink().equals("9999") && StrKit.notBlank(obj.getOuturl())){
							realLink=obj.getOuturl();
						}
					}	
					obj.setLink(realLink);
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());					
				}else  if(type.equals("goods")){
					Goods obj=JSONObject.parseObject(com.getString("data"), Goods.class);
					if(obj.getGoodsList()!=null && obj.getGoodsList().size()>0){
						obj.setProductList(new ArrayList<Product>());
						for(Long goodsid:obj.getGoodsList()){
							ProductResultDto finded=null;
							if(refProducts!=null){
								for(ProductResultDto p:refProducts ){
									if(goodsid.longValue()==p.getId().longValue()){
										finded=p;
										break;
									}
								}
							}	
						   if(finded!=null){
							   Product product=new Product();
							   product.setImg(finded.getImg());
							   product.setName(finded.getName());
							   product.setPrice(finded.getPrice());
							   product.setIntroduction(finded.getIntroduction());
							   if(publish){
								   product.setDetailLink(authConfig.getWxDomain() + "/product/detail?id="+finded.getId());

							   }else {
								   product.setBuyLink("javascript:void(0)");
								   product.setDetailLink("javascript:void(0)");
							   }
							   
							   obj.getProductList().add(product);
						   }
						}
						
						//--
						Template template=FreeMarkUtil.getTemplate("goods_"+obj.getListStyle()+".ftl");
						try {
							template.process(obj, out);
						} catch (TemplateException e) {
							throw new UmpException(e.getMessage());
						}
						buffer.append(out.toString());
					}
					
				}else if(type.equals("goods-list")){
					Goods obj=JSONObject.parseObject(com.getString("data"), Goods.class);
					JSONObject object=JSONObject.parseObject(com.getString("data"));
					ProductGroup productGroup=prodGroupdao.findById(object.getString("goodsGroupId"));
					obj.setProductList(new ArrayList<Product>());
					if(productGroup!=null){
						List<ProductResultDto> resultDto=productGroupService.getGroupProduct(productGroup.getId());
						for (ProductResultDto finded : resultDto) {
							 Product product=new Product();
							   product.setImg(finded.getImg());
							   product.setName(finded.getName());
							   product.setPrice(finded.getPrice());
							   product.setIntroduction(finded.getIntroduction());
							   if(publish){
								   product.setDetailLink(authConfig.getWxDomain() + "/product/detail?id="+finded.getId());
							   }else {
								   product.setBuyLink("javascript:void(0)");
								   product.setDetailLink("javascript:void(0)");
							   }
							   
							   obj.getProductList().add(product);
						}
					}
					Template template=FreeMarkUtil.getTemplate("goods_"+obj.getListStyle()+".ftl");
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
				}else if(type.equals("ad")){
					Ads obj=new Ads();
					obj.setAdList(new ArrayList<Ad>());
					JSONObject object=JSONObject.parseObject(com.getString("data"));
					JSONArray jarr = JSONArray.parseArray(object.getString("imgList"));	
					 for (Iterator<?> iterator = jarr.iterator(); iterator.hasNext();) { 
				          JSONObject job = (JSONObject) iterator.next(); 
				          Ad ad=new Ad();
				          ad.setId(job.getString("id"));
				          ad.setImgPath(job.getString("imgPath"));
				          ad.setTitle(job.getString("title"));
				          ad.setAdHref(job.getString("outhref"));
				          obj.getAdList().add(ad);
					 }
					
					Template template=FreeMarkUtil.getTemplate("ad.ftl");
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
				}else if(type.equals("img-href")){
					Ads obj=new Ads();
					obj.setAdList(new ArrayList<Ad>());
					JSONObject object=JSONObject.parseObject(com.getString("data"));
					//Ad ad=JSONObject.parseObject(com.getString("data"), Ad.class);
					JSONArray jarr = JSONArray.parseArray(object.getString("imgList"));	
					 for (Iterator<?> iterator = jarr.iterator(); iterator.hasNext();) { 
				          JSONObject job = (JSONObject) iterator.next(); 
				          Ad ad=new Ad();
				          ad.setId(job.getString("id"));
				          ad.setImgPath(job.getString("imgPath"));
				          ad.setTitle(job.getString("title"));
				          ad.setAdHref(job.getString("outhref"));
				          obj.getAdList().add(ad);
					 }
					
					Template template=FreeMarkUtil.getTemplate("img_href.ftl");
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
				}else if(type.equals("activity")){
					Goods obj=JSONObject.parseObject(com.getString("data"), Goods.class);
					if(obj.getGoodsList()!=null && obj.getGoodsList().size()>0){
						obj.setProductList(new ArrayList<Product>());
					Promotion promotion=promotiondao.findById(obj.getActivityId());
					PromotionResultDto dto=promotionService.getPromotionInfo(promotion);
					List<PromotionSetResultDto> promotionSets=dto.getPromotionSets();
					for(Long goodsid:obj.getGoodsList()){
						PromotionSetResultDto finded=null;
					if(refProducts!=null){
					for (PromotionSetResultDto promotionSetResultDto : promotionSets) {
							if(goodsid.longValue()==promotionSetResultDto.getProductId().longValue()){
								finded=promotionSetResultDto;
								break;
							}
						}
					}
					 if(finded!=null){
						   Product product=new Product();
						   product.setImg(finded.getProductImg());
						   product.setName(finded.getProductName());
						   product.setPrice(finded.getProductPrice());
						   product.setZekou(finded.getZhekou().toString());
						   product.setZekouPrice(finded.getPromotionValue().toString());
						   product.setDetailLink(authConfig.getWxDomain() + "/product/detail?id="+finded.getProductId());
						   Calendar ca=Calendar.getInstance();
						   ca.setTime(promotion.getEndDate());
						   product.setExpiresIn((ca.getTime().getTime() - new Date().getTime())/1000);
						   obj.getProductList().add(product);
					 }
					}
				 }
					Template template=FreeMarkUtil.getTemplate("activity.ftl");
					try {
						template.process(obj, out);
					} catch (TemplateException e) {
						throw new UmpException(e.getMessage());
					}
					buffer.append(out.toString());
				}
					
				
				out.close();
			}
		}
		return buffer.toString();
	}

	@Override
	public void deleteById(Long weipageId) {
		weiPagedao.deleteById(weipageId);
	}

	@Override
	public WeiPage findByStatus(Long sellerId, Integer status) {
		return weiPagedao.findFirst("select * from "+WeiPage.table+" where seller_id=? and status=? ", sellerId, status);
	}

	@Override
	public List<WeipageCategory> getSellerWeipageCategory(Long sellerId) {
	    return weiPageCategoryDao.find("select * from "+WeipageCategory.table +" where seller_id=? ", sellerId);
	}

	@Override
	public WeipageCategory findCategoryById(Long categoryId) {
		return weiPageCategoryDao.findById(categoryId);
	}

	@Override
	public void deleteCategoryById(Long categoryId) {
		weiPageCategoryDao.deleteById(categoryId);		
	}

	@Override
	public Page<WeipageCategory> pageCategory(Long sellerId, Integer pageNo, Integer pageSize) {
		QueryHelper helper = new QueryHelper("select * ", "from " + WeipageCategory.table);
		helper.addWhere("seller_id", sellerId).addOrderBy("asc", "orders").build();
		
		return weiPageCategoryDao.paginate(pageNo, pageSize, 
				helper.getSelect(), helper.getSqlExceptSelect(), helper.getParams());
	}

	@Override
	public Long save(WeipageCategory category) {
		if(category.getId() == null){
			category.save();
		}else{
			category.update();
		}
		return category.getId();
	}

}
