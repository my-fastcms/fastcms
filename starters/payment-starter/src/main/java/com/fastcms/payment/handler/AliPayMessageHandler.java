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

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付宝支付回调统一处理器
 * @author： wjun_java@163.com
 * @date： 2021/6/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class AliPayMessageHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

	@Override
	public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
		//com.egzosn.pay.demo.entity.PayType.getPayService()#48
		Object payId = payService.getPayConfigStorage().getAttach();

		Map<String, Object> message = payMessage.getPayMessage();
		//交易状态
		String trade_status = (String) message.get("trade_status");

		//上下文对象中获取账单
//        AmtApply amtApply = (AmtApply)context.get("amtApply");
		//日志存储
//        amtPaylogService.createAmtPaylogByCallBack(amtApply,  message.toString());
		//交易完成
		if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {

			BigDecimal payAmount = new BigDecimal((String) message.get("total_fee"));

			return payService.getPayOutMessage("success", "成功");

		}/* else if ("WAIT_BUYER_PAY".equals(trade_status) || "TRADE_CLOSED".equals(trade_status)) {

        }*/

		return payService.getPayOutMessage("fail", "失败");
	}

}
