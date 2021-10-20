/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.payment.handler;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.bean.WxPayMessage;
import com.fastcms.payment.listener.PaymentSuccessListenerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信支付回调业务逻辑处理入口
 * @author： wjun_java@163.com
 * @date： 2021/6/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class WxPayMessageHandler implements PayMessageHandler<WxPayMessage, PayService> {

	@Autowired
	private PaymentSuccessListenerManager paymentSuccessListenerManager;

	@Override
	public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, PayService payService) throws PayErrorException {
		//交易状态
		if ("SUCCESS".equals(payMessage.getPayMessage().get("result_code"))) {
//			/////这里进行成功的处理
//			PaymentRecord paymentRecord = paymentRecordService.getPaymentRecordByTrxNo(payMessage.getOutTradeNo());
//			if(paymentRecord != null) {
//				paymentRecord.setThirdpartyTransactionId(payMessage.getTransactionId());
//				paymentRecord.setThirdpartyAppid(payMessage.getAppid());
//				paymentRecord.setThirdpartyMchId(payMessage.getMchId());
//				paymentRecord.setThirdpartyType(WxPaymentPlatform.platformName);
//				paymentSuccessListenerManager.notifySuccess(paymentRecord);
//			}
			return payService.getPayOutMessage("SUCCESS", "OK");
		}

		return payService.getPayOutMessage("FAIL", "失败");
	}

}
