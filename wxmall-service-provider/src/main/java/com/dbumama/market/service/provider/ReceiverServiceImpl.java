package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbumama.market.model.Area;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.receiver.BuyerReceiverSubmitParamDto;
import com.dbumama.market.service.api.receiver.ReceiverException;
import com.dbumama.market.service.api.receiver.ReceiverService;
import com.jfinal.kit.StrKit;

/**
 * 收货地址
 * @author wangjun
 *
 */
@Service("receiverService")
public class ReceiverServiceImpl implements ReceiverService{

	/** 收货地址最大保存数 */
	public static final Integer MAX_RECEIVER_COUNT = 8;
	
	@Autowired
	private AreaService areaService;
	
	private static final BuyerReceiver receiverDao = new BuyerReceiver().dao();
	
	@Override
	public List<BuyerReceiver> getBuyerReceiver(Long buyerId) {
		List<BuyerReceiver> receivers = receiverDao.find("select * from " + 
				BuyerReceiver.table + " where buyer_id=?", buyerId);
		List<BuyerReceiver> receiver=new ArrayList<BuyerReceiver>();
		for (BuyerReceiver buyerReceiver : receivers) {
			Area area=areaService.findById(buyerReceiver.getAreaId());
			buyerReceiver.setAddress(area.getFullName()+buyerReceiver.getAddress());
			receiver.add(buyerReceiver);
		}
		return receiver;
	}

	@Override
	public BuyerReceiver findById(Long receiverId) {
		return receiverDao.findById(receiverId);
	}

	@Override
	public BuyerReceiver save(BuyerReceiverSubmitParamDto submitParam) throws ReceiverException {
		if(submitParam == null 
				|| submitParam.getBuyerId() == null
				|| StrKit.isBlank(submitParam.getAddress()) 
				|| StrKit.isBlank(submitParam.getName()) 
				|| StrKit.isBlank(submitParam.getPhone())
				|| StrKit.isBlank(submitParam.getArea_id())){
    		throw new ReceiverException("请传入完整的参数");
    	}
    	
    	if(StrKit.isBlank(submitParam.getProvince()) || StrKit.isBlank(submitParam.getCity()) /*|| StrKit.isBlank(district) */
    			|| "请选择...".equals(submitParam.getProvince()) || "请选择...".equals(submitParam.getCity()) /*|| "请选择...".equals(district)*/){
    		throw new ReceiverException("请选择省市县区");
    	}
    	
    	final String is_default = submitParam.getIs_default();
    	
    	BuyerReceiver receiver = null;
    	Long receiverId = submitParam.getReceiverId();
    	if(receiverId == null){
    		receiver = new BuyerReceiver(); 
    		receiver.setBuyerId(submitParam.getBuyerId());
    		receiver.setActive(true);
    		receiver.setCreated(new Date());
    		receiver.setUpdated(new Date());
    	}else{
    		receiver = receiverDao.findById(receiverId);
    		receiver.setUpdated(new Date());
    	}
    	
    	receiver.setIsDefault(StrKit.isBlank(is_default) || "0".equals(is_default) ? false : true);
    	receiver.setReceiverName(submitParam.getName());
 		receiver.setAddress(submitParam.getAddress());
 		receiver.setPhone(submitParam.getPhone());
 		receiver.setAreaId(new Long(submitParam.getArea_id()));
 		Area area = areaService.findById(Long.valueOf(submitParam.getArea_id()));
 		receiver.setAreaTreePath(area.getTreePath());
 		//String area [] = area_name.split(" ");
 		receiver.setProvince(submitParam.getProvince());
 		receiver.setCity(submitParam.getCity());
 		receiver.setDistrict(submitParam.getDistrict());
        
 		List<BuyerReceiver> receivers = receiverDao.find("select * from " + BuyerReceiver.table + " where buyer_id=?", submitParam.getBuyerId());
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
            	throw new ReceiverException("只允许添加最多" + MAX_RECEIVER_COUNT + "项收货地址!");
            }
            receiver.save();
        }
        return receiver;
	}

	@Override
	public BuyerReceiver getDefaultReceiver(Long buyerId) {
		return receiverDao.findFirst("SELECT * FROM "+BuyerReceiver.table+" WHERE buyer_id = ? AND is_default = 1 ", buyerId);
	}

}
