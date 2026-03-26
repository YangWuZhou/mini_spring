package com.yang.springframework;

import com.yang.springframework.bean.UserDao;
import com.yang.springframework.bean.UserService;
import com.yang.springframework.beans.PropertyValue;
import com.yang.springframework.beans.PropertyValues;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.BeanReference;
import com.yang.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void test_PropertyInjection() {
        // 1. 创建Bean工厂
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        // 2. 将要注入的属性封装成PropertyValues保存进Bean定义
        PropertyValues pvs = new PropertyValues();
        pvs.addPropertyValue(new PropertyValue("name", "中国"));
        pvs.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        // 3. 创建Bean定义
        BeanDefinition daoBD = new BeanDefinition(UserDao.class);
        BeanDefinition serviceBD = new BeanDefinition(UserService.class, pvs);
        // 4. Bean定义注册进工厂
        factory.registerBeanDefinition("userDao", daoBD);
        factory.registerBeanDefinition("userService", serviceBD);
        // 5. 创建Bean对象
        UserService userService = (UserService) factory.getBean("userService");
        userService.queryUserInfo("002");
    }
}
