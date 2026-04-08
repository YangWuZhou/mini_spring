package com.yang.springframework.bean;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.BeanClassLoaderAware;
import com.yang.springframework.beans.factory.BeanFactory;
import com.yang.springframework.beans.factory.BeanFactoryAware;
import com.yang.springframework.beans.factory.BeanNameAware;
import com.yang.springframework.context.ApplicationContext;
import com.yang.springframework.context.ApplicationContextAware;
import lombok.Data;

@Data
public class UserService implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware, ApplicationContextAware {
    private String name;
    private UserDao userDao;
    private String location;
    private String company;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    public void queryUserInfo(String id) {
        System.out.println("用户名: " + userDao.queryUserName(id) + ", 别名: " + name + ", 地址: " + location + ", 公司: " + company);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader: " + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanName: " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
