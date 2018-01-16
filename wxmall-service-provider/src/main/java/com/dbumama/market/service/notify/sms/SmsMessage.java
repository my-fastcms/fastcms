package com.dbumama.market.service.notify.sms;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SmsMessage implements Serializable{

	private String rec_num;
	private String sign_name;
	private String param;
	private String template;
	private String content;

	public String getRec_num() {
		return rec_num;
	}

	public void setRec_num(String rec_num) {
		this.rec_num = rec_num;
	}

	public String getSign_name() {
		return sign_name;
	}

	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static SmsMessage create() {
		return new SmsMessage();
	}

	public void send() {
		SmsSenderFactory.createSender().send(this);
	}

}
