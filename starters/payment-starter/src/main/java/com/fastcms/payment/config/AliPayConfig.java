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
package com.fastcms.payment.config;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.fastcms.payment.platform.AliPaymentPlatform;
import com.fastcms.payment.platform.PaymentPlatform;
import com.fastcms.payment.platform.PaymentPlatforms;

/**
 * 支付宝支付配置
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AliPayConfig extends AliPayConfigStorage implements PaymentPlatformConfig {

    /**
     * 商户对应的支付服务
     */
    private volatile PayService payService;

    /**
     * 支付平台
     */
    private PaymentPlatform platform;

    /**
     * HTTP请求配置
     */
    private HttpConfigStorage httpConfigStorage;

    public AliPayConfig() {
        String platformName = AliPaymentPlatform.platformName;
        setPayType(platformName);
        platform = PaymentPlatforms.getPaymentPlatform(platformName);
    }

    /**
     * 获取支付平台
     *
     * @return 支付平台
     */
    @Override
    public PaymentPlatform getPaymentPlatform() {
        return platform;
    }

    @Override
    public HttpConfigStorage getHttpConfigStorage() {
        return httpConfigStorage;
    }

    /**
     * 获取支付平台对应的支付服务
     * @return 支付服务
     */
    @Override
    public PayService getPayService() {
        if (null == payService){
            payService = platform.getPayService(this, getHttpConfigStorage());
        }
        return payService;
    }

    public AliPayConfig httpConfigStorage(HttpConfigStorage httpConfigStorage) {
        this.httpConfigStorage = httpConfigStorage;
        return this;
    }

}
