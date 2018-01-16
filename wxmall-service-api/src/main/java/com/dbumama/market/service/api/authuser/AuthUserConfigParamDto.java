package com.dbumama.market.service.api.authuser;

import java.io.File;
import java.util.Date;

import com.dbumama.market.service.api.common.AbstractParamDto;

@SuppressWarnings("serial")
public class AuthUserConfigParamDto extends AbstractParamDto{

	private Long id;
	private String appName;
	private String appId;
	private String appSecret;
	private String adminDomain;
	private String wxDomain;
	private String imgDomain;
	private String msgServerUrl;
	private String msgToken;
	private String msgAesKey;
	private String payMchId;
	private String paySecretKey;
	private String payResultUrl;
	private File certFile;			//公众号证书文件
	private Boolean active;
	private Date created;
	private Date updated;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}
	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * @return the adminDomain
	 */
	public String getAdminDomain() {
		return adminDomain;
	}
	/**
	 * @param adminDomain the adminDomain to set
	 */
	public void setAdminDomain(String adminDomain) {
		this.adminDomain = adminDomain;
	}
	/**
	 * @return the wxDomain
	 */
	public String getWxDomain() {
		return wxDomain;
	}
	/**
	 * @param wxDomain the wxDomain to set
	 */
	public void setWxDomain(String wxDomain) {
		this.wxDomain = wxDomain;
	}
	/**
	 * @return the imgDomain
	 */
	public String getImgDomain() {
		return imgDomain;
	}
	/**
	 * @param imgDomain the imgDomain to set
	 */
	public void setImgDomain(String imgDomain) {
		this.imgDomain = imgDomain;
	}
	/**
	 * @return the msgServerUrl
	 */
	public String getMsgServerUrl() {
		return msgServerUrl;
	}
	/**
	 * @param msgServerUrl the msgServerUrl to set
	 */
	public void setMsgServerUrl(String msgServerUrl) {
		this.msgServerUrl = msgServerUrl;
	}
	/**
	 * @return the msgToken
	 */
	public String getMsgToken() {
		return msgToken;
	}
	/**
	 * @param msgToken the msgToken to set
	 */
	public void setMsgToken(String msgToken) {
		this.msgToken = msgToken;
	}
	/**
	 * @return the msgAesKey
	 */
	public String getMsgAesKey() {
		return msgAesKey;
	}
	/**
	 * @param msgAesKey the msgAesKey to set
	 */
	public void setMsgAesKey(String msgAesKey) {
		this.msgAesKey = msgAesKey;
	}
	/**
	 * @return the payMchId
	 */
	public String getPayMchId() {
		return payMchId;
	}
	/**
	 * @param payMchId the payMchId to set
	 */
	public void setPayMchId(String payMchId) {
		this.payMchId = payMchId;
	}
	/**
	 * @return the paySecretKey
	 */
	public String getPaySecretKey() {
		return paySecretKey;
	}
	/**
	 * @param paySecretKey the paySecretKey to set
	 */
	public void setPaySecretKey(String paySecretKey) {
		this.paySecretKey = paySecretKey;
	}
	/**
	 * @return the payResultUrl
	 */
	public String getPayResultUrl() {
		return payResultUrl;
	}
	/**
	 * @param payResultUrl the payResultUrl to set
	 */
	public void setPayResultUrl(String payResultUrl) {
		this.payResultUrl = payResultUrl;
	}
	/**
	 * @return the certFile
	 */
	public File getCertFile() {
		return certFile;
	}
	/**
	 * @param certFile the certFile to set
	 */
	public void setCertFile(File certFile) {
		this.certFile = certFile;
	}
	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
