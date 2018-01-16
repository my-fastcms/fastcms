/**
 * 文件名:DrawResult.java
 * 版本信息:1.0
 * 日期:2015-6-28
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.api.lottery;

import java.io.Serializable;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-6-28
 */
public class DrawResultDto implements Serializable{

	/**
	 * @author: wjun.java@gmail.com
	 * @date: 2015-6-28
	 */
	private static final long serialVersionUID = 1L;

	private Integer index; 		//中奖的奖品索引
	private String type;		//奖品类型
	private String name;		//奖品名称
	private Integer outed;		//已出奖品数量
	private Boolean luck; 		//是否中奖
	
	public DrawResultDto(){
		setIndex(-1);
		setType("");
		setOuted(0);
		setName("");
		setLuck(false);
	}
	
	public Boolean getLuck() {
		return luck;
	}
	public void setLuck(Boolean luck) {
		this.luck = luck;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOuted() {
		return outed;
	}
	public void setOuted(int outed) {
		this.outed = outed;
	}
	
}
