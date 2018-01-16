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

import java.io.InputStream;
import java.util.Map;

import com.dbumama.market.web.core.ueditor.define.State;

/**
 * @author wangjun
 * 2017年7月11日
 */
public interface UeditorFileManager {
	/**
	 * 文件列表
	 * @param conf 配置
	 * @param start 开始
	 * @return state 状态接口
	 */
	State list(Map<String, Object> conf, int start);
	
	/**
	 * 保存二进制文件
	 * @param data 图片二进制信息
	 * @param rootPath 跟路径
	 * @param savePath 保存路径
	 * @return state 状态接口
	 */
	State saveFile(byte[] data, String rootPath, String savePath);
	
	/**
	 * 保存流文件
	 * @param is 流
	 * @param rootPath 跟路径
	 * @param savePath 保存路径
	 * @param maxSize 文件最大字节
	 * @return state 状态接口
	 */
	State saveFile(InputStream is, String rootPath, String savePath, String fileName, long maxSize);
	
	/**
	 * 保存流文件
	 * @param is 流
	 * @param rootPath 跟路径
	 * @param savePath 保存路径
	 * @return state 状态接口
	 */
	State saveFile(InputStream is, String rootPath, String savePath);
}

