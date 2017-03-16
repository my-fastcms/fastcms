package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.ImageGroup;
import com.dbumama.market.model.SellerImages;
import com.dbumama.market.service.api.product.ImageGroupResultDto;
import com.dbumama.market.service.api.product.ImageGroupService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@Service("imageGroupService")
public class ImageGroupServiceImpl implements ImageGroupService{

	@Override
	public List<ImageGroupResultDto> getGroup(Long sellerId) {
		String sql="select * from "+ImageGroup.table +" where seller_id=?";
        List<ImageGroup> imageGroups=ImageGroup.dao.find(sql, sellerId);
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
		String sql="select * from "+ImageGroup.table +" where seller_id=?";
        List<ImageGroup> imageGroups=ImageGroup.dao.find(sql, sellerId);
        if(imageGroups.isEmpty()){
        	ImageGroup imageGroup=new ImageGroup();
        	imageGroup.setGroupName("未分组");
        	imageGroup.setSellerId(sellerId);
        	imageGroup.save();
        	groupId=imageGroup.getId().toString();
        }
		return groupId;
	}


}
