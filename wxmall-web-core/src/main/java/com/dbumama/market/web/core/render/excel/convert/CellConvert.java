package com.dbumama.market.web.core.render.excel.convert;

/**
 *  单元格值转换器
 * @author zhoulei
 *
 */
public interface CellConvert<T> {
	T convert(String val, T obj);
}
