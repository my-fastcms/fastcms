package com.fastcms.hello;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * wjun_java@163.com
 */
@Extension
public class HelloServiceImpl implements HelloService, ExtensionPoint {

    @Autowired
    private HelloPluginMapper helloPluginMapper;

    @Override
    public void sayHello() {
        System.out.println(helloPluginMapper);
        System.out.println("=============sayHello");
    }

}
