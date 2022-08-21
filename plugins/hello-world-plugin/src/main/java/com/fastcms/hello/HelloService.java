package com.fastcms.hello;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * plugin hello service
 * wjun_java@163.com
 */
public interface HelloService extends IService<Hello> {

    void sayHello();

    List<Hello> getHelloList();

}
