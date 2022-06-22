package com.fastcms.hello;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * wjun_java@163.com
 */
@Extension
public class HelloServiceImpl extends ServiceImpl<HelloPluginMapper, Hello> implements HelloService, ExtensionPoint {

    @Autowired
    private HelloComponent helloComponent;

    @Override
    public void sayHello() {
        System.out.println("=============sayHello");
        System.out.println(helloComponent);
    }

}
