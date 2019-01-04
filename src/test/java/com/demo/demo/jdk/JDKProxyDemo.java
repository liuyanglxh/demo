package com.demo.demo.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class JDKProxyDemo implements InvocationHandler {

    private Object target;

    public Object getProxy(Object target){
        Objects.requireNonNull(target);
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * @param proxy 代理对象本身
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("success creating proxy object");
        //invoke是在target也就是真实对象上执行的
        Object result = method.invoke(target, args);
        System.out.println("success invoking method");
        return result;
    }
}
