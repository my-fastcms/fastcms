package com.dbumama.market.web.core.plugin.spring;

import javax.sql.DataSource;

import com.dbumama.market.service.utils.SpringContextUtil;
import com.jfinal.plugin.activerecord.IDataSourceProvider;

/**
 * wjun_java@163.com
 * 2016年7月23日
 */
public class SpringDataSourcePlugin implements IDataSourceProvider{

	/* (non-Javadoc)
	 * @see com.jfinal.plugin.activerecord.IDataSourceProvider#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return (DataSource) SpringContextUtil.getApplicationContext().getBean("dataSourceProxy");
	}

}
