package com.dbumama.market.web.core.plugin.shiro;

import java.util.Map;

/**
 * Created by wangrenhui on 14-1-7.
 */
public interface JdbcAuthzService {
	public Map<String, AuthzHandler> getJdbcAuthz();
}
