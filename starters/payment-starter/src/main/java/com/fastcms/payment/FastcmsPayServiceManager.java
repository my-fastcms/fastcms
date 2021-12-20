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
import com.fastcms.payment.bean.FastcmsPayOrder;
import com.fastcms.payment.bean.FastcmsQueryOrder;
import com.fastcms.payment.config.PayConfigStorageService;
import com.fastcms.payment.config.PaymentPlatformConfig;
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
	private PayConfigStorageService payConfigStorageService;

	@Override
	public boolean verify(String platform, Map<String, Object> params) {
		return payConfigStorageService.getConfig(platform).getPayService().verify(new NoticeParams(params));
	}

	@Override
	public Map<String, Object> getParameter2Map(String platform, Map<String, String[]> parameterMap, InputStream is) {
		return getNoticeParams(platform, new DefaultNoticeRequest( parameterMap, is)).getBody();
	}

	@Override
	public NoticeParams getNoticeParams(String platform, NoticeRequest request) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return config.getPayService().getNoticeParams(request);
	}

	@Override
	public String toPay(FastcmsPayOrder payOrder) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(payOrder.getPlatform());
		payOrder.setTransactionType(config.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
		PayService payService = config.getPayService();
		Map<String, Object> orderInfo = payService.orderInfo(payOrder);
		return payService.buildRequest(orderInfo, MethodType.POST);
	}

	@Override
	public Map<String, Object> app(FastcmsPayOrder payOrder) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(payOrder.getPlatform());
		payOrder.setTransactionType(config.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
		PayService payService = config.getPayService();
		return payService.app(payOrder);
	}

	@Override
	public Map<String, Object> getOrderInfo(FastcmsPayOrder payOrder) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(payOrder.getPlatform());
		payOrder.setTransactionType(config.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
		PayService payService = config.getPayService();
		Map<String, Object> orderInfo = payService.orderInfo(payOrder);
		return orderInfo;
	}

	@Override
	public Map<String, Object> microPay(FastcmsPayOrder payOrder) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(payOrder.getPlatform());
		payOrder.setTransactionType(config.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
		Map<String, Object> params = config.getPayService().microPay(payOrder);
		return params;
	}

	@Override
	public byte[] toQrPay(FastcmsPayOrder payOrder) throws IOException {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(payOrder.getPlatform());
		payOrder.setTransactionType(config.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(config.getPayService().genQrPay(payOrder), "JPEG", baos);
		return baos.toByteArray();
	}

	@Override
	public String getQrPay(FastcmsPayOrder payOrder) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(payOrder.getPlatform());
		payOrder.setTransactionType(config.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
		return config.getPayService().getQrPay(payOrder);
	}

	@Override
	public String payBack(String platform, Map<String, String[]> parameterMap, InputStream is) throws IOException {
		return payBack(platform, new DefaultNoticeRequest(parameterMap, is));
	}

	@Override
	public String payBack(String platform, NoticeRequest request) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		PayService payService = config.getPayService();
		return payService.payBack(request).toMessage();
	}

	@Override
	public Map<String, Object> query(FastcmsPayOrder order) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(order.getPlatform());
		return config.getPayService().query(order);
	}

	@Override
	public Map<String, Object> close(FastcmsPayOrder order) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(order.getPlatform());
		final FastcmsQueryOrder queryOrder = new FastcmsQueryOrder();
		queryOrder.setTradeNo(order.getTradeNo());
		queryOrder.setOutTradeNo(order.getOutTradeNo());
		return config.getPayService().close(queryOrder);
	}

	@Override
	public RefundResult refund(String platform, RefundOrder order) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return config.getPayService().refund(order);
	}

	@Override
	public Map<String, Object> refundQuery(String platform, RefundOrder order) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return config.getPayService().refundquery(order);
	}

	@Override
	public Map<String, Object> downloadBill(FastcmsQueryOrder order) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(order.getPlatform());
		return config.getPayService().downloadBill(order.getBillDate(), order.getBillType());
	}

	@Override
	public Map<String, Object> transfer(String platform, TransferOrder order) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return config.getPayService().transfer(order);
	}

	@Override
	public Map<String, Object> transferQuery(String platform, String outNo, String tradeNo) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return config.getPayService().transferQuery(outNo, tradeNo);
	}

	@Override
	public PayMessage createMessage(String platform, Map<String, Object> message) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return config.getPayService().createMessage(message);
	}

	@Override
	public <T extends PayService> T cast(String platform, Class<T> payServiceClass) {
		PaymentPlatformConfig config = payConfigStorageService.getConfig(platform);
		return (T) config.getPayService();
	}
}
