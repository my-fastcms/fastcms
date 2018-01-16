package com.dbumama.market.service.api.common;

/**
 * wjun_java@163.com
 * 2016年7月8日
 */
@SuppressWarnings("serial")
public abstract class AbstractPageParamDto extends AbstractParamDto{

	private Integer pageNo;
	private Integer pageSize;
	
	protected AbstractPageParamDto(Integer pageNo, Integer pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	protected AbstractPageParamDto(Integer pageNo){
		this(pageNo, 15);
	}
	
	protected AbstractPageParamDto(Long sellerId, Integer pageNo){
		this(pageNo);
		this.sellerId = sellerId;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
