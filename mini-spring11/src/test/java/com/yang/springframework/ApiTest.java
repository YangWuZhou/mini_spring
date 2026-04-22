package com.yang.springframework;

import com.yang.springframework.context.support.ClassPathXmlApplicationContext;
import com.yang.springframework.event.CustomEvent;
import org.junit.jupiter.api.Test;

public class ApiTest {

    @Test
    public void test_EventAndEventListener() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 12321312L, "这是一条消息"));

        applicationContext.registerShutdownHook();
    }
}
