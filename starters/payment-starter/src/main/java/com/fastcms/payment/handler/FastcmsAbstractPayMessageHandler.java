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
import com.egzosn.pay.common.bean.PayMessage;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对微信重复推送支付消息处理
 * @author： wjun_java@163.com
 * @date： 2022/4/13
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class FastcmsAbstractPayMessageHandler<M extends PayMessage, S extends PayService> implements PayMessageHandler<PayMessage, PayService> {

    /**
     * 正在处理中的订单
     */
    static final Set<String> outTradeNoSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public PayOutMessage handle(PayMessage payMessage, Map<String, Object> context, PayService payService) throws PayErrorException {

        synchronized (outTradeNoSet) {
            if (outTradeNoSet.contains(payMessage.getOutTradeNo())) {
                return payService.getPayOutMessage("SUCCESS", "OK");
            }

            outTradeNoSet.add(payMessage.getOutTradeNo());

        }

        try {
            return doHandle(payMessage, context, payService);
        } finally {
            outTradeNoSet.remove(payMessage.getOutTradeNo());
        }
    }

    protected abstract PayOutMessage doHandle(PayMessage payMessage, Map<String, Object> context, PayService payService) throws PayErrorException;

}
