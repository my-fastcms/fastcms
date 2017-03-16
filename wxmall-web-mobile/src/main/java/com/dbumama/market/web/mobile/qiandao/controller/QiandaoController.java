package com.dbumama.market.web.mobile.qiandao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.Qiandao;
import com.dbumama.market.model.QiandaoConfig;
import com.dbumama.market.model.QiandaoUser;
import com.dbumama.market.service.api.qiandao.QiandaoException;
import com.dbumama.market.service.api.qiandao.QiandaoParamDto;
import com.dbumama.market.service.api.qiandao.QiandaoService;
import com.dbumama.market.service.api.qiandao.QiandaoSubmitParamDto;
import com.dbumama.market.service.api.qiandao.QiandaoSubmitResultDto;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

/**
 * wjun_java@163.com
 * 2016年7月8日
 */
@RouteBind(path="qiandao")
public class QiandaoController extends BaseMobileController{

	@BY_NAME
	private QiandaoService qiandaoService;
	
	public void index(){
		QiandaoParamDto qiandaoParamDto = new QiandaoParamDto(getPageNo());
		qiandaoParamDto.setSeller(getSellerUser());
		qiandaoParamDto.setBuyer(getBuyerUser());
		try {
			String qiandaoJsonData = qiandaoService.getCurrQiandao(qiandaoParamDto);
			JSONObject json = JSON.parseObject(qiandaoJsonData);
			setAttr("end_timestamp", json.getString("end_timestamp"));
			setAttr("qiandao", JSON.parseObject(json.getString("qiandao"), Qiandao.class));
			setAttr("qiandaoItems", JSON.parseArray(json.getString("qiandaoItems"),  QiandaoConfig.class));
			setAttr("qiandaoUser", JSON.parseObject(json.getString("qiandaoUser"), QiandaoUser.class));
			setAttr("qiandaoRecords", JSON.parseArray(json.getString("qiandaoRecords"), String.class));
			render("qd_index.html");
		} catch (QiandaoException e) {
			setAttr("error", e.getMessage());
			render("qd_noqiandao.html");
		}
	}
	
	/**
	 * 提交签到
	 */
	public void submit(){
		try {
			QiandaoSubmitParamDto qiandaoSubmitParamDto = new QiandaoSubmitParamDto(0);
			qiandaoSubmitParamDto.setQiandaoId(getParaToLong("qiandaoId"));
			qiandaoSubmitParamDto.setBuyer(getBuyerUser());
			qiandaoSubmitParamDto.setSeller(getSellerUser());
			QiandaoSubmitResultDto qiandaoResult = qiandaoService.submit(qiandaoSubmitParamDto);
			rendSuccessJson(qiandaoResult);
		} catch (QiandaoException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
}
