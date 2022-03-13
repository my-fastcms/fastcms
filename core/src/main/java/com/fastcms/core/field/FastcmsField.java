package com.fastcms.core.field;

import java.io.Serializable;
import java.util.List;

/**
 * <div class="mb-3">
 * 		<label for="fieldName">字段名称</label>
 * 		<div class="input-group">
 *    		<input type="text" class="form-control" id="fieldName" name="fieldName" placeholder="字段名称">
 * 		</div>
 * </div>
 *
 * @author： wjun_java@163.com
 * @date： 2022/3/13
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface FastcmsField {

	/**
	 * <label for="fieldName">${label}</label>
	 * 渲染的html字段的label
	 * @return
	 */
	String getLabel();

	/**
	 * <input type="${type}">
	 * @return
	 */
	String getType();

	/**
	 * <input type="text" id="${fieldId}">
	 * @return
	 */
	String getFieldId();

	/**
	 * <input type="text" name="${name}">
	 * @return
	 */
	String getName();

	/**
	 * <input type="text" placeholder="${placeholder}">
	 * @return
	 */
	String getPlaceholder();

	/**
	 * 支持select等多项值的选择
	 * @return
	 */
	List<Options> getOptions();

	class Options implements Serializable {
		String label;
		String value;

		public Options(String label, String value) {
			this.label = label;
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

}
