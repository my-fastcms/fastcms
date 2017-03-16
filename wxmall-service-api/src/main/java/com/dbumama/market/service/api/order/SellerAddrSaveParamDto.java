package com.dbumama.market.service.api.order;

import com.dbumama.market.service.common.AbstractParamDto;

@SuppressWarnings("serial")
public class SellerAddrSaveParamDto extends AbstractParamDto{

	private Long addrId;
	private Long areaId;
	private Long sellerId;
	private String contactName;		//联系人姓名
	private String city;
	private String country;
	private String province;
	private String addr;			//详细地址
	private String memo;
	private String phone;			//联系人手机
	private String sellerCompany;	//公司名称
	private String zipCode;			//邮编
	
	public SellerAddrSaveParamDto(Long sellerId, String contactName, 
			String city, Long areaId, String province, 
			String addr, String phone){
		this.sellerId = sellerId;
		this.contactName = contactName;
		this.city = city;
		this.areaId = areaId;
		this.province = province;
		this.addr = addr;
		this.phone = phone;
	}
	
	public Long getAddrId() {
		return addrId;
	}
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSellerCompany() {
		return sellerCompany;
	}
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
}
