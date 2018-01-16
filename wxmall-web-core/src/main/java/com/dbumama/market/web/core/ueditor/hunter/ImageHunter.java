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
package com.dbumama.market.web.core.ueditor.hunter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dbumama.market.web.core.ueditor.PathFormat;
import com.dbumama.market.web.core.ueditor.UeditorConfigKit;
import com.dbumama.market.web.core.ueditor.define.AppInfo;
import com.dbumama.market.web.core.ueditor.define.BaseState;
import com.dbumama.market.web.core.ueditor.define.MIMEType;
import com.dbumama.market.web.core.ueditor.define.MultiState;
import com.dbumama.market.web.core.ueditor.define.State;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class ImageHunter {
	private String filename = null;
	private String savePath = null;
	private String rootPath = null;
	private List<String> allowTypes = null;
	private long maxSize = -1;

	private List<String> filters = null;

	public ImageHunter(Map<String, Object> conf) {

		this.filename = (String) conf.get("filename");
		this.savePath = (String) conf.get("savePath");
		this.rootPath = (String) conf.get("rootPath");
		this.maxSize = (Long) conf.get("maxSize");
		this.allowTypes = Arrays.asList((String[]) conf.get("allowFiles"));
		this.filters = Arrays.asList((String[]) conf.get("filter"));

	}

	public State capture(String[] list) {
		MultiState state = new MultiState(true);

		for (String source : list) {
			state.addState(captureRemoteData(source));
		}

		return state;
	}

	public State captureRemoteData(String urlStr) {
		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;

		try {
			url = new URL(urlStr);

			if (!validHost(url.getHost())) {
				return new BaseState(false, AppInfo.PREVENT_HOST);
			}

			connection = (HttpURLConnection) url.openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(true);

			if (!validContentState(connection.getResponseCode())) {
				return new BaseState(false, AppInfo.CONNECTION_ERROR);
			}

			suffix = MIMEType.getSuffix(connection.getContentType());

			if (!validFileType(suffix)) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			if (!validFileSize(connection.getContentLength())) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			String savePath = this.getPath(this.savePath, this.filename, suffix);

			State state = UeditorConfigKit.getFileManager().saveFile(connection.getInputStream(), rootPath, savePath);

			if (state.isSuccess()) {
				state.putInfo("url", PathFormat.format(savePath));
				state.putInfo("source", urlStr);
			}

			return state;

		} catch (Exception e) {
			return new BaseState(false, AppInfo.REMOTE_FAIL);
		}
	}

	private String getPath(String savePath, String filename, String suffix) {
		return PathFormat.parse(savePath + suffix, filename);
	}

	private boolean validHost(String hostname) {
		return !filters.contains(hostname);
	}

	private boolean validContentState(int code) {
		return HttpURLConnection.HTTP_OK == code;
	}

	private boolean validFileType(String type) {
		return this.allowTypes.contains(type);
	}

	private boolean validFileSize(int size) {
		return size < this.maxSize;
	}
}

