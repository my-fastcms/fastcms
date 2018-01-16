/**
 * Copyright (c) 广州点步信息科技有限公司 2016-2017, wjun_java@163.com.
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *	    http://www.dbumama.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dbumama.market.message;

/**
 * @author wangjun
 * 2017年7月13日
 */
public class MessageKit {

	public static void register(Class<? extends MessageListener> listenerClass) {
		MessageManager.me().registerListener(listenerClass);
	}
	
	public static void unRegister(Class<? extends MessageListener> listenerClass) {
		MessageManager.me().unRegisterListener(listenerClass);
	}

	public static void sendMessage(Message message) {
		MessageManager.me().pulish(message);
	}

	public static void sendMessage(String action, Object data) {
		MessageManager.me().pulish(new Message(action, data));
	}

	public static void sendMessage(String action) {
		MessageManager.me().pulish(new Message(action, null));
	}

}
