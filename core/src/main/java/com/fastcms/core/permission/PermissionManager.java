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
package com.fastcms.core.permission;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface PermissionManager {

	/**
	 * 初始化系统权限
	 * @Param appClass
	 * @throws Exception
	 */
	void initSystemPermissions(Class appClass) throws Exception;

	/**
	 * 刷新到最新系统菜单
	 * @Param appClass
	 * @throws Exception
	 */
	void refreshSystemPermissions(Class appClass) throws Exception;

	/**
	 * 清除模块菜单
	 * @param moduleId
	 */
	void deletePermissions(String moduleId);

}
