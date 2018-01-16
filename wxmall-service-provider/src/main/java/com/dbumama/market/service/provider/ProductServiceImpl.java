package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductImage;
import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.ProductSpecification;
import com.dbumama.market.model.ProductSpecificationValue;
import com.dbumama.market.model.Promotion;
import com.dbumama.market.model.PromotionSet;
import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.product.ImagepathResultDto;
import com.dbumama.market.service.api.product.ProductAllResultDto;
import com.dbumama.market.service.api.product.ProductDetailResultDto;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductMobileParamDto;
import com.dbumama.market.service.api.product.ProductMobileResultDto;
import com.dbumama.market.service.api.product.ProductParamDto;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.service.api.product.ProductSpecPriceResultDto;
import com.dbumama.market.service.api.product.ProductSubmitParamDto;
import com.dbumama.market.service.api.serinum.SerinumService;
import com.dbumama.market.service.api.specification.SpecificationResultDto;
import com.dbumama.market.service.api.ump.ProdPromotionResultDto;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
@Service("productService")
public class ProductServiceImpl extends AbstractServiceImpl implements ProductService{
	
	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";
	
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private SerinumService serinumService;
	
	private static final Product productDao = new Product().dao();
	private static final ProductImage prodImgdao = new ProductImage().dao();
	private static final ProductSpecification prodSpecidao = new ProductSpecification().dao();
	private static final ProductSpecificationValue prodSpecValuedao = new ProductSpecificationValue().dao();
	private static final ProductSpecItem prodSpecItemdao = new ProductSpecItem().dao();
	private static final AuthUserConfig authConfigDao = new AuthUserConfig().dao();
	private static final SpecificationValue specValueDao = new SpecificationValue().dao();
	private static final Specification specDao = new Specification().dao();
	
