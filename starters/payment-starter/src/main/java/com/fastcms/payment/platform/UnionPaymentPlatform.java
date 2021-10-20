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
package com.fastcms.payment.platform;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.union.api.UnionPayConfigStorage;
import com.egzosn.pay.union.api.UnionPayService;
import com.egzosn.pay.union.bean.UnionTransactionType;
import com.fastcms.payment.handler.UnionPayMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 银联支付平台
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration(value = UnionPaymentPlatform.platformName, proxyBeanMethods = false)
public class UnionPaymentPlatform implements PaymentPlatform {

    public static final String platformName = "unionPay";

    @Autowired
    private UnionPayMessageHandler unionPayMessageHandler;

    /**
     * 获取商户平台
     *
     * @return 商户平台
     */
    @Override
    public String getPlatform() {
        return platformName;
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage 支付配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage) {
        if (payConfigStorage instanceof UnionPayConfigStorage) {
            UnionPayService unionPayService = new UnionPayService((UnionPayConfigStorage) payConfigStorage);
            unionPayService.setPayMessageHandler(unionPayMessageHandler);
            return unionPayService;
        }
        UnionPayConfigStorage configStorage = new UnionPayConfigStorage();
        configStorage.setMerId(payConfigStorage.getPid());
        //是否为证书签名
        configStorage.setCertSign(true);
        configStorage.setPid(payConfigStorage.getPid());
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
        configStorage.setSignType(payConfigStorage.getSignType());
        configStorage.setPayType(payConfigStorage.getPayType());
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setTest(payConfigStorage.isTest());
        UnionPayService unionPayService = new UnionPayService(configStorage);
        unionPayService.setPayMessageHandler(unionPayMessageHandler);
        return unionPayService;
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage  支付配置
     * @param httpConfigStorage 网络配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage, HttpConfigStorage httpConfigStorage) {
        PayService payService = getPayService(payConfigStorage);
        payService.setRequestTemplateConfigStorage(httpConfigStorage);
        return payService;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        return UnionTransactionType.valueOf(name);
    }


}
