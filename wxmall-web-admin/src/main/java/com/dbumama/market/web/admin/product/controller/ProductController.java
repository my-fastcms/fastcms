package com.dbumama.market.web.admin.product.controller;

import java.math.BigDecimal;
import java.util.List;

import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.service.api.delivery.DeliveryTemplateService;
import com.dbumama.market.service.api.product.ProductCategoryService;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductParamDto;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.service.api.product.ProductSubmitParamDto;
import com.dbumama.market.service.api.specification.SpecificationParamDto;
import com.dbumama.market.service.api.specification.SpecificationService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;
@RouteBind(path = "product", viewPath = "product")
public class ProductController extends AdminBaseController<Product> {
	@BY_NAME
	private ProductCategoryService productCategoryService;
	@BY_NAME
	private SpecificationService specificationService;
	@BY_NAME
	private ProductService productService;
	@BY_NAME
	private DeliveryTemplateService deliveryTemplateService;
	
	public void index() {
		render("pd_index.html");
	}
	
	public void warehouseIndex() {
		render("pd_warehouse.html");
	}
	
	public void warehouse(){
		ProductParamDto productParamDto = new ProductParamDto(getSellerId(), getPageNo());
		productParamDto.setIsMarketable(0);
		try {
			rendSuccessJson(productService.list(productParamDto));			
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void list(){
		ProductParamDto productParamDto = new ProductParamDto(getSellerId(), getPageNo());
		productParamDto.setPageSize(getPageSize());
		productParamDto.setIsMarketable(1);
		productParamDto.setProductIds(getPara("productIds"));
		try {
			rendSuccessJson(productService.list(productParamDto));			
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
	}

	public void add() {
		SpecificationParamDto specificationParamDto = new SpecificationParamDto();
		specificationParamDto.setSellerId(getSellerId());
		List<ProductCategory> productCategory = productCategoryService.list();
		setAttr("productCategory", productCategory);
		setAttr("specificationResultDto", specificationService.findAll(specificationParamDto));
		render("pd_edit.html");
	}
	
	public void edit() {
		if(StrKit.notBlank(getPara(0))){
			SpecificationParamDto specificationParamDto = new SpecificationParamDto();
			specificationParamDto.setSellerId(getSellerId());
			ProductParamDto productParamDto=new ProductParamDto(getSellerId());
			productParamDto.setProductId(new Long(getPara(0)));
			Product product=productService.findById(getParaToLong(0));
			if(product != null){
				setAttr("imgUrl", product.getImage());
				product.setImage(getImageDomain()+product.getImage());
				setAttr("deliveryTemplate", deliveryTemplateService.findById(product.getDeliveryTemplateId()));	
				setAttr("product", product);
				List<ProductCategory> productCategory = productCategoryService.list();
				setAttr("productCategory", productCategory);
				setAttr("specificationResultDto", specificationService.findAll(specificationParamDto));
				setAttr("productAllResultDto",productService.findAllResultDto(productParamDto));
				setAttr("categoryName",productCategoryService.findById(product.getProductCategoryId()).getName());
			}
		}
		render("pd_edit.html");
	}
	
	/**
	 * 保存
	 * 
	 */
//	@Before(ProductValidator.class)
	public void save() {
		Product product=null;
		try {
			product = getModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String priceAndStock=getPara("stocks");
		String[] imgList=getParaValues("imgList");
		Long productCategoryId = getParaToLong("productCategoryId");
		Long[] specificationIds = getParaValuesToLong("specificationIds");
		String[] specificationValues = getParaValues("specificationValues");
		Boolean isMarketable = getParaToBoolean("isMarketable", false);
		Boolean isUnifiedSpec = getParaToBoolean("is_unified_spec", false);
		String price=getPara("price");
		String stock=getPara("stock");
		String marketPrice=getPara("market_price");
		Integer deliveryType=getParaToInt("delivery_type", 0);
		BigDecimal deliveryFees=null;
		if(!StrKit.isBlank(getPara("delivery_fees"))){
	     deliveryFees=new BigDecimal(getPara("delivery_fees"));
		}
		BigDecimal deliveryWeight=null;
		if(!StrKit.isBlank(getPara("delivery_weight"))){
			deliveryWeight=new BigDecimal(getPara("delivery_weight"));
		}
		Long deliveryTemplateId=getParaToLong("delivery_template_id");
		ProductSubmitParamDto productParamDto = new ProductSubmitParamDto(getSellerId(), imgList, productCategoryId, getPara("name"), getPara("image"), getPara("introduction"));
		productParamDto.setImages(imgList);
		productParamDto.setSpecificationIds(specificationIds);
		productParamDto.setSpecificationValues(specificationValues);
		productParamDto.setIsMarketable(isMarketable);
		productParamDto.setPriceAndStock(priceAndStock);
		productParamDto.setPrice(price);
		productParamDto.setMarketPrice(marketPrice);
		productParamDto.setStock(stock);
		productParamDto.setIsUnifiedSpec(isUnifiedSpec);
		productParamDto.setDeliveryType(deliveryType);
        if(0==deliveryType){
        	productParamDto.setDeliveryFees(deliveryFees);
        	productParamDto.setDeliveryTemplateId(null);
		}else{
			productParamDto.setDeliveryFees(null);
        	productParamDto.setDeliveryTemplateId(deliveryTemplateId);
		}
        if(deliveryTemplateId!=null){
			DeliveryTemplate dt=deliveryTemplateService.findById(deliveryTemplateId);
			if(deliveryType!=1||dt.getValuationType()!=2){
				productParamDto.setDeliveryWeight(null);	
			}else if(deliveryType==1&&dt.getValuationType()==2&&!isUnifiedSpec){
				productParamDto.setDeliveryWeight(null);
			}else{
				productParamDto.setDeliveryWeight(deliveryWeight);
			}
		}else{
			productParamDto.setDeliveryWeight(null);
		}
		if(product.getId()==null){
			try {
				productService.save(productParamDto);
				redirect("/product/index");
			} catch (ProductException e) {
				setAttr("error", e.getMessage());
				render("/product/pd_error.html");
			}
			
		}else{
			try {
				productService.update(productParamDto,product.getId());
				redirect("/product/index");
			} catch (ProductException e) {
				setAttr("error", e.getMessage());
				render("/product/pd_error.html");
			}
		}
	}
	/**
	 * 下架该商品
	 */
   public void offShelve(){
	   String ids = getPara("ids");
	   for(String id : ids.split("-")){
		   Product product=productService.findById(Long.valueOf(id));
		   product.setIsMarketable(false);
		   product.update();
	   }
	   rendSuccessJson("操作成功！");
   }
   
   /**
	 * 上架该商品
	 */
   public void shelve(){
	   String ids = getPara("ids");
	   for(String id : ids.split("-")){
		   Product product=productService.findById(Long.valueOf(id));
		   product.setIsMarketable(true);
		   product.update();
	   }
	   rendSuccessJson("操作成功！");
   }
	
	@Override
	protected Class<Product> getModelClass() {
		return Product.class;
	}

}
