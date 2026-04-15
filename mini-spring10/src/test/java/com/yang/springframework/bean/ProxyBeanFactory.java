package com.yang.springframework.bean;

import com.yang.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            if ("toString".equals(method.getName())) {
                return this.toString();
            }

            Map<String, String> map = new HashMap<>();
            map.put("10001", "zhangsan");
            map.put("10002", "lisi");
            map.put("10003", "wangwu");
            return "被代理" + method.getName() + ": " + map.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
