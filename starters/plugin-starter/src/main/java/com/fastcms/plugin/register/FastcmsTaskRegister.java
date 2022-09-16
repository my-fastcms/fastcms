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

package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.FastcmsTask;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 注册定时任务
 * @author： wjun_java@163.com
 * @date： 2022/4/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsTaskRegister extends AbstractPluginRegister {

    ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

    public FastcmsTaskRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        scheduledAnnotationBeanPostProcessor = (ScheduledAnnotationBeanPostProcessor) getBean(ScheduledAnnotationBeanPostProcessor.class);
    }

    @Override
    public void registry(String pluginId) throws Exception {
        getPluginTaskClasses(pluginId).forEach(item -> scheduledAnnotationBeanPostProcessor.postProcessAfterInitialization(getBean(item), item.getName()));
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {
        getPluginTaskClasses(pluginId).forEach(item -> destroyBean(item));
    }

    protected List<Class<?>> getPluginTaskClasses(String pluginId) throws Exception {
        return getPluginClasses(pluginId).stream().filter(item -> item.getAnnotation(FastcmsTask.class) != null).collect(Collectors.toList());
    }
}
