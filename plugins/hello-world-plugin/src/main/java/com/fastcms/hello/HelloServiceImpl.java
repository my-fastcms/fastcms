package com.fastcms.hello;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;

/**
 * wjun_java@163.com
 */
@Extension
public class HelloServiceImpl implements HelloService, ExtensionPoint {

    @Override
    public void sayHello() {
        System.out.println("=============sayHello");
    }

}
