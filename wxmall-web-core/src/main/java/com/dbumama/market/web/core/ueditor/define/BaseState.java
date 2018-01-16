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
package com.dbumama.market.web.core.ueditor.define;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.web.core.ueditor.Encoder;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class BaseState implements State {
	private boolean state = false;
	private String info = null;

	private Map<String, Object> infoMap = new HashMap<String, Object>();

	public Map<String, Object> getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(Map<String, Object> infoMap) {
		this.infoMap = infoMap;
	}

	public BaseState() {
		this.state = true;
	}

	public BaseState(boolean state) {
		this.setState(state);
	}

	public BaseState(boolean state, String info) {
		this.setState(state);
		this.info = info;
	}

	public BaseState(boolean state, int infoCode) {
		this.setState(state);
		this.info = AppInfo.getStateInfo(infoCode);
	}

	public boolean isSuccess() {
		return this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setInfo(int infoCode) {
		this.info = AppInfo.getStateInfo(infoCode);
	}

	@Override
	public String toJSONString() {
		this.toJSONObject();
		return Encoder.toUnicode(JSONObject.toJSONString(this.infoMap));
	}

	@Override
	public void putInfo(String name, String val) {
		this.infoMap.put(name, val);
	}

	@Override
	public void putInfo(String name, long val) {
		this.putInfo(name, val + "");
	}

	@Override
	public Map<String, Object> toJSONObject() {
		String stateVal = this.isSuccess() ? AppInfo.getStateInfo(AppInfo.SUCCESS) : this.info;
		this.infoMap.put("state", stateVal);
		return infoMap;
	}
}

