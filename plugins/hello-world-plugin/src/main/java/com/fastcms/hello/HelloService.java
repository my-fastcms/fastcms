<<<<<<< HEAD
package com.fastcms.hello;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * plugin hello service
 * wjun_java@163.com
 */
public interface HelloService extends IService<Hello> {

    void sayHello();

    List<Hello> getHelloList();

    HelloVo findByHelloId(Long id);

    class HelloVo implements Serializable {
        String name;
    }

}
=======
package com.fastcms.hello;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * plugin hello service
 * wjun_java@163.com
 */
public interface HelloService extends IService<Hello> {

    void sayHello();

    List<Hello> getHelloList();

    HelloVo findByHelloId(Long id);

    class HelloVo implements Serializable {
        String name;
    }

}
>>>>>>> 9b22e8ee2077deb1d0ca28d39702bca483d28388
