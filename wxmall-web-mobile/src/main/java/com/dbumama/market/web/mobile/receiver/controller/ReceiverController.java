package com.dbumama.market.web.mobile.receiver.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dbumama.market.model.Area;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.jfinal.kit.StrKit;

@RouteBind(path = "receiver")
public class ReceiverController extends BaseMobileController{
	/** 收货地址最大保存数 */
	public static final Integer MAX_RECEIVER_COUNT = 8;
	@Inject.BY_NAME
	private AreaService areaService;
	public void index(){
		List<BuyerReceiver> receivers = BuyerReceiver.dao.find("select * from " + 
				BuyerReceiver.table + " where buyer_id=?", getBuyerId());
		List<BuyerReceiver> receiver=new ArrayList<BuyerReceiver>();
		for (BuyerReceiver buyerReceiver : receivers) {
			Area area=Area.dao.findById(buyerReceiver.getAreaId());
			buyerReceiver.setAddress(area.getFullName()+buyerReceiver.getAddress());
			receiver.add(buyerReceiver);
		}
		setAttr("receiverList", receiver);
		render("/receiver/index.html");
	}
	
	public void add() {
        render("/receiver/receiver-input.html");
    }

    public void edit() {
        String id = getPara("id");
        BuyerReceiver receiver = BuyerReceiver.dao.findById(id);
        setAttr("receiver", receiver);
        render("/receiver/receiver-input.html");
    }

    // 收货地址添加
    public void save() {
        try {
        	final String address = getPara("address");
        	final String name = getPara("name");
        	final String phone = getPara("phone");
        	final String area_id = getPara("area_id");
        	final String province = getPara("province");
        	final String city = getPara("city");
        	final String district = getPara("district");
        	if(StrKit.isBlank(address) || StrKit.isBlank(name) || StrKit.isBlank(phone) || StrKit.isBlank(area_id)){
        		rendFailedJson("请传入完整的参数");
        		return;
        	}
        	
        	final String is_default = getPara("is_default");
        	
        	BuyerReceiver receiver = null;
        	Long receiverId = getParaToLong("id");
        	if(receiverId == null){
        		receiver = new BuyerReceiver(); 
        		receiver.setBuyerId(getBuyerId());
        		receiver.setActive(true);
        		receiver.setCreated(new Date());
        		receiver.setUpdated(new Date());
        	}else{
        		receiver = BuyerReceiver.dao.findById(receiverId);
        		receiver.setUpdated(new Date());
        	}
        	
        	receiver.setIsDefault(StrKit.isBlank(is_default) || "0".equals(is_default) ? false : true);
        	receiver.setReceiverName(name);
     		receiver.setAddress(address);
     		receiver.setPhone(phone);
     		receiver.setAreaId(new Long(area_id));
     		Area area=Area.dao.findById(area_id);
     		receiver.setAreaTreePath(area.getTreePath());
     		//String area [] = area_name.split(" ");
     		receiver.setProvince(province);
     		receiver.setCity(city);
     		receiver.setDistrict(district);
            
     		List<BuyerReceiver> receivers = BuyerReceiver.dao.find("select * from " + BuyerReceiver.table + " where buyer_id=?", getBuyerId());
            if(receiver.getIsDefault() !=null && receiver.getIsDefault()){//如果当前设置默认，更新其他默认地址为不默认
            	for(BuyerReceiver receiver2 : receivers){
            		if(receiver2.getIsDefault()){
                		receiver2.setIsDefault(false);
                		receiver2.update();	
            		}
            	}
            }
            
            if(receiverId != null){//修改
                receiver.update();
            }else{//新增
                if (receivers != null &&  receivers.size() >= MAX_RECEIVER_COUNT) {
                	rendFailedJson("只允许添加最多" + MAX_RECEIVER_COUNT + "项收货地址!");
                    return;
                }
                receiver.save();
            }
            
            rendSuccessJson(receiver);
        } catch (Exception e) {
            log.error("get receiver error", e);
            rendFailedJson("系统500错误");
        }
    }
	
    
    /**
	 * 地区
	 */
	public void area() {
		Long parentId = getParaToLong("parentId");
		List<Area> areas = new ArrayList<Area>();
		Area parent =Area.dao.findById(parentId);
		if (parent != null) {
			areas =areaService.getChildren(parent.getId());
		} else {
			areas = areaService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (Area area : areas) {
			options.put(area.getId(), area.getName());
		}
		renderJson(options);
	}
}
