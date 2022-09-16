<<<<<<< HEAD
package com.fastcms.hello;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Override
    public List<Hello> getHelloList() {
        return getBaseMapper().getHelloList();
    }

    @Override
    public HelloVo findByHelloId(Long id) {
        return getBaseMapper().findByHelloId(id);
    }

}
=======
package com.fastcms.hello;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Override
    public List<Hello> getHelloList() {
        return getBaseMapper().getHelloList();
    }

    @Override
    public HelloVo findByHelloId(Long id) {
        return getBaseMapper().findByHelloId(id);
    }

}
>>>>>>> 9b22e8ee2077deb1d0ca28d39702bca483d28388
