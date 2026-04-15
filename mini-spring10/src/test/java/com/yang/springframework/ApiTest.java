package com.yang.springframework;

import cn.hutool.core.lang.Assert;
import com.yang.springframework.bean.UserService;
import com.yang.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

public class ApiTest {

    /**
     * 测试FactoryBean
     */
    @Test
    public void test_FactoryBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 获取Bean对象调用方法
        UserService userService1 = applicationContext.getBean("userService", UserService.class);
        UserService userService2 = applicationContext.getBean("userService", UserService.class);

        Assert.notEquals(userService1, userService2);
    }
}
