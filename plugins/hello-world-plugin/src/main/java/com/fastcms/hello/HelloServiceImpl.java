package com.fastcms.hello;

import org.pf4j.Extension;
import org.springframework.stereotype.Service;

/**
 * wjun_java@163.com
 */
@Service
@Extension
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("hello, fastcms plugins");
    }

}
