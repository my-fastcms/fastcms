package com.dbumama.market.web.mobile.product.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.model.ProductReview;
import com.dbumama.market.service.api.cart.CartService;
import com.dbumama.market.service.api.product.ProductCategoryService;
import com.dbumama.market.service.api.product.ProductDetailResultDto;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductMobileParamDto;
import com.dbumama.market.service.api.product.ProductMobileResultDto;
import com.dbumama.market.service.api.product.ProductParamDto;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.service.api.product.ProductSpecPriceResultDto;
import com.dbumama.market.service.api.product.ProductSpecificationService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


/**
 * Created by dextrys on 2016/1/8.
 */
@RouteBind(path="product")
public class ProductController extends BaseMobileController {
	
	@BY_NAME
	CartService cartService;
	@BY_NAME
	private ProductService productService;
	@BY_NAME
	private ProductCategoryService productCategoryService;
	@BY_NAME
	private ProductSpecificationService productSpecificationService;
	public void index(){
		List<ProductCategory> productCategorys = ProductCategory.dao.findRoots(null);
		List<ProductCategory> validCategorys = new ArrayList<ProductCategory>();
		for(ProductCategory pcate : productCategorys){
			List<Product> products = Product.dao.find("select * from " + Product.table + " where product_category_id=? and active=1 and is_marketable=1 ", pcate.getId());
			if(products != null && products.size()>0){
				validCategorys.add(pcate);
			}
		}
		setAttr("productCategory", validCategorys);
		if(getParaToLong("categId") != null){
			setAttr("categId", getParaToLong("categId"));
		}
		render("/product/index.html");
	}
	
	public void list(){
		Long productCategoryId = getParaToLong("categId");
		BigDecimal startPrice = new BigDecimal(getPara("startPrice","0"));
		BigDecimal endPrice = new BigDecimal(getPara("endPrice","0"));
		ProductMobileParamDto mobileParamDto = new ProductMobileParamDto(getSellerId(), getPageNo());
		mobileParamDto.setCategId(productCategoryId);
		mobileParamDto.setStartPrice(startPrice);
		mobileParamDto.setEndPrice(endPrice);
		List<ProductMobileResultDto> productResultDtos = productService.findProducts4Mobile(mobileParamDto);
		rendSuccessJson(productResultDtos);
	}

    public void detail() {
    	ProductParamDto productParamDto = new ProductParamDto(getSellerId(), getParaToLong("id"));
    	if(getAuthUser(getRequest()) != null){
    		productParamDto.setAppId(getAuthUser(getRequest()).getAppId());
    	}
    	try {
    		ProductDetailResultDto productDetail = productService.getMobieDetail(productParamDto);
    		if(StrKit.notBlank(productDetail.getWirlessUrl())){
    			setAttr("wirlessUrl", productDetail.getWirlessUrl());    			
    		}
    		setAttr("product", productDetail.getProduct());
    		setAttr("images", productDetail.getImageList());
    		//商品规格
    		if(productDetail.getSpecifications() != null){
    			setAttr("specifications", productDetail.getSpecifications());	
    		}
    		//商品限时打折价格
    		if(productDetail.getPromotionInfo() != null){
    			setAttr("promotion", productDetail.getPromotionInfo());
    		}
    		//商品满减送
    		if(productDetail.getFullCutInfo()!= null){
    			setAttr("fullCut", productDetail.getFullCutInfo());
    		}
    		
		} catch (ProductException e) {
			e.printStackTrace();
		}
    	
        List<Record> reviews = Db.find("select p.*, b.nickname, b.headimgurl from " + ProductReview.table + " p"
        		+ " left join " + BuyerUser.table + " b on p.buyer_id=b.id"
        		+ " where product_id=? order by p.created desc ", productParamDto.getProductId());
        setAttr("reviews", reviews);
        setAttr("cartCount", cartService.getCartItemCountByBuyer(getBuyerId()));
        render("/product/detail.html");
        
    }
    
    public void stocks(){
    	String productId=getPara("productId");
    	try {
    		HashMap<String, ProductSpecPriceResultDto> data = productService.getProductSpecPrice(Long.valueOf(productId));
    		rendSuccessJson(data);
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
    }
    
    public void getPromotionProductList(){
    	ProductMobileParamDto mobileParamDto = new ProductMobileParamDto(getSellerId(), getPageNo());
    	try {
    		Page<ProductMobileResultDto> products = productService.getMobilePromotionProduct(mobileParamDto);
    		rendSuccessJson(products.getList());
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
    }
    
    /**
     * 获取打折商品列表
     */
    public void getPromotionProduct(){
    	render("/product/prod_promotion_index.html");
    }
    
}
