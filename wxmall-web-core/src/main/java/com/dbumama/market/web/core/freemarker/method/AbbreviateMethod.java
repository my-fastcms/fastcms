package com.dbumama.market.web.core.freemarker.method;

import java.util.regex.Pattern;

import freemarker.template.SimpleScalar;

/**
 * 模板方法 - 字符串缩略
 * 
 * 
 * 
 */
public class AbbreviateMethod extends WxmMethod {

	/** 中文字符配比 */
	private static final Pattern PATTERN = Pattern.compile("[\\u4e00-\\u9fa5\\ufe30-\\uffa0]+$");

	/**
	 * 
	 * 字符串缩略
	 * 
	 * @param str
	 *            原字符串
	 * @param width
	 *            宽度
	 * @param ellipsis
	 *            省略符
	 * @return 缩略字符
	 */
	private String abbreviate(String str, Integer width, String ellipsis) {
		if (width != null) {
			int strLength = 0;
			for (int strWidth = 0; strLength < str.length(); strLength++) {
				strWidth = PATTERN.matcher(String.valueOf(str.charAt(strLength))).find() ? strWidth + 2 : strWidth + 1;
				if (strWidth >= width) {
					break;
				}
			}
			if (strLength < str.length()) {
				if (ellipsis != null) {
					return str.substring(0, strLength + 1) + ellipsis;
				} else {
					return str.substring(0, strLength + 1);
				}
			} else {
				return str;
			}
		} else {
			if (ellipsis != null) {
				return str + ellipsis;
			} else {
				return str;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.freemarker.method.WxmMethod#onExec()
	 */
	@Override
	public Object onExec() {
		Integer width = null;
		String ellipsis = null;
		if(size() == null || size() <=0 ) return null;
		if (size() == 2) {
			if (getToBigInteger(1) != null) {
				width = getToBigInteger(1).intValue();
			}
		} else if (size() > 2) {
			if (getToBigInteger(1) != null) {
				width = getToBigInteger(1).intValue();
			}
			if (getToBigInteger(2) != null) {
				ellipsis = getToString(2);
			}
		}
		return new SimpleScalar(abbreviate(get(0).toString(), width, ellipsis));
	}

}