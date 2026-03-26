package com.yang.springframework;

import com.yang.springframework.bean.UserService;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yang.springframework.beans.factory.support.SimpleInstantiationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void test_ParameterConstructorFromCglib() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. 注册 Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3. 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService", "张三");
        userService.queryUserInfo();

        beanFactory.registerBeanDefinition("userService2", beanDefinition);
        Assertions.assertEquals("张三", userService.getName());
        UserService userService2 = (UserService) beanFactory.getBean("userService2", "张三", 20);
        userService2.queryUserInfo();
        Assertions.assertNotEquals(userService, userService2);
    }

    @Test
    public void test_ParameterConstructorFromJDK() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setInstantiationStrategy(new SimpleInstantiationStrategy());
        // 2. 注册 Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3. 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService", "张三");
        userService.queryUserInfo();

        Assertions.assertEquals("张三", userService.getName());
    }
}
