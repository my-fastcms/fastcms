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

import com.dbumama.market.web.core.ueditor.manager.DefaultFileManager;
import com.dbumama.market.web.core.ueditor.manager.UeditorFileManager;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class UeditorConfigKit {
	protected static UeditorFileManager fileManager = new DefaultFileManager();
	
	public static void setFileManager(UeditorFileManager fileManager) {
		UeditorConfigKit.fileManager = fileManager;
	}
	
	public static UeditorFileManager getFileManager() {
		return UeditorConfigKit.fileManager;
	}
}

