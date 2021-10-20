package com.fastcms.aop;

import java.lang.reflect.Proxy;

/**
 * @author： wjun_java@163.com
 * @date： 2021/9/30
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Car car = (Car) Proxy.newProxyInstance(Car.class.getClassLoader(), new Class[]{Car.class}, (proxy, method, args1) -> {
            System.out.println("before method name>>>" + method.getName());
            Object invoke = method.invoke(new DaZhong(), args1);
            System.out.println("end method name>>>" + method.getName());
            return invoke;
        });
        car.run();
    }

    public interface Car {
        void info(String name);
        void run();
    }

    public static class DaZhong implements Car {

        @Override
        public void info(String name) {
            System.out.println(name);
        }

        @Override
        public void run() {
            System.out.println(">>>DaZhong run");
        }
    }

}
