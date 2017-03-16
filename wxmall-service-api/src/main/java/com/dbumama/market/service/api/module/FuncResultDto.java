package com.dbumama.market.service.api.module;

import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class FuncResultDto extends AbstractResultDto {

	private String qrcode;
	private String funcUrl;
	
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getFuncUrl() {
		return funcUrl;
	}
	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}
	
}
