package com.dbumama.market.service.api.qiandao;

/**
 * wjun_java@163.com
 * 2016年7月20日
 */
@SuppressWarnings("serial")
public class QiandaoSubmitParamDto extends QiandaoParamDto{

	public QiandaoSubmitParamDto(int pageNo) {
		super(pageNo);
	}

	private Long qiandaoId;
	
	public Long getQiandaoId() {
		return qiandaoId;
	}

	public void setQiandaoId(Long qiandaoId) {
		this.qiandaoId = qiandaoId;
	}
	
}
