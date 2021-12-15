package com.fastcms.hello;

import org.pf4j.ExtensionPoint;

/**
 * plugin hello service
 * wjun_java@163.com
 */
public interface HelloService extends ExtensionPoint {

    void sayHello();

}
