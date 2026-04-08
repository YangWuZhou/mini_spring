package com.yang.springframework;

import com.yang.springframework.bean.UserService;
import com.yang.springframework.beans.factory.BeanFactory;
import com.yang.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

public class ApiTest {

    /**
     * 测试初始化和销毁方法
     */
    @Test
    public void test_Aware() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo("001");
        System.out.println("ApplicationContext: " + userService.getApplicationContext());
        BeanFactory beanFactory = userService.getBeanFactory();
        System.out.println("BeanFactory: " + beanFactory);
    }
}
