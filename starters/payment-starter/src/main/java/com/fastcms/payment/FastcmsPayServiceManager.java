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
package com.fastcms.payment;

import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.*;
import com.egzosn.pay.common.bean.result.PayException;
import com.egzosn.pay.common.exception.PayErrorException;
import com.fastcms.payment.bean.FastcmsPayOrder;
import com.fastcms.payment.bean.FastcmsQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsPayServiceManager implements PayServiceManager {

	@Autowired
	private PaymentPlatformService paymentPlatformService;

	@Override
	public boolean verify(String platform, Map<String, Object> params) {
		return getPaymentPlatform(platform).getPayService().verify(new NoticeParams(params));
	}

	@Override
	public Map<String, Object> getParameter2Map(String platform, Map<String, String[]> parameterMap, InputStream is) {
		return getNoticeParams(platform, new DefaultNoticeRequest( parameterMap, is)).getBody();
	}

	@Override
	public NoticeParams getNoticeParams(String platform, NoticeRequest request) {
		return getPaymentPlatform(platform).getPayService().getNoticeParams(request);
	}

	@Override
	public String toPay(FastcmsPayOrder payOrder) {
		PaymentPlatform paymentPlatform = getPaymentPlatform(payOrder.getPlatform());
		payOrder.setTransactionType(paymentPlatform.getTransactionType(payOrder.getWayTrade()));
		PayService payService = paymentPlatform.getPayService();
		Map<String, Object> orderInfo = payService.orderInfo(payOrder);
		return payService.buildRequest(orderInfo, MethodType.POST);
	}

	@Override
	public Map<String, Object> app(FastcmsPayOrder payOrder) {
		PaymentPlatform paymentPlatform = getPaymentPlatform(payOrder.getPlatform());
		payOrder.setTransactionType(paymentPlatform.getTransactionType(payOrder.getWayTrade()));
		PayService payService = paymentPlatform.getPayService();
		return payService.app(payOrder);
	}

	@Override
	public Map<String, Object> getOrderInfo(FastcmsPayOrder payOrder) {
		PaymentPlatform paymentPlatform = getPaymentPlatform(payOrder.getPlatform());
		payOrder.setTransactionType(paymentPlatform.getTransactionType(payOrder.getWayTrade()));
		PayService payService = paymentPlatform.getPayService();
		Map<String, Object> orderInfo = payService.orderInfo(payOrder);
		return orderInfo;
	}

	@Override
	public Map<String, Object> microPay(FastcmsPayOrder payOrder) {
		PaymentPlatform paymentPlatform = getPaymentPlatform(payOrder.getPlatform());
		payOrder.setTransactionType(paymentPlatform.getTransactionType(payOrder.getWayTrade()));
		Map<String, Object> params = paymentPlatform.getPayService().microPay(payOrder);
		return params;
	}

	@Override
	public byte[] toQrPay(FastcmsPayOrder payOrder) throws IOException {
		PaymentPlatform paymentPlatform = getPaymentPlatform(payOrder.getPlatform());
		payOrder.setTransactionType(paymentPlatform.getTransactionType(payOrder.getWayTrade()));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(paymentPlatform.getPayService().genQrPay(payOrder), "JPEG", baos);
		return baos.toByteArray();
	}

	@Override
	public String getQrPay(FastcmsPayOrder payOrder) {
		PaymentPlatform paymentPlatform = getPaymentPlatform(payOrder.getPlatform());
		payOrder.setTransactionType(paymentPlatform.getTransactionType(payOrder.getWayTrade()));
		return paymentPlatform.getPayService().getQrPay(payOrder);
	}

	@Override
	public String payBack(String platform, Map<String, String[]> parameterMap, InputStream is) throws IOException {
		return payBack(platform, new DefaultNoticeRequest(parameterMap, is));
	}

	@Override
	public String payBack(String platform, NoticeRequest request) {
		return getPaymentPlatform(platform).getPayService().payBack(request).toMessage();
	}

	@Override
	public Map<String, Object> query(FastcmsPayOrder order) {
		return getPaymentPlatform(order.getPlatform()).getPayService().query(order);
	}

	@Override
	public Map<String, Object> close(FastcmsPayOrder order) {
		PaymentPlatform paymentPlatform = getPaymentPlatform(order.getPlatform());
		final FastcmsQueryOrder queryOrder = new FastcmsQueryOrder();
		queryOrder.setTradeNo(order.getTradeNo());
		queryOrder.setOutTradeNo(order.getOutTradeNo());
		return paymentPlatform.getPayService().close(queryOrder);
	}

	@Override
	public RefundResult refund(String platform, RefundOrder order) {
		return getPaymentPlatform(platform).getPayService().refund(order);
	}

	@Override
	public Map<String, Object> refundQuery(String platform, RefundOrder order) {
		return getPaymentPlatform(platform).getPayService().refundquery(order);
	}

	@Override
	public Map<String, Object> downloadBill(FastcmsQueryOrder order) {
		return getPaymentPlatform(order.getPlatform()).getPayService().downloadBill(order.getBillDate(), order.getBillType());
	}

	@Override
	public Map<String, Object> transfer(String platform, TransferOrder order) {
		return getPaymentPlatform(platform).getPayService().transfer(order);
	}

	@Override
	public Map<String, Object> transferQuery(String platform, String outNo, String tradeNo) {
		return getPaymentPlatform(platform).getPayService().transferQuery(outNo, tradeNo);
	}

	@Override
	public PayMessage createMessage(String platform, Map<String, Object> message) {
		return getPaymentPlatform(platform).getPayService().createMessage(message);
	}

	@Override
	public <T extends PayService> T cast(String platform, Class<T> payServiceClass) {
		return (T) getPaymentPlatform(platform).getPayService();
	}

	protected PaymentPlatform getPaymentPlatform(String platform) {
		PaymentPlatform paymentPlatform = paymentPlatformService.getPlatform(platform);
		if (paymentPlatform == null) throw new PayErrorException(new PayException("500", "未找到支付平台插件," + platform));
		return paymentPlatform;
	}

}
