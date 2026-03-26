package com.yang.springframework.common;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.PropertyValue;
import com.yang.springframework.beans.PropertyValues;
import com.yang.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition userServiceBd = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = userServiceBd.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "字节跳动"));
    }
}
