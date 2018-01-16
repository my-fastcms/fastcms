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
package com.dbumama.market.web.core.ueditor.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.dbumama.market.web.core.ueditor.define.AppInfo;
import com.dbumama.market.web.core.ueditor.define.BaseState;
import com.dbumama.market.web.core.ueditor.define.MultiState;
import com.dbumama.market.web.core.ueditor.define.State;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.BucketManager.FileListIterator;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class QiniuFileManager extends AbstractFileManager{
	private final Auth auth;
	private final String bucket;
	private UploadManager uploadManager;
	private BucketManager bucketManager;
	
	public QiniuFileManager(String ak, String sk, String bucket) {
		auth = Auth.create(ak, sk);
		this.bucket = bucket;
		uploadManager = new UploadManager();
		bucketManager = new BucketManager(auth);
	}
	
	private String getUpToken(){
		return auth.uploadToken(bucket);
	}
	
	private State getState(String[] keys) {
		MultiState state = new MultiState(true);
		BaseState fileState = null;

		for (String key : keys) {
			if (key == null) {
				break;
			}
			fileState = new BaseState(true);
			fileState.putInfo("url", key);
			state.addState(fileState);
		}

		return state;
	}
	
	@Override
	public State list(Map<String, Object> conf, int start) {
		String dirPath = (String) conf.get("dir");
		List<String> allowFiles = getAllowFiles(conf.get("allowFiles"));
		int count = (Integer) conf.get("count");
		
		if (dirPath.startsWith("/")) {
			dirPath = dirPath.substring(1);
		}
		
		FileListIterator it = bucketManager.createFileListIterator(bucket, dirPath, count, null);
		List<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			FileInfo[] items = it.next();
			for (FileInfo fileInfo : items) {
				String key = fileInfo.key;
				String ext = FilenameUtils.getExtension(key);
				if (allowFiles.contains(ext)) {
					list.add("/" + fileInfo.key);
				}
			}
		}
		
		Collections.reverse(list);
		
		State state = null;
		if (start < 0 || start > list.size()) {
			state = new MultiState(true);
		} else {
			String[] fileList = Arrays.copyOfRange(list.toArray(new String[]{}), start, start + count);
			state = getState(fileList);
		}

		state.putInfo("start", start);
		state.putInfo("total", list.size());
		return state;
	}

	@Override
	public State saveFile(byte[] data, String rootPath, String savePath) {
		if (savePath.startsWith("/")) {
			savePath = savePath.substring(1);
		}
		
		try {
			uploadManager.put(data, savePath, getUpToken());
		} catch (QiniuException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
		State state = new BaseState(true);
		state.putInfo("size", data.length);
		state.putInfo("title", getFileName(savePath));
		return state;
	}
	
	@Override
	public State saveFile(InputStream is, String rootPath, String savePath, String fileName, long maxSize) {
		if (savePath.startsWith("/")) {
			savePath = savePath.substring(1);
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			IOUtils.copy(is, output);
			data = output.toByteArray();
			if (data.length > maxSize) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}
			uploadManager.put(data, savePath, getUpToken());
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(is);
		}
		State state = new BaseState(true);
		state.putInfo("size", data.length);
		state.putInfo("title", getFileName(savePath));
		return state;
	}

	@Override
	public State saveFile(InputStream is, String rootPath, String savePath) {
		if (savePath.startsWith("/")) {
			savePath = savePath.substring(1);
		}
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			IOUtils.copy(is, output);
			data = output.toByteArray();
			uploadManager.put(data, savePath, getUpToken());
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(is);
		}
		State state = new BaseState(true);
		state.putInfo("size", data.length);
		state.putInfo("title", getFileName(savePath));
		return state;
	}

}

