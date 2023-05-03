package com.fastcms.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.ArticleZan;
import com.fastcms.common.exception.FastcmsException;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2023-05-01
 */
public interface IArticleZanService extends IService<ArticleZan> {

	/**
	 * 获取文章点赞列表
	 * @param pageParam
	 * @param articleId
	 * @return
	 */
	Page<ArticleZanVo> pageArticleZan(Page pageParam, Long articleId);

	/**
	 * 点赞
	 * @param userId
	 * @param articleId
	 * @return
	 */
	ArticleZan saveZan(Long userId, Long articleId) throws FastcmsException;

	/**
	 * 取消点赞
	 * @param userId
	 * @param articleId
	 * @return
	 */
	boolean cancelZan(Long userId, Long articleId) throws FastcmsException;

	/**
	 * 用户是否赞过
	 * @param userId
	 * @param articleId
	 * @return
	 */
	boolean isUserHadZan(Long userId, Long articleId);

	/**
	 * 用户点赞
	 */
	class ArticleZanVo extends ArticleZan implements Serializable {
		/**
		 * 点赞用户昵称
		 */
		String nickName;

		/**
		 * 点赞用户头像
		 */
		String headImg;

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getHeadImg() {
			return headImg;
		}

		public void setHeadImg(String headImg) {
			this.headImg = headImg;
		}
	}

}
