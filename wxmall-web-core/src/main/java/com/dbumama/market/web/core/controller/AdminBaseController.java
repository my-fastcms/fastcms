/**
 * 文件名:AdminBaseController.java
 * 版本信息:1.0
 * 日期:2015-5-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.core.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-9
 */
public abstract class AdminBaseController <M extends Model<M>> extends BaseController {
	
	protected abstract Class<M> getModelClass();
	
	/**
	 * 设置操作人员id及操作时间
	 * @param m
	 * @param uid
	 */
	protected void setTrace(Model<M> m) {
		Date date=new Date();
		
		String now = DateTimeUtil.FORMAT_YYYY_MM_DDHHMMSS.format(date);
		if (m.getLong("id") !=null) {
			m.put("updated", now);//修改时间
		} else {
			m.put("created", now);//创建时间
			m.put("updated", now);
		}
	}
	
	protected Model<M> get(String id) {
		if(StringUtils.isEmpty(id))
			return null;
		try {
			Model<M> model_ = getModelClass().newInstance();
			return model_.findById(id);
		} catch (Exception e) {log.error("查询Model异常，id："+id,e);}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected M getModel() throws Exception {
		return (M)getModelExt(getModelClass());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Model getModelExt(Class clz) throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			Model model_ = (Model) clz.newInstance();
			Table tableInfo = TableMapping.me().getTable(clz);
			model_._getAttrNames();
			Enumeration<String> attrNames = request.getParameterNames();
			while(attrNames.hasMoreElements()) {
				String attr = attrNames.nextElement();
				Class<?> colType = null;
				if (tableInfo.hasColumnLabel(attr.toLowerCase()))
					colType = tableInfo.getColumnType(attr.toLowerCase());
				if (tableInfo.hasColumnLabel(attr.toUpperCase())) {
					colType = tableInfo.getColumnType(attr.toUpperCase());
				}
				if (colType != null) {
					String value = request.getParameter(attr);
					model_.set(attr.toLowerCase(), value != null ? convert(colType, value): null);
				}
			}
			/**从request流中取json*/
			if(model_._getAttrValues()==null||model_._getAttrValues().length==0){
				String json = inputStream2String(this.getRequest().getInputStream());
				model_ = JSON.parseObject(json,model_.getClass());
			}
			setTrace(model_);
			return model_;
		}catch(Exception e){
			throw e;
		}		
	}
	
	private static final int timeStampLen = "2011-01-18 16:18:18".length();
	private static final String timeStampPattern = "yyyy-MM-dd HH:mm:ss";
	private static final String datePattern = "yyyy-MM-dd";
	
	/**
	 * test for all types of mysql
	 * 
	 * 表单提交测试结果:
	 * 1: 表单中的域,就算不输入任何内容,也会传过来 "", 也即永远不可能为 null.
	 * 2: 如果输入空格也会提交上来
	 * 3: 需要考 model中的 string属性,在传过来 "" 时是该转成 null还是不该转换,
	 *    我想, 因为用户没有输入那么肯定是 null, 而不该是 ""
	 * 
	 * 注意: 1:当clazz参数不为String.class, 且参数s为空串blank的情况,
	 *       此情况下转换结果为 null, 而不应该抛出异常
	 *      2:调用者需要对被转换数据做 null 判断，参见 ModelInjector 的两处调用
	 */
	 final Object convert(Class<?> clazz, String s) throws ParseException {
		// mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
		if (clazz == String.class) {
			return (StringUtils.isEmpty(s) ? null : s);	// 用户在表单域中没有输入内容时将提交过来 "", 因为没有输入,所以要转成 null.
		}
		s = s.trim();
		if (StringUtils.isEmpty(s)) {	// 前面的 String跳过以后,所有的空字符串全都转成 null,  这是合理的
			return null;
		}
		// 以上两种情况无需转换,直接返回, 注意, 本方法不接受null为 s 参数(经测试永远不可能传来null, 因为无输入传来的也是"")
		
		Object result = null;
		// mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
		if (clazz == Integer.class || clazz == int.class) {
			result = Integer.parseInt(s);
		}
		// mysql type: bigint
		else if (clazz == Long.class || clazz == long.class) {
			result = Long.parseLong(s);
		}
		// 经测试java.util.Data类型不会返回, java.sql.Date, java.sql.Time,java.sql.Timestamp 全部直接继承自 java.util.Data, 所以 getDate可以返回这三类数据
		else if (clazz == java.util.Date.class) {
        	if (s.length() >= timeStampLen) {	// if (x < timeStampLen) 改用 datePattern 转换，更智能
        		// Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
        		// result = new java.util.Date(java.sql.Timestamp.valueOf(s).getTime());	// error under jdk 64bit(maybe)
        		result = new SimpleDateFormat(timeStampPattern).parse(s);
        	}
			else {
				// result = new java.util.Date(java.sql.Date.valueOf(s).getTime());	// error under jdk 64bit
				result = new SimpleDateFormat(datePattern).parse(s);
			}
        }
		// mysql type: date, year
        else if (clazz == java.sql.Date.class) {
        	if (s.length() >= timeStampLen) {	// if (x < timeStampLen) 改用 datePattern 转换，更智能
        		// result = new java.sql.Date(java.sql.Timestamp.valueOf(s).getTime());	// error under jdk 64bit(maybe)
        		result = new java.sql.Date(new SimpleDateFormat(timeStampPattern).parse(s).getTime());
        	}
        	else {
        		// result = new java.sql.Date(java.sql.Date.valueOf(s).getTime());	// error under jdk 64bit
        		result = new java.sql.Date(new SimpleDateFormat(datePattern).parse(s).getTime());
        	}
        }
		// mysql type: time
        else if (clazz == java.sql.Time.class) {
        	result = java.sql.Time.valueOf(s);
		}
		// mysql type: timestamp, datetime
        else if (clazz == java.sql.Timestamp.class) {
        	result = java.sql.Timestamp.valueOf(s);
		}
		// mysql type: real, double
        else if (clazz == Double.class) {
        	result = Double.parseDouble(s);
		}
		// mysql type: float
        else if (clazz == Float.class) {
        	result = Float.parseFloat(s);
		}
		// mysql type: bit, tinyint(1)
        else if (clazz == Boolean.class) {
        	result = Boolean.parseBoolean(s) || "1".equals(s);
		}
		// mysql type: decimal, numeric
        else if (clazz == java.math.BigDecimal.class) {
        	result = new java.math.BigDecimal(s);
		}
		// mysql type: unsigned bigint
		else if (clazz == java.math.BigInteger.class) {
			result = new java.math.BigInteger(s);
		}
		// mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob. I have not finished the test.
        else if (clazz == byte[].class) {
			result = s.getBytes();
		}
		else {
			throw new RuntimeException(clazz.getName() + " can not be converted, please use other type of attributes in your model!");
        }
		
		return result;
	}
	
}
