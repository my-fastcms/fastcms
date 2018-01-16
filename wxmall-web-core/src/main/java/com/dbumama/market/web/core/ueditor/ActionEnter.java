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
package com.dbumama.market.web.core.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dbumama.market.web.core.ueditor.define.ActionMap;
import com.dbumama.market.web.core.ueditor.define.AppInfo;
import com.dbumama.market.web.core.ueditor.define.BaseState;
import com.dbumama.market.web.core.ueditor.define.State;
import com.dbumama.market.web.core.ueditor.hunter.ImageHunter;
import com.dbumama.market.web.core.ueditor.upload.Uploader;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class ActionEnter {
	private ConfigManager configManager = null;

	private static final ActionEnter me = new ActionEnter();

	private ActionEnter() {
		this.configManager = ConfigManager.getInstance();
	}

	public static ActionEnter me() {
		return me;
	}
	
	public String exec(HttpServletRequest request) {
		String callbackName = request.getParameter("callback");

		if (callbackName != null) {
			if (!validCallbackName(callbackName)) {
				return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
			}

			return callbackName + "(" + this.invoke(request) + ");";
		} else {
			return this.invoke(request);
		}
	}

	private String invoke(HttpServletRequest request) {
		String actionType = request.getParameter("action");
		@SuppressWarnings("deprecation")
		String rootPath = request.getRealPath("/");
		
		if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
			return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
		}

		if (this.configManager == null || !this.configManager.valid()) {
			return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
		}

		State state = null;

		int actionCode = ActionMap.getType(actionType);

		Map<String, Object> conf = null;

		switch (actionCode) {

		case ActionMap.CONFIG:
			return this.configManager.getAllConfig().toJSONString();

		case ActionMap.UPLOAD_IMAGE:
		case ActionMap.UPLOAD_SCRAWL:
		case ActionMap.UPLOAD_VIDEO:
		case ActionMap.UPLOAD_FILE:
			conf = this.configManager.getConfig(actionCode, rootPath);
			state = new Uploader(request, conf).doExec();
			break;

		case ActionMap.CATCH_IMAGE:
			conf = configManager.getConfig(actionCode, rootPath);
			String[] list = request.getParameterValues((String) conf.get("fieldName"));
			state = new ImageHunter(conf).capture(list);
			break;

		case ActionMap.LIST_IMAGE:
		case ActionMap.LIST_FILE:
			conf = configManager.getConfig(actionCode, rootPath);
			int start = getStartIndex(request);
			state = UeditorConfigKit.fileManager.list(conf, start);
			break;

		}
		return state.toJSONString();
	}

	public int getStartIndex(HttpServletRequest request) {
		String start = request.getParameter("start");
		try {
			return Integer.parseInt(start);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * callback参数验证
	 * @param name 参数名
	 * @return boolean 是否校验通过
	 */
	public boolean validCallbackName(String name) {
		if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
			return true;
		}
		return false;
	}
}

