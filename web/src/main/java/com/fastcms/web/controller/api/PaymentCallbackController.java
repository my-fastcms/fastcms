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
package com.fastcms.web.controller.api;

import com.fastcms.payment.PayServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ignore
 * 支付回调
 * @author： wjun_java@163.com
 * @date： 2021/6/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping("/payback")
public class PaymentCallbackController {

	@Autowired
	private PayServiceManager payServiceManager;

	/**
	 * 支付回调
	 * @param request
	 * @param platform
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "{platform}")
	public String payBack(HttpServletRequest request, @PathVariable String platform) throws IOException {
		return payServiceManager.payBack(platform, request.getParameterMap(), request.getInputStream());
	}

}