	@Override
	@Transactional(rollbackFor = ProductException.class)
	public void save(ProductSubmitParamDto productSubmitParamDto) throws ProductException{
		if(productSubmitParamDto == null 
				|| productSubmitParamDto.getSellerId() == null
				|| productSubmitParamDto.getProductCategoryId() == null
				|| productSubmitParamDto.getImages() == null)
			throw new ProductException("保存商品出错,请检查参数");
		
		Product product = new Product();
		product.setSellerId(productSubmitParamDto.getSellerId());
		product.setName(productSubmitParamDto.getName());
		product.setImage(productSubmitParamDto.getMainImage());
		product.setIntroduction(productSubmitParamDto.getIntroduction());
		product.setProductCategoryId(productSubmitParamDto.getProductCategoryId());
		product.setSn(serinumService.getProductSn());
		product.setSales(new Long(0));
		product.setIsList(true);
		product.setActive(1);
		product.setCreated(new Date());
		product.setUpdated(new Date());
		product.setIsMarketable(productSubmitParamDto.getIsMarketable());
		product.setIsUnifiedSpec(productSubmitParamDto.getIsUnifiedSpec());
		product.setDeliveryType(productSubmitParamDto.getDeliveryType());
		product.setDeliveryFees(productSubmitParamDto.getDeliveryFees());
		product.setDeliveryTemplateId(productSubmitParamDto.getDeliveryTemplateId());
		product.setDeliveryWeight(productSubmitParamDto.getDeliveryWeight());
		if(StrKit.isBlank(productSubmitParamDto.getMarketPrice())){
			product.setMarketPrice(null);
		}else{
			product.setMarketPrice(new BigDecimal(productSubmitParamDto.getMarketPrice()));
		}
		if(productSubmitParamDto.getIsUnifiedSpec()){
			//说明统一规格
			product.setPrice(productSubmitParamDto.getPrice());
			product.setStock(StrKit.notBlank(productSubmitParamDto.getStock()) ? Integer.valueOf(productSubmitParamDto.getStock()) : 0);
		}else{
			//说明是多规格
			product.setPrice(getSpvPrice(productSubmitParamDto));
			product.setStock(getTotalStock(productSubmitParamDto));
		}
		
		try {
			product.save();
		} catch (Exception e) {
			throw new ProductException(e.getMessage());
		}
		
		//保存商品图片
		List<ProductImage> productImages = new ArrayList<ProductImage>();
		for (int i = 0; i < productSubmitParamDto.getImages().length; i++) {
			ProductImage productImage=new ProductImage();
			productImage.setProductId(product.getId());
			productImage.setLarge(productSubmitParamDto.getImages()[i]);
			productImage.setOrders(i+1);
			productImage.setActive(1);
			productImage.setCreated(new Date());
			productImage.setUpdated(new Date());
			productImages.add(productImage);
		}
		
		try {
			Db.batchSave(productImages, productImages.size());
		} catch (Exception e) {
			throw new ProductException(e.getMessage());
		}
		
		//保存选择的规格跟规格值
		if(productSubmitParamDto.getSpecificationIds() != null && productSubmitParamDto.getSpecificationIds().length>0 
				&& productSubmitParamDto.getSpecificationValues() != null && productSubmitParamDto.getSpecificationValues().length>0){
			List<ProductSpecification> specifications = new ArrayList<ProductSpecification>();
			List<ProductSpecificationValue> specificationValues = new ArrayList<ProductSpecificationValue>();
			for (int i = 0; i < productSubmitParamDto.getSpecificationIds().length; i++) {
				ProductSpecification ps=new ProductSpecification();
				ps.setProductId(product.getId());
				ps.setSpecificationId(productSubmitParamDto.getSpecificationIds()[i]);
				ps.setActive(1);
				ps.setCreated(new Date());
				ps.setUpdated(new Date());
				specifications.add(ps);
			}
			try {
				Db.batchSave(specifications, specifications.size());
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
			for (int i = 0; i < productSubmitParamDto.getSpecificationValues().length; i++) {
				ProductSpecificationValue psValue=new ProductSpecificationValue();
				String[] psname=productSubmitParamDto.getSpecificationValues()[i].split("_");
				psValue.setSpecificationId(new Long(psname[1]));
				psValue.setSpecificationValueId(new Long(psname[0]));
				psValue.setProductId(product.getId());
				psValue.setActive(1);
				psValue.setCreated(new Date());
				psValue.setUpdated(new Date());
				specificationValues.add(psValue);
			}
			
			try {
				Db.batchSave(specificationValues, specificationValues.size());
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
		}
		
		//选择规格的价格与库存
		if(StrKit.notBlank(productSubmitParamDto.getPriceAndStock())){
			List<ProductSpecItem> stocks = new ArrayList<ProductSpecItem>();
			String stock = productSubmitParamDto.getPriceAndStock();
			JSONArray jarr = JSONArray.parseArray(stock); 
	        for (Iterator<?> iterator = jarr.iterator(); iterator.hasNext();) { 
	          JSONObject job = (JSONObject) iterator.next(); 
	          ProductSpecItem stockss=new ProductSpecItem();
	          stockss.setProductId(product.getId());
	          if(job.get("price") == null) throw new ProductException("多规格商品有价格为空，请检查");
	          BigDecimal bprice = new BigDecimal(job.get("price").toString());
	          stockss.setPrice(bprice);
	          if(job.get("specificationValue") !=null){
		          stockss.setSpecificationValue(job.get("specificationValue").toString());	        	  
	          }
	          if(StrKit.notBlank(job.getString("weight"))){
	        	  BigDecimal bweight = new BigDecimal(job.get("weight").toString());
		          stockss.setWeight(bweight); 
	          }
	          stockss.setStock(job.get("stock") == null || "".equals(job.getString("stock")) ? 0 : Integer.parseInt(job.get("stock").toString()));
	          stockss.setActive(1);
	          stockss.setCreated(new Date());
	          stockss.setUpdated(new Date());
	          stocks.add(stockss);
	        }
	        try {
				Db.batchSave(stocks, stocks.size());
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
		}
		
	}

	@Override
	@Transactional(rollbackFor = ProductException.class)
	public void update(ProductSubmitParamDto productSubmitParamDto,Long productId) throws ProductException{
		if(productSubmitParamDto == null 
				|| productSubmitParamDto.getSellerId() == null
				|| productSubmitParamDto.getProductCategoryId() == null
				|| productSubmitParamDto.getImages() == null)
			throw new ProductException("保存商品出错,请检查参数");
		
		Product product = new Product();
		product.setId(productId);
		product.setSellerId(productSubmitParamDto.getSellerId());
		product.setName(productSubmitParamDto.getName());
		product.setImage(productSubmitParamDto.getMainImage());
		product.setIntroduction(productSubmitParamDto.getIntroduction());
		product.setProductCategoryId(productSubmitParamDto.getProductCategoryId());
		product.setSn(serinumService.getProductSn());
		product.setSales(new Long(0));
		product.setIsList(true);
		product.setActive(1);
		product.setCreated(new Date());
		product.setUpdated(new Date());
		product.setIsMarketable(productSubmitParamDto.getIsMarketable());
		product.setIsUnifiedSpec(productSubmitParamDto.getIsUnifiedSpec());
		product.setDeliveryType(productSubmitParamDto.getDeliveryType());
		product.setDeliveryFees(productSubmitParamDto.getDeliveryFees());
		product.setDeliveryTemplateId(productSubmitParamDto.getDeliveryTemplateId());
		product.setDeliveryWeight(productSubmitParamDto.getDeliveryWeight());
		if(StrKit.isBlank(productSubmitParamDto.getMarketPrice())){
			product.setMarketPrice(null);
		}else{
			product.setMarketPrice(new BigDecimal(productSubmitParamDto.getMarketPrice()));
		}
		if(productSubmitParamDto.getIsUnifiedSpec()){
			//说明统一规格
			product.setPrice(productSubmitParamDto.getPrice());
			product.setStock(StrKit.notBlank(productSubmitParamDto.getStock()) ? Integer.valueOf(productSubmitParamDto.getStock()) : 0);
		}else{
			//说明是多规格
			product.setPrice(getSpvPrice(productSubmitParamDto));
			product.setStock(getTotalStock(productSubmitParamDto));
		}
		
		try {
			product.update();
		} catch (Exception e) {
			throw new ProductException(e.getMessage());
		}
		
		//保存商品图片
		List<ProductImage> productImages = new ArrayList<ProductImage>();
		for (int i = 0; i < productSubmitParamDto.getImages().length; i++) {
			ProductImage productImage=new ProductImage();
			productImage.setProductId(product.getId());
			productImage.setLarge(productSubmitParamDto.getImages()[i]);
			productImage.setOrders(i+1);
			productImage.setActive(1);
			productImage.setCreated(new Date());
			productImage.setUpdated(new Date());
			productImages.add(productImage);
		}
		
		try {
			Db.deleteById(ProductImage.table, "product_id", productId);
			Db.batchSave(productImages, productImages.size());
		} catch (Exception e) {
			throw new ProductException(e.getMessage());
		}
		
		//保存选择的规格跟规格值
		if(productSubmitParamDto.getSpecificationIds() != null && productSubmitParamDto.getSpecificationIds().length>0 
				&& productSubmitParamDto.getSpecificationValues() != null && productSubmitParamDto.getSpecificationValues().length>0){
			List<ProductSpecification> specifications = new ArrayList<ProductSpecification>();
			List<ProductSpecificationValue> specificationValues = new ArrayList<ProductSpecificationValue>();
			for (int i = 0; i < productSubmitParamDto.getSpecificationIds().length; i++) {
				ProductSpecification ps=new ProductSpecification();
				ps.setProductId(product.getId());
				ps.setSpecificationId(productSubmitParamDto.getSpecificationIds()[i]);
				ps.setActive(1);
				ps.setCreated(new Date());
				ps.setUpdated(new Date());
				specifications.add(ps);
			}
			try {
				Db.deleteById(ProductSpecification.table, "product_id", productId);
				Db.batchSave(specifications, specifications.size());
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
			for (int i = 0; i < productSubmitParamDto.getSpecificationValues().length; i++) {
				ProductSpecificationValue psValue=new ProductSpecificationValue();
				String[] psname=productSubmitParamDto.getSpecificationValues()[i].split("_");
				psValue.setSpecificationId(new Long(psname[1]));
				psValue.setSpecificationValueId(new Long(psname[0]));
				psValue.setProductId(product.getId());
				psValue.setActive(1);
				psValue.setCreated(new Date());
				psValue.setUpdated(new Date());
				specificationValues.add(psValue);
			}
			
			try {
				Db.deleteById(ProductSpecificationValue.table, "product_id", productId);
				Db.batchSave(specificationValues, specificationValues.size());
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
		}
		
		//选择规格的价格与库存
		if(StrKit.notBlank(productSubmitParamDto.getPriceAndStock())){
			List<ProductSpecItem> stocks = new ArrayList<ProductSpecItem>();
			String stock = productSubmitParamDto.getPriceAndStock();
			JSONArray jarr = JSONArray.parseArray(stock); 
	        for (Iterator<?> iterator = jarr.iterator(); iterator.hasNext();) { 
	        	JSONObject job = (JSONObject) iterator.next(); 
	        	ProductSpecItem stockss=new ProductSpecItem();
	        	stockss.setProductId(product.getId());
	        	stockss.setSpecificationValue(job.get("specificationValue").toString());
	        	BigDecimal bprice = new BigDecimal(job.get("price").toString());
	        	stockss.setPrice(bprice);
	        	stockss.setStock(Integer.parseInt(job.get("stock").toString()));
	        	if(!StrKit.isBlank(job.get("weight").toString())){
	        		BigDecimal bweight = new BigDecimal(job.get("weight").toString());
	        		stockss.setWeight(bweight); 
	        	}
	         
	        	stockss.setActive(1);
	        	stockss.setCreated(new Date());
	        	stockss.setUpdated(new Date());
	        	stocks.add(stockss);
	        }
	        try {
				Db.deleteById(ProductSpecItem.table, "product_id", productId);
				Db.batchSave(stocks, stocks.size());
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
		}
	}
	
	@Override
	public Page<ProductResultDto> list(ProductParamDto productParamDto) throws ProductException {
		if(productParamDto == null || productParamDto.getSellerId() == null || productParamDto.getIsMarketable() == null)
			throw new ProductException("管理端获取商品列表缺少必要参数");
		AuthUserConfig authConfig = authConfigDao.findFirst("select * from " + AuthUserConfig.table);
		
		QueryHelper helper = new QueryHelper("select * ", "from " + Product.table);
		helper.addWhere("seller_id", productParamDto.getSellerId())
			  .addWhere("active", 1)
			  .addWhere("is_marketable", productParamDto.getIsMarketable())
			  .addWhereNotIn("id", productParamDto.getProductIds())
			  .addOrderBy("desc", "updated").build();
		
		Page<Product> productPage=productDao.paginate(productParamDto.getPageNo(), productParamDto.getPageSize(), 
				helper.getSelect(), helper.getSqlExceptSelect(), helper.getParams());
		List<ProductResultDto> productResults= new ArrayList<ProductResultDto>();
		for(Product product:productPage.getList()){
			ProductResultDto productResultDto=new ProductResultDto();
			productResultDto.setId(product.getId());
			productResultDto.setName(product.getName());
			productResultDto.setImg(getImageDomain() + product.getImage());
			productResultDto.setPrice(product.getPrice());
			productResultDto.setStock(product.getStock());
			productResultDto.setStartDate(product.getCreated());
			productResultDto.setSales(product.getSales());
			productResultDto.setWirlessUrl(authConfig.getWxDomain() + "/product/detail?id="+product.getId());
			productResults.add(productResultDto);
		}
		Page<ProductResultDto> productResultDto=new Page<>(productResults, productParamDto.getPageNo(), productParamDto.getPageSize(), productPage.getTotalPage(),productPage.getTotalRow() );
		return productResultDto;
	}
	
	
	@Override
	public List<ProductResultDto> getMarketableByIds(Long sellerId,String ids) throws ProductException {
		
		String sql="select * from " + Product.table + " where   active=1 and is_marketable = 1 and seller_id = ? and id in("+ids+")";
		List<Product> list=productDao.find(sql,sellerId); 
				
		List<ProductResultDto> productResults= new ArrayList<ProductResultDto>();
		for(Product product:list){
			ProductResultDto productResultDto=new ProductResultDto();
			productResultDto.setId(product.getId());
			productResultDto.setName(product.getName());
			productResultDto.setImg(getImageDomain() + product.getImage());
			productResultDto.setPrice(product.getPrice());
			productResultDto.setStock(product.getStock());
			productResultDto.setStartDate(product.getCreated());
			productResultDto.setSales(product.getSales());
			
			productResults.add(productResultDto);
		}
		
		return productResults;
	}

	@Override
	public ProductDetailResultDto getMobieDetail(ProductParamDto productParamDto) {
		if(productParamDto == null || productParamDto.getProductId() == null)
			throw new UmpException("获取商品详情缺少必要参数");
		
		Product product = productDao.findById(productParamDto.getProductId());
		if(product == null) throw new UmpException("商品不存在");
		if(product.getIsMarketable() == false) throw new UmpException("商品已下架");
		if(product.getActive() != 1) throw new UmpException("商品已删除");
		product.setImage(getImageDomain()+product.getImage());
		ProductDetailResultDto productDetail =new ProductDetailResultDto();
		productDetail.setProduct(product);
		
		AuthUserConfig authConfig = authConfigDao.findFirst("select * from " + AuthUserConfig.table);
		productDetail.setWirlessUrl(authConfig.getWxDomain() + "/product/detail?id="+product.getId());
		
        List<String> imgList=new ArrayList<>();
        List<ProductImage> images = prodImgdao.find("select * from " + ProductImage.table + " where product_id = ?", product.getId());
        for (ProductImage productImage : images) {
        	imgList.add(getImageDomain()+productImage.getLarge());
		}
        productDetail.setImageList(imgList);
        //product.setImage(PropKit.get("img_domain")+product.getImage());
        
        if(product.getIsUnifiedSpec() !=null && !product.getIsUnifiedSpec()){//是否多规格
        	List<SpecificationResultDto> specifications = new ArrayList<SpecificationResultDto>();
        	//查询商品多规格
            List<ProductSpecification> prodSpeces = prodSpecidao.find("select * from " + ProductSpecification.table + " where product_id = ?", product.getId());
            for(ProductSpecification ps : prodSpeces){
            	SpecificationResultDto specificationResultDto = new SpecificationResultDto();
            	Specification speci = specDao.findById(ps.getSpecificationId());
            	specificationResultDto.setSpecification(speci);
            	List<ProductSpecificationValue> prodSpecValues = prodSpecValuedao.find(
            			"select * from " + ProductSpecificationValue.table + " where product_id=? and specification_id = ? ", product.getId(), ps.getSpecificationId());
            	List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue> ();
            	for(ProductSpecificationValue productSpecValue : prodSpecValues){
            		SpecificationValue specificationValue = specValueDao.findById(productSpecValue.getSpecificationValueId());
            		specificationValues.add(specificationValue);
            	}
            	specificationResultDto.setSpecificationValues(specificationValues);
            	specifications.add(specificationResultDto);
            }
            productDetail.setSpecifications(specifications);
        }
        //限时打折
        ProdPromotionResultDto productPromotionResultDto = promotionService.getProductPromotion(product);
        productDetail.setPromotionInfo(productPromotionResultDto);
		return productDetail;
	}
	
	@Override
	public ProductAllResultDto findAllResultDto(ProductParamDto productParamDto) {
		ProductAllResultDto results =new ProductAllResultDto();
		String sql="SELECT * FROM `t_product` WHERE `id`="+productParamDto.getProductId()+
				" and `seller_id`="+productParamDto.getSellerId();
		Product product=productDao.findFirst(sql);
		String sqlImage = "SELECT * FROM `t_product_image` WHERE `product_id`= ?";
		List<ProductImage> productImages = prodImgdao.find(sqlImage, product.getId());
		List<ImagepathResultDto> imageList = new ArrayList<ImagepathResultDto>();
		for(ProductImage image : productImages){
			ImagepathResultDto imgDto=new ImagepathResultDto();
			imgDto.setImgPath(getImageDomain() + image.getLarge());
			imgDto.setImgUrl(image.getLarge());
			imageList.add(imgDto);
		}
		results.setImageList(imageList);
		String sqlSpecification = "SELECT s.* FROM t_product_specification ps INNER JOIN t_specification s ON ps.`specification_id` = s.id WHERE ps.`product_id` = ?";
		List<Specification> specifications=specDao.find(sqlSpecification, product.getId());
		results.setSpecifications(specifications);
		
		String sqlSpecificationVlue = "SELECT sv.* FROM t_product_specification_value psv INNER JOIN t_specification_value sv ON psv.`specification_value_id` = sv.`id` WHERE psv.`product_id` = ?";
		List<SpecificationValue> specificationValues = specValueDao.find(sqlSpecificationVlue, product.getId());
		results.setSpecificationValues(specificationValues);
		
		String sqlStock = "SELECT * FROM "+ProductSpecItem.table+" WHERE `product_id`= ?";
		List<ProductSpecItem> stocks=prodSpecItemdao.find(sqlStock, product.getId());
		results.setStocks(stocks);
		return results;
	}


	@Override
	public List<ProductMobileResultDto> findProducts4Mobile(ProductMobileParamDto mobileParamDto)
			throws ProductException {
		if(mobileParamDto == null)
			throw new ProductException("微信端获取商品列表缺少必要参数");
		String select = "SELECT p0.* ";
		String sqlExceptSelect = "FROM "+Product.table+" p0 ";
		
		QueryHelper helper = new QueryHelper(select, sqlExceptSelect);
		helper
		.addWhere("p0.active", 1)
		.addWhere("p0.is_marketable", 1)
		.addWhere("p0.product_category_id", mobileParamDto.getCategId())
		.addWhereLike("p0.name", mobileParamDto.getKeyword())
		.addOrderBy(mobileParamDto.getOrderBy(), "p0.price, p0.sales")
		.build();
		
		Page<Product> pager = productDao.paginate(mobileParamDto.getPageNo(), mobileParamDto.getPageSize(), 
				helper.getSelect(), helper.getSqlExceptSelect(), helper.getParams());
		
		List<ProductMobileResultDto> productVos = new ArrayList<ProductMobileResultDto>();
		for(Product product : pager.getList()){
			ProductMobileResultDto pv = new ProductMobileResultDto();
			pv.setId(product.getLong("id").toString());
			if(StrKit.notBlank(product.getStr("name"))){
				pv.setName(product.getStr("name").length() > 10 ? product.getStr("name").substring(0, 10).concat("...") : product.getStr("name"));
			}
			pv.setMainPic(getImageDomain()+product.getImage());	
			pv.setStore(product.getStock()==null ? "0" : product.getStock().toString());
			pv.setPrice(product.getPrice());
			if(product.getMarketPrice() != null)
				pv.setMarketPrice(product.getMarketPrice().toString());
			productVos.add(pv);
		}
		
		return productVos;
	}

	/**
	 * 获取规格价格区间
	 * @param productSubmitParamDto
	 * @return
	 */
	private String getSpvPrice(ProductSubmitParamDto productSubmitParamDto){
		String stock = productSubmitParamDto.getPriceAndStock();
		JSONArray jarr = JSONArray.parseArray(stock); 
		BigDecimal min,max;
		min=max=new BigDecimal(jarr.getJSONObject(0).get("price").toString());
		for(int i=0;i<jarr.size();i++){
			JSONObject json = jarr.getJSONObject(i);
			BigDecimal bprice = new BigDecimal(json.get("price").toString());
			if(bprice.compareTo(max)==1){
				max= bprice;
			}
			if(bprice.compareTo(min)==-1){
				min= bprice;
			}
        }
		if(max.compareTo(min)==0){
			return max.toString();
		}else{  
		    return min+" ~ "+max;
		}
	}
	
	private Integer getTotalStock(ProductSubmitParamDto productSubmitParamDto){
		int stockTotal=0;
		String stock = productSubmitParamDto.getPriceAndStock();
		JSONArray jarr = JSONArray.parseArray(stock); 
        for(int i=0;i<jarr.size();i++){
			JSONObject json = jarr.getJSONObject(i);
			int stocknum=Integer.parseInt(json.get("stock").toString());
			stockTotal=stockTotal+stocknum;
        }
		return stockTotal;
	}

	@Override
	public HashMap<String, ProductSpecPriceResultDto> getProductSpecPrice(Long productId)
			throws ProductException {
		if(productId == null) throw new ProductException("获取商品规格价格参数错误");
		Product product = productDao.findById(productId);
		if(product == null) throw new ProductException("商品不存在");
		
		HashMap<String, ProductSpecPriceResultDto> result = new HashMap<String, ProductSpecPriceResultDto>();
		List<ProductSpecItem> specItems = prodSpecItemdao.find("select * FROM "+ProductSpecItem.table+" WHERE product_id = ? ", productId);
		for (ProductSpecItem specItem : specItems) {
			ProductSpecPriceResultDto priceDto = new ProductSpecPriceResultDto();
			priceDto.setPrice(specItem.getPrice().toString());
			priceDto.setStock(specItem.getStock().toString());
			if(specItem.getWeight()!=null){
				priceDto.setWeight(specItem.getWeight().toString());	
			}
			
			//检查对于商品是否有促销，限时打折
			String promoPrice = promotionService.getProductPromotionPrice(product, specItem);
			if(StrKit.notBlank(promoPrice)) priceDto.setPromPrice(promoPrice.toString());
			result.put(specItem.getSpecificationValue(), priceDto);
		}
		return result;
	}


	@Override
	public List<ProductResultDto> getProducts(String productIds) throws ProductException {
		if(StrKit.isBlank(productIds)) throw new ProductException("调用批量获取商品详情接口参数错误");
		
		List<ProductResultDto> resultDtos = new ArrayList<ProductResultDto>();
		final String [] productIdArrs = productIds.split("-");
		List<Object> params = new ArrayList<Object>();
		final StringBuffer condition = new StringBuffer(); 
		for(String id : productIdArrs){
			params.add(Long.valueOf(id));
			condition.append("?").append(",");
		}
		condition.deleteCharAt(condition.length() -1);
		
		final StringBuffer where = new StringBuffer(" where 1=1 ");
		where.append(" and id in ("+condition+")");
		final String select = "select * ";
		final String sqlExceptSelect =select+ " from "+Product.table+" "+where.toString();
		List<Product> products=productDao.find(sqlExceptSelect, params.toArray());
		for (Product product : products) {
			ProductResultDto resultDto = new ProductResultDto();
			resultDto.setId(product.getId());
			resultDto.setImg(getImageDomain() + product.getImage());
			resultDto.setName(product.getName());
			resultDto.setPrice(product.getPrice());
			resultDto.setStock(product.getStock());
			resultDtos.add(resultDto);
		}
		return resultDtos;
	}

	@Override
	public Page<ProductMobileResultDto> getMobilePromotionProduct(ProductMobileParamDto mobileParamDto)
			throws ProductException {
		if(mobileParamDto ==null)
			throw new ProductException("获取打折商品缺少必要参数");
		List<ProductMobileResultDto> resultDtos = new ArrayList<ProductMobileResultDto>();
		
		//查询出有效打折数据
		Date currDate = new Date();
		final String select = "select * ";
		final String sqlExceptSelect = " from "+Product.table+" where is_marketable=1 and active=1 and id in"
				+ " (select product_id from " + PromotionSet.table + " where promotion_id "
				+ "in (select id from "+Promotion.table+" where start_date<=? and end_date >=? ))";
		Page<Product> pages = productDao.paginate(mobileParamDto.getPageNo(), mobileParamDto.getPageSize(),
				select, sqlExceptSelect,
				currDate, currDate);
		for(Product product : pages.getList()){
			ProductMobileResultDto resultDto = new ProductMobileResultDto();
			resultDto.setId(product.getId().toString());
			resultDto.setMainPic(getImageDomain() + product.getImage());
			if(StrKit.notBlank(product.getStr("name"))){
				resultDto.setName(product.getStr("name").length() > 10 ? product.getStr("name").substring(0, 10).concat("...") : product.getStr("name"));
			}
			resultDto.setPrice(product.getPrice());
			resultDto.setStore(product.getStock().toString());
			resultDtos.add(resultDto);
		}
	
		return new Page<ProductMobileResultDto>(resultDtos, pages.getPageNumber(), pages.getPageSize(), pages.getTotalPage(), pages.getTotalRow());
	}

	@Override
	public List<ProductMobileResultDto> getHotProduct(ProductMobileParamDto mobileParamDto) throws ProductException {
		QueryHelper helper = new QueryHelper();
		helper.addWhere("is_marketable", 1)
		.addWhere("active", 1)
		.addOrderBy("desc", "price").build();
		String sql = "select * from " + Product.table + helper.getWhere() + " LIMIT 6 ";
		List<Product> products = productDao.find(sql, helper.getParams());
		List<ProductMobileResultDto> resultDtos = new ArrayList<ProductMobileResultDto>();
		for(Product product : products){
			ProductMobileResultDto mobileResultDto = new ProductMobileResultDto();
			resultDtos.add(mobileResultDto);
			mobileResultDto.setId(product.getId().toString());
			mobileResultDto.setMainPic(getImageDomain() + product.getImage());
			if(StrKit.notBlank(product.getName())){
				mobileResultDto.setName(product.getName().length() > 10 ? product.getName().substring(0, 10).concat("...") : product.getName());
			}
			mobileResultDto.setPrice(product.getPrice());
		}
		return resultDtos;
	}

	@Override
	public List<ProductMobileResultDto> getNewProduct(ProductMobileParamDto mobileParamDto) throws ProductException {
		QueryHelper helper = new QueryHelper();
		helper.addWhere("is_marketable", 1)
		.addWhere("active", 1)
		.addOrderBy("desc", "price").build();
		String sql = "select * from " + Product.table + helper.getWhere() + " LIMIT 6 ";
		List<Product> products = productDao.find(sql, helper.getParams());
		List<ProductMobileResultDto> resultDtos = new ArrayList<ProductMobileResultDto>();
		for(Product product : products){
			ProductMobileResultDto mobileResultDto = new ProductMobileResultDto();
			resultDtos.add(mobileResultDto);
			mobileResultDto.setId(product.getId().toString());
			mobileResultDto.setMainPic(getImageDomain() + product.getImage());
			if(StrKit.notBlank(product.getName())){
				mobileResultDto.setName(product.getName().length() > 10 ? product.getName().substring(0, 10).concat("...") : product.getName());
			}
			mobileResultDto.setPrice(product.getPrice());
		}
		return resultDtos;
	}

	@Override
	public List<ProductMobileResultDto> getRecommendProduct(ProductMobileParamDto mobileParamDto)
			throws ProductException {
		QueryHelper helper = new QueryHelper();
		helper.addWhere("is_marketable", 1)
		.addWhere("active", 1)
		.addOrderBy("desc", "price").build();
		String sql = "select * from " + Product.table + helper.getWhere() + " LIMIT 6 ";//暂时按价格排序
		List<Product> products = productDao.find(sql, helper.getParams());
		List<ProductMobileResultDto> resultDtos = new ArrayList<ProductMobileResultDto>();
		for(Product product : products){
			ProductMobileResultDto mobileResultDto = new ProductMobileResultDto();
			resultDtos.add(mobileResultDto);
			mobileResultDto.setId(product.getId().toString());
			mobileResultDto.setMainPic(getImageDomain() + product.getImage());
			if(StrKit.notBlank(product.getName())){
				mobileResultDto.setName(product.getName().length() > 10 ? product.getName().substring(0, 10).concat("...") : product.getName());
			}
			mobileResultDto.setPrice(product.getPrice());
		}
		return resultDtos;
	}

	@Override
	public List<ProductMobileResultDto> getIndexProduct(ProductMobileParamDto mobileParamDto) throws ProductException {
		QueryHelper helper = new QueryHelper();
		helper.addWhere("is_marketable", 1)
		.addWhere("active", 1)
		.addOrderBy("desc", "price").build();
		//暂时按价格排序 取30条数据
		List<Product> products = productDao.find("select * from " + Product.table + helper.getWhere() + " LIMIT 30 ", helper.getParams());
		List<ProductMobileResultDto> resultDtos = new ArrayList<ProductMobileResultDto>();
		for(Product product : products){
			ProductMobileResultDto mobileResultDto = new ProductMobileResultDto();
			resultDtos.add(mobileResultDto);
			mobileResultDto.setId(product.getId().toString());
			mobileResultDto.setMainPic(getImageDomain() + product.getImage());
			if(StrKit.notBlank(product.getName())){
				mobileResultDto.setName(product.getName().length() > 10 ? product.getName().substring(0, 10).concat("...") : product.getName());
			}
			mobileResultDto.setPrice(product.getPrice());
		}
		return resultDtos;
	}

	@Override
	public Long getProductCountByCategroyId(Long categroyId) {
		return Db.queryLong("select * from " + Product.table + " where product_category_id=? ", categroyId);
	}

	@Override
	public Product findById(Long productId) {
		return productDao.findById(productId);
	}
	
}
