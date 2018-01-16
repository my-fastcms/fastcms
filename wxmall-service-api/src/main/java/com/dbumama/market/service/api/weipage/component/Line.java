package com.dbumama.market.service.api.weipage.component;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Line implements Serializable {
	private String lineColor;
	private String lineType;

	public String getLineColor() {
		return lineColor;
	}

	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

}
