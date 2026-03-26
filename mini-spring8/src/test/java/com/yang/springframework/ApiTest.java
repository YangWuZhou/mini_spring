package com.yang.springframework;

import com.yang.springframework.bean.UserService;
import com.yang.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yang.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.yang.springframework.common.MyBeanFactoryPostProcessor;
import com.yang.springframework.common.MyBeanPostProcessor;
import com.yang.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

public class ApiTest {

    /**
     * 测试初始化和销毁方法
     */
    @Test
    public void test_initAndDestroy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo("001");
    }
}
