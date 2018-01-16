package com.dbumama.market.service.api.qiandao;

import java.util.List;

import com.dbumama.market.model.Qiandao;
import com.dbumama.market.model.QiandaoConfig;
import com.dbumama.market.model.SellerUser;
import com.jfinal.plugin.activerecord.Page;

/**
 * wjun_java@163.com
 * 2016年7月2日
 */
public interface QiandaoService {
	/**
	 * @param activity
	 * @param items
	 * @param sellerUser
	 * @throws ActivityServiceException
	 */
	public Qiandao doSave(Qiandao qiandao, String items, SellerUser sellerUser) throws QiandaoException;
	
	/**
	 * @param activity
	 * @param items
	 * @param sellerUser
	 * @throws ActivityServiceException
	 */
	public Qiandao doUpdate(Qiandao qiandao, String items, SellerUser sellerUser) throws QiandaoException;
	
	/**
	 * 手机端api：根据单前时间获取是否有签到活动
	 * @throws QiandaoException
	 */
	public String getCurrQiandao(QiandaoParamDto qiandaoParamDto) throws QiandaoException;
	
	/**
	 * 手机端api：提交签到
	 * @param qiandao
	 * @param buyerNick
	 * @param sellerNick
	 * @return
	 * @throws QiandaoException
	 */
	public QiandaoSubmitResultDto submit(QiandaoSubmitParamDto qiandaoSubmitParamDto) throws QiandaoException;
	
	Qiandao findById(Long qiandaoId);
	Page<QiandaoResultDto> page(Long sellerId, Integer pageNo, Integer pageSize, String name);
	List<QiandaoConfig> getQiandaoConfig(Long qiandaoId);
}
