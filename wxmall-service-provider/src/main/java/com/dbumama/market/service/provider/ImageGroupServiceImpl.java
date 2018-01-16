package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.ImageGroup;
import com.dbumama.market.model.SellerImages;
import com.dbumama.market.service.api.product.ImageGroupResultDto;
import com.dbumama.market.service.api.product.ImageGroupService;
import com.dbumama.market.service.api.product.ImagepathResultDto;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@Service("imageGroupService")
public class ImageGroupServiceImpl extends AbstractServiceImpl implements ImageGroupService{

	private static final ImageGroup imgGroupdao = new ImageGroup().dao();
	private static final SellerImages sellerImgsdao = new SellerImages().dao();
	
	@Override
	public List<ImageGroupResultDto> getGroup(Long sellerId) {
		String sql="select * from "+ImageGroup.table +" where seller_id=?";
        List<ImageGroup> imageGroups = imgGroupdao.find(sql, sellerId);
        List<ImageGroupResultDto> resultDto=new ArrayList<ImageGroupResultDto>();
        for (ImageGroup imageGroup : imageGroups) {
        	ImageGroupResultDto dto=new ImageGroupResultDto();
        	String sqlNum="select count(*) as imageNum from "+SellerImages.table +" where active=1 AND img_group_id="+imageGroup.getId();
        	Record record=Db.findFirst(sqlNum);
        	dto.setImageNum(record.getLong("imageNum"));
        	dto.setId(imageGroup.getId());
        	dto.setGroupName(imageGroup.getGroupName());
        	resultDto.add(dto);
		}
		return resultDto;
	}

	@Override
	public String isHaveGroup(Long sellerId) {
		String groupId=null;
        List<ImageGroup> imageGroups=imgGroupdao.find("select * from "+ImageGroup.table +" where seller_id=?", sellerId);
        if(imageGroups.isEmpty()){
        	ImageGroup imageGroup=new ImageGroup();
        	imageGroup.setGroupName("未分组");
        	imageGroup.setSellerId(sellerId);
        	imageGroup.save();
        	groupId=imageGroup.getId().toString();
        }
		return groupId;
	}

	@Override
	public ImageGroup findById(Long id) {
		return imgGroupdao.findById(id);
	}

	@Override
	public SellerImages findSellerImagesById(Long id) {
		return sellerImgsdao.findById(id);
	}

	@Override
	public Page<ImagepathResultDto> pageImages(Long sellerId, Long groupId, Integer pageNo, Integer pageSize) {
		QueryHelper helper = new QueryHelper("SELECT  o.*", " FROM "+SellerImages.table+" o ");
		helper.addWhere("o.seller_id", sellerId).addWhere("o.img_group_id", groupId).addWhere("o.active", 1).addOrderBy("desc", "o.created").build();
		Page<SellerImages> pages=sellerImgsdao.paginate(pageNo, pageSize, helper.getSelect(), helper.getSqlExceptSelect(), helper.getParams());
		List<ImagepathResultDto> imagePath=new ArrayList<ImagepathResultDto>();
		for(SellerImages sellerImage : pages.getList()){
			ImagepathResultDto dto=new ImagepathResultDto();
			dto.setId(sellerImage.getId());
			dto.setImgPath(getImageDomain() + sellerImage.getImgPath());
			dto.setImgUrl(sellerImage.getImgPath());
			imagePath.add(dto);
		}
		return new Page<>(imagePath, pages.getPageNumber(), pages.getPageSize(), pages.getTotalPage(),pages.getTotalRow());
	}

}
