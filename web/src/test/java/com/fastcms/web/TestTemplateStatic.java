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

package com.fastcms.web;

import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import freemarker.template.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * @author： wjun_java@163.com
 * @date： 2022/9/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@SpringBootTest
public class TestTemplateStatic {

    @Autowired
    Configuration fastcmsConfiguration;

    static final Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    @Autowired
    TemplateService templateService;

    @Test
    public void test() throws Exception {
        Template currTemplate = templateService.getTemplate("www.fastcms.com");
        configuration.setDirectoryForTemplateLoading(currTemplate.getTemplatePath().toFile());
        freemarker.template.Template template = configuration.getTemplate("index.html","utf-8");
        Writer out = new OutputStreamWriter(new FileOutputStream(currTemplate.getTemplatePath().resolve("static").toString() + "/index.htm"),"UTF-8");
        HashMap<String, Object> data = new HashMap<>();
        fastcmsConfiguration.getSharedVariableNames().forEach(item -> data.put((String) item, fastcmsConfiguration.getSharedVariable((String) item)));
        template.process(data, out);
        out.flush();
        out.close();
    }

}
