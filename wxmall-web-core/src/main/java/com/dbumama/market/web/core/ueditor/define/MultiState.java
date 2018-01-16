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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.web.core.ueditor.Encoder;

/**
 * 多状态集合状态 其包含了多个状态的集合, 其本身自己也是一个状态
 * @author wangjun
 * 2017年7月10日
 */
public class MultiState implements State{
	private boolean state = false;
	private String info = null;
	private Map<String, Object> infoMap = new HashMap<String, Object>();
	private List<Map<String, Object>> stateList = new ArrayList<Map<String, Object>>();

	public MultiState(boolean state) {
		this.state = state;
	}

	public MultiState(boolean state, String info) {
		this.state = state;
		this.info = info;
	}

	public MultiState(boolean state, int infoKey) {
		this.state = state;
		this.info = AppInfo.getStateInfo(infoKey);
	}

	@Override
	public boolean isSuccess() {
		return this.state;
	}

	public void addState(State state) {
		stateList.add(state.toJSONObject());
	}

	/**
	 * 该方法调用无效果
	 */
	@Override
	public void putInfo(String name, String val) {
		this.infoMap.put(name, val);
	}

	@Override
	public Map<String, Object> toJSONObject() {
		String stateVal = this.isSuccess() ? AppInfo.getStateInfo(AppInfo.SUCCESS) : this.info;

		this.infoMap.put("state", stateVal);
		this.infoMap.put("list", stateList);
		return infoMap;
	}
	
	@Override
	public String toJSONString() {
		this.toJSONObject();
		return Encoder.toUnicode(JSONObject.toJSONString(infoMap));
	}

	@Override
	public void putInfo(String name, long val) {
		this.infoMap.put(name, val);
	}
}

