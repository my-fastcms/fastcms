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
package com.fastcms.core.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class RenderUtils {

    public static String render(String templateName, String renderTemplate, Object data) throws Exception {
        Template template = new Template(templateName, renderTemplate, getConfiguration());
        String result;
        StringWriter stringWriter = null;
        try {
            stringWriter = new StringWriter();
            template.process(data, stringWriter);
            result = stringWriter.toString();
        } finally {
            if (stringWriter != null) {
                stringWriter.close();
            }
        }
        return result;
    }

    static Configuration getConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(stringTemplateLoader);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }

}
