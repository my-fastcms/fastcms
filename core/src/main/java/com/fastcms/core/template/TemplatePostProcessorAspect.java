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
package com.fastcms.core.template;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拦截TemplateService的getCurrTemplate()方法
 * @author： wjun_java@163.com
 * @date： 2023/2/19
 * @description：
 * @modifiedBy：
 * @version: 1.0
 * @see TemplateService
 */
@Aspect
@Component
public class TemplatePostProcessorAspect {

	@Autowired
	private TemplatePostProcessorManager templatePostProcessorManager;

	@Around("execution(* com.fastcms.core.template.TemplateService.getCurrTemplate())")
	public Template getTemplate(ProceedingJoinPoint joinPoint) throws Throwable {
		Template template = (Template) joinPoint.proceed();
		TemplatePostProcessor templatePostProcessor = templatePostProcessorManager.getTemplatePostProcessor();
		return templatePostProcessor.postProcess(template);
	}

}
