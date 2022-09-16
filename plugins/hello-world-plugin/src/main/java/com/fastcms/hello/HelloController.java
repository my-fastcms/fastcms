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
package com.fastcms.hello;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.plugin.PassFastcms;
import com.fastcms.utils.PluginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * wjun_java@163.com
 */
@Controller
@RequestMapping(FastcmsConstants.PLUGIN_MAPPING + "/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping
    public String index() {

        List<HelloService> extensions = PluginUtils.getExtensions(HelloService.class);
        extensions.forEach(item -> item.sayHello());

        List<Hello> helloList = helloService.getHelloList();
        System.out.println(helloList);

        HelloService.HelloVo byHelloId = helloService.findByHelloId(1l);
        System.out.println(byHelloId);

        return "hello";
    }

    @PassFastcms
    @GetMapping("say")
    @ResponseBody
    public Object say() {
        return RestResultUtils.success("hello fastcms");
    }

}
