package com.dbumama.market.web.admin.upload.controller;

import java.util.List;

import com.dbumama.market.model.ImageGroup;
import com.dbumama.market.model.SellerImages;
import com.dbumama.market.service.api.product.ImageGroupResultDto;
import com.dbumama.market.service.api.product.ImageGroupService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;

@RouteBind(path = "attachment", viewPath = "attachment")
public class AttachmentController extends AdminBaseController<ImageGroup>{
	@BY_NAME
	private ImageGroupService imageGroupService;
	public void index(){
        List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
        setAttr("imageGroups", resultDto);
		render("/attachment/image_group_index.html");
	}
	
	public void getImageGroupJson(){
        List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
        rendSuccessJson(resultDto); 
	}
	
	public void addGroup(){
		ImageGroup imageGroup=imageGroupService.findById(getParaToLong("id"));	
		setAttr("imageGroup", imageGroup);
		render("/attachment/add_group.html");
	}
	
	public void editGroup(){
		Long groupId=getParaToLong("groupId");
		List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
	    setAttr("imageGroups", resultDto);
	    setAttr("groupId", groupId);
	    setAttr("imageId", getPara("imageId"));
		render("/attachment/edit_group.html");
	}
	
	public void saveEditGroup(){
		final String image=getPara("imageId");
		final Long groupId=getParaToLong("groupId");
		try {
			if(!StrKit.isBlank(image)&&groupId!=null){
				String imageIds[]=(image.substring(0,image.length()-1)).split(",");
				for (int i = 0; i < imageIds.length; i++) {
					SellerImages sellerImages = imageGroupService.findSellerImagesById(Long.valueOf(imageIds[i]));
					sellerImages.setImgGroupId(groupId);
						sellerImages.update();	
						
				}
			}
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void save(){
		Long id=getParaToLong("id");
		String groupName=getPara("group_name");
		ImageGroup imageGroup=new ImageGroup();
		imageGroup.setGroupName(groupName);
		imageGroup.setSellerId(getSellerId());
		imageGroup.setId(id);
		try {
			if(imageGroup.getId()==null){
				imageGroup.save();
			}else{
				imageGroup.update();	
			}
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
		
	}
	
	public void getImageList(){
		Long groupId=getParaToLong("id");
		rendSuccessJson(imageGroupService.pageImages(getSellerId(), groupId, getPageNo(), getPageSize()));
	}
	
	public void del(){
		SellerImages sellerImages=imageGroupService.findSellerImagesById(getParaToLong("ids"));
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
