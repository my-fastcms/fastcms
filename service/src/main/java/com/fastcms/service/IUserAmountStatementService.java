package com.fastcms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.UserAmountStatement;

import java.io.Serializable;

/**
 * 用户余额流水情况
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
public interface IUserAmountStatementService extends IService<UserAmountStatement> {

	/**
	 * 获取用户账户变动流水
	 * @param pageParam
	 * @param action
	 * @param status
	 * @return
	 */
	Page<UserAmountStatementVo> pageUserAmountStatement(Page pageParam, String action, Integer status);

	class UserAmountStatementVo extends UserAmountStatement implements Serializable {

		private String nickName;

		private String prodTitle;

		private String orderTitle;

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getProdTitle() {
			return prodTitle;
		}

		public void setProdTitle(String prodTitle) {
			this.prodTitle = prodTitle;
		}

		public String getOrderTitle() {
			return orderTitle;
		}

		public void setOrderTitle(String orderTitle) {
			this.orderTitle = orderTitle;
		}
	}

}
