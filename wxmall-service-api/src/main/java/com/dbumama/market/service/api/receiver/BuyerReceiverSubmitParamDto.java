package com.dbumama.market.service.api.receiver;

import com.dbumama.market.service.api.common.AbstractParamDto;

@SuppressWarnings("serial")
public class BuyerReceiverSubmitParamDto extends AbstractParamDto{
	String address;
	String name;
	String phone;
	String area_id;
	String province;
	String city;
	String district;
	String is_default;
	Long buyerId;
	Long receiverId;
	
	public BuyerReceiverSubmitParamDto(Long buyerId, String address, String name, String phone, String area_id, String province,
			String city, String district) {
		super();
		this.buyerId = buyerId;
		this.address = address;
		this.name = name;
		this.phone = phone;
		this.area_id = area_id;
		this.province = province;
		this.city = city;
		this.district = district;
	}
	
	public String getIs_default() {
		return is_default;
	}
	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
}
