package com.dbumama.market.web.admin.product.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.ImageGroup;
import com.dbumama.market.model.SellerImages;
import com.dbumama.market.service.api.product.ImageGroupResultDto;
import com.dbumama.market.service.api.product.ImageGroupService;
import com.dbumama.market.service.api.product.ImagepathResultDto;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

@RouteBind(path = "attachment", viewPath = "attachment")
@Before(IocInterceptor.class)
public class ImageGroupController extends  AdminBaseController<ImageGroup>{
	@Inject.BY_NAME
	private ImageGroupService imageGroupService;
	public void index(){
        List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
        setAttr("imageGroups", resultDto);
		render("/attachment/imageGroup_index.html");
	}
	
	public void getImageGroupJson(){
        List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
        rendSuccessJson(resultDto); 
	}
	
	public void addGroup(){
		render("/attachment/add_group.html");
	}
	
	public void save(){
		String groupName=getPara("group_name");
		ImageGroup imageGroup=new ImageGroup();
		imageGroup.setGroupName(groupName);
		imageGroup.setSellerId(getSellerId());
		
		try {
			imageGroup.save();
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
		
	}
	
	public void getImageList(){
		Long groupId=getParaToLong("id");
		String select = " SELECT  o.*";
		String sqlExceptSelect = " FROM "+SellerImages.table+" o "
				+" where 1=1 and o.active=1 ";
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer();
		where.append(" and o.seller_id=?");
		params.add(getSellerId());
		
		if(groupId!=null){
			where.append(" and o.img_group_id=? ");
			params.add(groupId);
		}
		sqlExceptSelect = sqlExceptSelect + " " + where.toString() + " order by o.created desc ";
		Page<SellerImages> pages=SellerImages.dao.paginate(getPageNo(), 18, select, sqlExceptSelect,params.toArray());
		List<ImagepathResultDto> imagePath=new ArrayList<ImagepathResultDto>();
		for(SellerImages sellerImage : pages.getList()){
			ImagepathResultDto dto=new ImagepathResultDto();
			dto.setId(sellerImage.getId());
			dto.setImgPath(getImageDomain() + sellerImage.getImgPath());
			dto.setImgUrl(sellerImage.getImgPath());
			imagePath.add(dto);
		}
		Page<ImagepathResultDto> pageDto=new Page<>(imagePath, getPageNo(), 18, pages.getTotalPage(),pages.getTotalRow() );
		rendSuccessJson(pageDto);
	}
	
	public void del(){
		Long id=getParaToLong("ids");
		SellerImages sellerImages=SellerImages.dao.findById(id);
		sellerImages.setActive(0);
		try {
			sellerImages.update();	
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	@Override
	protected Class<ImageGroup> getModelClass() {
		return ImageGroup.class;
	}

}
