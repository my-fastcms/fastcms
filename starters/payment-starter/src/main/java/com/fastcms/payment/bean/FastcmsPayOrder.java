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
package com.fastcms.payment.bean;

import com.egzosn.pay.common.bean.PayOrder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsPayOrder extends PayOrder implements Serializable {

	/**
	 *	支付平台
	 */
	private String  platform;

	/**
	 * 交易类型，交易方式，
	 * 本字段与{@link com.egzosn.pay.common.bean.PayOrder#getTransactionType()}相同。
	 *
	 *  例如，网页支付，扫码付等等
	 */
	private String wayTrade;

	public FastcmsPayOrder(String platform, String wayTrade) {
		this.platform = platform;
		this.wayTrade = wayTrade;
	}

	public FastcmsPayOrder(String platform, String wayTrade, String subject, String body, BigDecimal price, String outTradeNo) {
		super(subject, body, price, outTradeNo);
		this.platform = platform;
		this.wayTrade = wayTrade;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getWayTrade() {
		return wayTrade;
	}

	public void setWayTrade(String wayTrade) {
		this.wayTrade = wayTrade;
	}

}
