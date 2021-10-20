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

import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class UnionPayMessageHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

	@Override
	public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
		return null;
	}

}
