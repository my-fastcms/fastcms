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

import org.pf4j.PluginClassLoader;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class PluginBeanPostProcessor implements BeanPostProcessor, PriorityOrdered, BeanFactoryAware {

	private DefaultListableBeanFactory defaultListableBeanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean.getClass().getClassLoader() == null) return bean;

		List<BeanPostProcessor> beanPostProcessors = defaultListableBeanFactory.getBeanPostProcessors();

		if(bean.getClass().getClassLoader().getClass() == PluginClassLoader.class) {

			for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
				if(beanPostProcessor instanceof AnnotationAwareAspectJAutoProxyCreator) {
					((AnnotationAwareAspectJAutoProxyCreator) beanPostProcessor).setBeanClassLoader(bean.getClass().getClassLoader());
					break;
				}
			}

		}else{

			for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
				if(beanPostProcessor instanceof AnnotationAwareAspectJAutoProxyCreator) {
					((AnnotationAwareAspectJAutoProxyCreator) beanPostProcessor).setBeanClassLoader(ClassUtils.getDefaultClassLoader());
					break;
				}
			}

		}
		return bean;
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
	}

}
