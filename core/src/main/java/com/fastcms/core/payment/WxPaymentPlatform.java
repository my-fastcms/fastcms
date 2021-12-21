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
package com.fastcms.core.payment;

import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.api.WxPayService;
import com.egzosn.pay.wx.bean.WxTransactionType;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.payment.PaymentPlatform;
import com.fastcms.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信支付平台
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class WxPaymentPlatform extends WxPayConfigStorage implements PaymentPlatform {

    public static final String platformName = "wxPay";

    @Autowired
    private WxPayMessageHandler wxPayMessageHandler;

    private static WxPayService wxPayService;

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
     * @return 支付服务
     */
    @Override
    public PayService getPayService() {
        return getPayService(null);
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param httpConfigStorage 网络配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(HttpConfigStorage httpConfigStorage) {

        setAppId(ConfigUtils.getConfig(FastcmsConstants.WECHAT_MINI_APP_ID));
        setMchId(ConfigUtils.getConfig(FastcmsConstants.WECHAT_MCH_ID));

        setInputCharset("UTF-8");
//        setSecretKey(ConfigUtils.getConfig(FastcmsConstants.WECHAT_MCH_SECRET));
        setKeyPrivate(ConfigUtils.getConfig(FastcmsConstants.WECHAT_MCH_SECRET));
//        setKeyPublic(ConfigUtils.getConfig(FastcmsConstants.WECHAT_MCH_ID));
        setNotifyUrl(ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) + "/payback/" + getPlatform());
        setReturnUrl(ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) + "/payback/" + getPlatform());
        setPayType(getPlatform());
        setTest(false);
        setSignType("MD5");

        if(null == wxPayService) {
            wxPayService = new WxPayService(this);
        }else {
            wxPayService.setPayConfigStorage(this);
        }

        wxPayService.setRequestTemplateConfigStorage(httpConfigStorage);
        wxPayService.setPayMessageHandler(wxPayMessageHandler);
        return wxPayService;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        return WxTransactionType.valueOf(name);
    }

}
