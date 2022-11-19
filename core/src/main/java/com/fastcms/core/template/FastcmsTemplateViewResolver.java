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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FastcmsTemplateViewResolver extends FreeMarkerViewResolver implements InitializingBean, Ordered {

    @Override
    protected Class<?> requiredViewClass() {
        return FastcmsTemplateView.class;
    }

    @Override
    protected AbstractUrlBasedView instantiateView() {
        return (getViewClass() == FastcmsTemplateView.class ? new FastcmsTemplateView() : super.instantiateView());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setSuffix(".html");
        setContentType("text/html;charset=UTF-8");
        setRequestContextAttribute("request");
    }

//    @Override
//    public int getOrder() {
//        return Ordered.LOWEST_PRECEDENCE - 7;
//    }

}
