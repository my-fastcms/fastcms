package com.dbumama.market.web.admin.product.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.model.SellerImages;
import com.dbumama.market.service.api.product.ImageGroupResultDto;
import com.dbumama.market.service.api.product.ImageGroupService;
import com.dbumama.market.service.api.product.ImagepathResultDto;
import com.dbumama.market.service.api.product.ProductCategoryService;
import com.dbumama.market.service.api.product.ProductParamDto;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.service.api.product.ProductSubmitParamDto;
import com.dbumama.market.service.api.specification.SpecificationParamDto;
import com.dbumama.market.service.api.specification.SpecificationService;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import net.coobird.thumbnailator.Thumbnails;
@RouteBind(path = "product", viewPath = "product")
@Before(IocInterceptor.class)
public class ProductController extends AdminBaseController<Product> {
	@Inject.BY_NAME
	private ProductCategoryService productCategoryService;
	@Inject.BY_NAME
	private SpecificationService specificationService;
	@Inject.BY_NAME
	private ProductService productService;
	@Inject.BY_NAME
	private ImageGroupService imageGroupService;
	public void index() {
		render("pd_index.html");
	}
	
	public void warehouseIndex() {
		render("pd_warehouse.html");
	}
	
	public void warehouse(){
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		StringBuilder sqlBuilder = new StringBuilder("from " + Product.table + " where seller_id = ? and active=1 and is_marketable=0");
		sqlBuilder.append(" order by updated desc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		Page<Product> productPage=Product.dao.paginate(getPageNo(), getPageSize(), 
				"select * ", 
				sqlBuilder.toString(), 
				o);
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
			productResults.add(productResultDto);
		}
		Page<ProductResultDto> productResultDto=new Page<>(productResults, getPageNo(), getPageSize(), productPage.getTotalPage(),productPage.getTotalRow() );
		rendSuccessJson(productResultDto);
	}
	
	public void list(){
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		StringBuilder sqlBuilder = new StringBuilder("from " + Product.table + " where seller_id = ? and active=1 and is_marketable=1");
		sqlBuilder.append(" order by updated desc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		Page<Product> productPage=Product.dao.paginate(getPageNo(), getPageSize(), 
				"select * ", 
				sqlBuilder.toString(), 
				o);
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
			if(getUsedAuthUser() != null){
				productResultDto.setWirlessUrl(PropKit.get("w_doamin")+"product/detail?id="+product.getId());	
			}
			productResults.add(productResultDto);
		}
		Page<ProductResultDto> productResultDto=new Page<>(productResults, getPageNo(), getPageSize(), productPage.getTotalPage(),productPage.getTotalRow() );
		rendSuccessJson(productResultDto);
	}

	public void add() {
		SpecificationParamDto specificationParamDto = new SpecificationParamDto();
		specificationParamDto.setSellerId(getSellerId());
		
		List<ProductCategory> productCategory = productCategoryService.findRoots();
		setAttr("productCategory", productCategory);
		setAttr("specificationResultDto", specificationService.findAll(specificationParamDto));
		render("pd_add.html");
	}
	
	public void edit() {
		SpecificationParamDto specificationParamDto = new SpecificationParamDto();
		specificationParamDto.setSellerId(getSellerId());
		
		ProductParamDto productParamDto=new ProductParamDto(getSellerId());
		productParamDto.setProductId(new Long(getPara(0)).longValue());
		if(!StrKit.isBlank(getPara(0))){
			Product product=Product.dao.findById(getPara(0));
			setAttr("imgUrl", product.getImage());
			product.setImage(getImageDomain()+product.getImage());
			if(product != null){
				
				setAttr("deliveryTemplate", DeliveryTemplate.dao.findById(product.getDeliveryTemplateId()));	
				
				setAttr("product", product);
				List<ProductCategory> productCategory = productCategoryService.findRoots();
				setAttr("productCategory", productCategory);
				setAttr("specificationResultDto", specificationService.findAll(specificationParamDto));
				setAttr("productAllResultDto",productService.findAllResultDto(productParamDto));
				setAttr("categoryName",productCategoryService.find(product.getProductCategoryId()).getName());
			}
			
		}
		render("pd_edit.html");
	}
	
	/**
	 * 保存
	 * 
	 */
	//@Before(ProductValidator.class)
	//@Before(Tx.class)
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
		int deliveryType=getParaToInt("delivery_type");
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
        if(deliveryType==0){
        	productParamDto.setDeliveryFees(deliveryFees);
        	productParamDto.setDeliveryTemplateId(null);
		}else{
			productParamDto.setDeliveryFees(null);
        	productParamDto.setDeliveryTemplateId(deliveryTemplateId);
		}
        if(deliveryTemplateId!=null){
			DeliveryTemplate dt=DeliveryTemplate.dao.findById(deliveryTemplateId);
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
			productService.save(productParamDto);
		}else{
			productService.update(productParamDto,product.getId());
		}
		
		redirect("/product/index");
	}
	/**
	 * 下架该商品
	 */
   public void offShelve(){
	   String ids = getPara("ids");
	   for(String id : ids.split("-")){
		   Product product=Product.dao.findById(id);
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
		   Product product=Product.dao.findById(id);
		   product.setIsMarketable(true);
		   product.update();
	   }
	   rendSuccessJson("操作成功！");
   }
	
   /**
    * 选择图片界面
    * 
    */
    public void addImage(){
    	List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
        setAttr("imageGroups", resultDto);
    	setAttr("model", getPara("model"));
    	render("/product/list_image.html");
    }
    
    public void addUploadImage(){
    	setAttr("model", getPara("model"));
    	setAttr("groupId", getPara("groupId"));
    	render("/product/pd_upload_image.html");
    }

	public void upload1() {
		if ("config".equals(getPara("action"))) {
			renderJsp("/resources/ueditor/jsp/config.json");
			return;
		}
		if ("listimage".equals(getPara("action"))) {
			/*String[] str = { "jpg", "gif", "png", "jpeg" };
			String filePath = "/" + getSellerId();
			File dir = new File(PropKit.get("upload_file_path") + filePath);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			File[] files = dir.listFiles();*/
			JSONArray jsonArray = new JSONArray();
			setAttr("state", "SUCCESS");
			/*if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					// 过滤非图片
					String fileType = files[i].getName().substring(files[i].getName().lastIndexOf('.') + 1,
							files[i].getName().length());
					for (int t = 0; t < str.length; t++) {
						if (str[t].equals(fileType.toLowerCase())) {
							JSONObject json = new JSONObject();
							json.put("url", getRequest().getContextPath() + "/upload/" + getSellerId() + "/" + files[i].getName());
							jsonArray.add(json);
						}
					}getParaToInt("start")+1
				}getPageNo()
			}*/
			
			int pageNo = getParaToInt("start")/ getParaToInt("size") + 1;
			
			Page<SellerImages> sellerImages = SellerImages.dao.paginate(
					pageNo, getParaToInt("size"), 
					"select * ",
					" from " + SellerImages.table + " where seller_id=? ", getSellerId());
			for(SellerImages sellerImage : sellerImages.getList()){
				JSONObject json = new JSONObject();
				json.put("url", getImageDomain() + sellerImage.getImgPath());
				jsonArray.add(json);
			}
			
			setAttr("list", jsonArray);
			setAttr("total", sellerImages == null ? 0 : sellerImages.getTotalRow());
			
			setAttr("start", getParaToInt("start"));
			renderJson(new String[] { "state", "list","total","start" });
		} else {
			String filePath = "/" + getSellerId() + "/" + DateTimeUtil.FORMAT_YYYYMMDD8.format(new Date());
			List<UploadFile> uFile = getFiles(filePath);
			for (UploadFile uploadFile : uFile) {
				String fileName = uploadFile.getFileName();
				String imgUrl = filePath + "/" + uploadFile.getFileName();
				String original = uploadFile.getOriginalFileName();
				long fileSize = uploadFile.getFile().length();
				try {
					File compressFile = null;
			        if(uploadFile.getFile().length() > 200 * 1024){ //超过200Kb的图片进行压缩
			        	compressFile = new File(uploadFile.getFile().getPath());
			            //对文件进行压缩
			            Thumbnails.of(uploadFile.getFile())
			            .scale(1f)
			            //.size(160, 160)
			            //.rotate(90)
			            //.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("watermark.png")), 0.5f)
			            .outputQuality(0.5f)
			            .toFile(compressFile);
			            imgUrl = filePath + "/" + compressFile.getName();
			            original = compressFile.getName();
			            fileSize = compressFile.length();
			        }
					SellerImages sellerImages = new SellerImages();
					sellerImages.setSellerId(getSellerId());
					sellerImages.setImgPath(imgUrl);
					sellerImages.setActive(1);
					sellerImages.setCreated(new Date());
					sellerImages.setUpdated(new Date());
					sellerImages.save();
				} catch (Exception e) {
					continue;
				}
				
				String[] typeArr = fileName.split("\\.");
				setAttr("url", getImageDomain() + imgUrl);
				//setAttr("url", imageUrl);
				setAttr("title", fileName);
				setAttr("original", original);
				setAttr("type", "." + typeArr[1]); // 这里根据实际扩展名去写
				setAttr("size", fileSize);
				setAttr("state", "SUCCESS");
			}
			renderJson(new String[] { "original", "url","original", "title", "state" });
		}
	}

	public void upload() {
		String groupId=getPara("groupId");
		String filePath = "/" + getSellerId() + "/" + DateTimeUtil.FORMAT_YYYYMMDD8.format(new Date());
		List<UploadFile> uFile = getFiles(filePath);
		if (uFile == null) {
			rendFailedJson("没有图片");
			return;
		}
		JSONArray jsonArray = new JSONArray();
		for (UploadFile uploadFile : uFile) {
			JSONObject json = new JSONObject();
			try {
				File compressFile = null;
				String imgUrl = filePath + "/" + uploadFile.getFileName();
		        if(uploadFile.getFile().length() > 200 * 1024){ //超过200Kb的图片进行压缩
		        	compressFile = new File(uploadFile.getFile().getPath());
		            //对文件进行压缩
		            Thumbnails.of(uploadFile.getFile())
		            .scale(1f)
		            //.size(160, 160)
		            //.rotate(90)
		            //.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("watermark.png")), 0.5f)
		            .outputQuality(0.5f)
		            .toFile(compressFile);
		            imgUrl = filePath + "/" + compressFile.getName();
		        }
		        json.put("filePath", getImageDomain() + imgUrl);
				json.put("fileUrl", imgUrl);
				SellerImages sellerImages = new SellerImages();
				sellerImages.setSellerId(getSellerId());
				if(!StrKit.isBlank(groupId)&&!groupId.endsWith("undefined")){
					sellerImages.setImgGroupId(new Long(groupId));
				}
				sellerImages.setImgPath(imgUrl);
				sellerImages.setActive(1);
				sellerImages.setCreated(new Date());
				sellerImages.setUpdated(new Date());
				sellerImages.save();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			jsonArray.add(json);
		}
		rendSuccessJson(jsonArray);
	}

	/**
	 * 浏览
	 */
	public void browser() {
		Page<SellerImages> sellerImagess = SellerImages.dao.paginate(
				getPageNo(), 24, 
				"select * ",
				" from " + SellerImages.table + " where seller_id=? ", getSellerId());
		List<ImagepathResultDto> imagePath=new ArrayList<ImagepathResultDto>();
		for(SellerImages sellerImage : sellerImagess.getList()){
			ImagepathResultDto dto=new ImagepathResultDto();
			dto.setImgPath(getImageDomain() + sellerImage.getImgPath());
			dto.setImgUrl(sellerImage.getImgPath());
			imagePath.add(dto);
		}
		Page<ImagepathResultDto> pageDto=new Page<>(imagePath, getPageNo(), 24, sellerImagess.getTotalPage(),sellerImagess.getTotalRow() );
		rendSuccessJson(pageDto);
	}

	@Override
	protected Class<Product> getModelClass() {
		return Product.class;
	}

}
