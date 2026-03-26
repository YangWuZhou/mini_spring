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
     * 测试不使用封装的ApplicationContext获取对象
     */
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() {
        // 1. 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. BeanDefinition 加载完成 & Bean实例化前，修改BeanDefinition的属性值
        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = beanFactory.getBean("myBeanFactoryPostProcessor", MyBeanFactoryPostProcessor.class);
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean实例化后，修改Bean属性信息
        MyBeanPostProcessor myBeanPostProcessor = beanFactory.getBean("myBeanPostProcessor", MyBeanPostProcessor.class);
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);

        // 5. 获取Bean对象
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo("001");
    }

    /**
     * 使用ApplicationContext获取对象
     */
    @Test
    public void test_ApplicationContext() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo("001");
    }
}
