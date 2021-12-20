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
package com.fastcms.plugin;

import org.pf4j.PluginWrapper;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/12
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface PluginManagerService {

	/**
	 * 安装插件
	 * @param path
	 * @throws Exception
	 */
	void installPlugin(Path path) throws Exception;

	/**
	 * 卸载插件
	 * @param pluginId
	 * @throws Exception
	 */
	void unInstallPlugin(String pluginId) throws Exception;

	/**
	 * 插件环境初始化
	 * @throws Exception
	 */
	void initPlugins() throws Exception;

	/**
	 * 分页查询插件列表
	 * @param pageNum
	 * @param pageSize
	 * @param pluginId	插件id
	 * @param provider	插件提供者
	 * @return
	 */
	PluginResult getPluginList(int pageNum, int pageSize, String pluginId, String provider);

	class PluginResult implements Serializable {
		private Integer total;
		private List<PluginVo> pluginVoList;

		public PluginResult(Integer total, List<PluginVo> pluginVoList) {
			this.total = total;
			this.pluginVoList = pluginVoList;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public List<PluginVo> getPluginVoList() {
			return pluginVoList;
		}

		public void setPluginVoList(List<PluginVo> pluginVoList) {
			this.pluginVoList = pluginVoList;
		}
	}

	class PluginVo implements Serializable {
		private String pluginId;
		private String pluginClass;
		private String version;
		private String provider;
		private String description;
		private String dependencies;
		private String pluginState;

		public PluginVo(PluginWrapper pluginWrapper) {
			this.pluginId = pluginWrapper.getPluginId();
			this.pluginClass = pluginWrapper.getDescriptor().getPluginClass();
			this.version = pluginWrapper.getDescriptor().getVersion();
			this.provider = pluginWrapper.getDescriptor().getProvider();
			this.description = pluginWrapper.getDescriptor().getPluginDescription();
			this.dependencies = pluginWrapper.getDescriptor().getDependencies().stream().map(dependency -> dependency.getPluginId()).collect(Collectors.joining());
			this.pluginState = pluginWrapper.getPluginState().name();
		}

		public String getPluginId() {
			return pluginId;
		}

		public void setPluginId(String pluginId) {
			this.pluginId = pluginId;
		}

		public String getPluginClass() {
			return pluginClass;
		}

		public void setPluginClass(String pluginClass) {
			this.pluginClass = pluginClass;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getProvider() {
			return provider;
		}

		public void setProvider(String provider) {
			this.provider = provider;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDependencies() {
			return dependencies;
		}

		public void setDependencies(String dependencies) {
			this.dependencies = dependencies;
		}

		public String getPluginState() {
			return pluginState;
		}

		public void setPluginState(String pluginState) {
			this.pluginState = pluginState;
		}
	}

}
