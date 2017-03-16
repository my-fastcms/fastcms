package com.dbumama.market.web.core.plugin.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

/**
 * Created by wangrenhui on 14-1-7.
 */
public class JdbcPermissionAuthzHandler extends AbstractAuthzHandler {
	private final String jdbcPermission;

	public JdbcPermissionAuthzHandler(String jdbcPermission) {
		this.jdbcPermission = jdbcPermission;
	}

	@Override
	public void assertAuthorized() throws AuthorizationException {
		//是超级管理员？
		if(SubjectKit.hasRoleAdmin()){
			return;
		}
		
		// 数据库权限
		if (jdbcPermission != null) {
			Subject subject = getSubject();
			subject.checkPermission(jdbcPermission);
			return;
		}
	}
}
