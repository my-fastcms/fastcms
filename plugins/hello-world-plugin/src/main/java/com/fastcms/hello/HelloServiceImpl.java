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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2022/11/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Extension
public class HelloServiceImpl extends ServiceImpl<HelloPluginMapper, Hello> implements HelloService, ExtensionPoint, InitializingBean {

    @Autowired
    private HelloComponent helloComponent;

    @Override
    @Transactional
    public void sayHello() {
        System.out.println("=============sayHello");
        System.out.println(helloComponent);
        Hello hello = new Hello();
        hello.setName("hello fastcms 2023");
        save(hello);
    }

    @Override
    public List<Hello> getHelloList() {
        return getBaseMapper().getHelloList();
    }

    @Override
    public HelloVo findByHelloId(Long id) {
        return getBaseMapper().findByHelloId(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("sayHello.afterPropertiesSet");
    }

}
