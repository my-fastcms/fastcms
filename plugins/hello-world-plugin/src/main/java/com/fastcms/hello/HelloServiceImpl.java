package com.fastcms.hello;

import org.pf4j.Extension;

/**
 * wjun_java@163.com
 */
@Extension
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("=============sayHello");
    }

}
