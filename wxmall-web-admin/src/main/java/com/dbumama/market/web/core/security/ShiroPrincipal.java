package com.dbumama.market.web.core.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.SellerUser;

/**
 * 自定义认证主体
 * 
 * @author LiHongYuan
 * 
 */
public class ShiroPrincipal implements Serializable {

	private static final long serialVersionUID = -5216712981159026254L;

	// 用户对象
	private SellerUser admin;
	// 用户权限列表
	private List<String> authorities = new ArrayList<String>();
	// 用户角色列表
	private List<String> roles = new ArrayList<String>();
	// 是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
	// 这里会导致修改权限时，需要重新登录方可有效
	private boolean isAuthorized = false;

	/**
	 * 构造函数，参数为Admin对象 根据Admin对象属性，赋值给Principal相应的属性上
	 * 
	 * @param admin
	 */
	public ShiroPrincipal(SellerUser admin) {
		this.admin = admin;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isAuthorized() {
		return isAuthorized;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public SellerUser getAdmin() {
		return admin;
	}

	public void setAdmin(SellerUser admin) {
		this.admin = admin;
	}

	public String getAdminName() {
		return this.admin.getNick();
	}

	public String getId() {
		return this.admin.getId().toString();
	}

	/**
	 * <shiro:principal/>标签显示中文名称
	 */
	@Override
	public String toString() {
		return this.admin.getNick();
	}
}
