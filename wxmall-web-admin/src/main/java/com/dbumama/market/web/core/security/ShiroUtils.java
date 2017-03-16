package com.dbumama.market.web.core.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.dbumama.market.model.SellerUser;

/**
 * 工具类
 * 
 * @author LiHongYuan
 * 
 */
public class ShiroUtils {
	/**
	 * 返回当前登录的认证实体AdminId
	 * 
	 * @return
	 */
	public static String getLoginAdminId() {
		ShiroPrincipal principal = getAdminPrincipal();
		if (principal != null)
			return principal.getId();
		return "";
	}

	
	
	/**
	 * 返回当前登录的认证实体Admin
	 * 
	 * @return
	 */
	public static SellerUser getLoginAdmin() {
		ShiroPrincipal principal = getAdminPrincipal();
		if (principal != null)
			return principal.getAdmin();
		return null;
	}

	
	
	/**
	 * 获取当前登录的Admin认证实体
	 * 
	 * @return
	 */
	public static ShiroPrincipal getAdminPrincipal() {
		Subject subject = SecurityUtils.getSubject();
		return (ShiroPrincipal) subject.getPrincipal();
	}

	/**
	 * 获取当前认证Admin实体的中文名称
	 * 
	 * @return
	 */
	public static String getAdminFullname() {
		ShiroPrincipal principal = getAdminPrincipal();
		if (principal != null)
			return principal.toString();
		return "";
	}

	
	
	/**
	 * 获取当前认证Admin实体的登录名称
	 * 
	 * @return
	 */
	public static String getLoginAdminName() {
		ShiroPrincipal principal = getAdminPrincipal();
		if (principal != null)
			return principal.getAdminName();
		throw new RuntimeException("user's name is null.");
	}
	
	

}
