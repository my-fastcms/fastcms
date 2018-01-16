package com.dbumama.market.web.mobile.receiver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbumama.market.model.Area;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.receiver.BuyerReceiverSubmitParamDto;
import com.dbumama.market.service.api.receiver.ReceiverService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path = "receiver")
public class ReceiverController extends BaseMobileController{
	@Inject.BY_NAME
	private AreaService areaService;
	@BY_NAME
	private ReceiverService receiverService;
	
	public void index(){
		List<BuyerReceiver> receivers = receiverService.getBuyerReceiver(getBuyerId());
		setAttr("receiverList", receivers);
		render("/receiver/index.html");
	}
	
	public void add() {
        render("/receiver/receiver-input.html");
    }

    public void edit() {
        BuyerReceiver receiver = receiverService.findById(getParaToLong("id"));
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
        	final String is_default = getPara("is_default");
        	final Long receiverId = getParaToLong("id");
            
        	BuyerReceiverSubmitParamDto submitParam = new BuyerReceiverSubmitParamDto(getBuyerId(), address, name, phone, area_id, province, city, district);
        	submitParam.setIs_default(is_default);
        	submitParam.setReceiverId(receiverId);
            rendSuccessJson(receiverService.save(submitParam));
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
		Area parent = areaService.findById(parentId);
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
