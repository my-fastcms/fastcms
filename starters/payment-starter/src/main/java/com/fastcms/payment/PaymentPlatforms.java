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

import com.egzosn.pay.common.bean.TransactionType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付平台集合
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class PaymentPlatforms {

    private static final Map<String, PaymentPlatform> PAYMENT_PLATFORMS = Collections.synchronizedMap(new HashMap<>());

    /**
     * 加载支付平台
     *
     * @param platform 支付平台
     */
    public static void loadPaymentPlatform(PaymentPlatform platform) {
        PAYMENT_PLATFORMS.put(platform.getPlatform(), platform);
    }

    /**
     * 获取所有的支付平台
     *
     * @return 所有的支付平台
     */
    public static Map<String, PaymentPlatform> getPaymentPlatforms() {
        return PAYMENT_PLATFORMS;
    }

    /**
     * 通过支付平台名称与交易类型(支付类型)名称或者交易类型
     *
     * @param platformName        支付平台名称
     * @param transactionTypeName 交易类型名称
     * @return 交易类型
     */
    public static TransactionType getTransactionType(String platformName, String transactionTypeName) {
        PaymentPlatform platform = getPaymentPlatform(platformName);
        return platform.getTransactionType(transactionTypeName);
    }

    /**
     * 通过支付平台名称与交易类型(支付类型)名称或者交易类型
     *
     * @param platformName 支付平台名称
     * @return 交易类型
     */
    public static PaymentPlatform getPaymentPlatform(String platformName) {
        PaymentPlatform platform = PAYMENT_PLATFORMS.get(platformName);
        return platform;
    }


}
