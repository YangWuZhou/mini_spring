package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.factory.config.BeanDefinition;

/**
 * <p>BeanDefinitionRegistry是用于存储Bean定义
 * （例如RootBeanDefinition和ChildBeanDefinition实例）的注册表接口。
 * 通常由内部使用AbstractBeanDefinition层次结构的BeanFactory实现。
 *
 * <p>这是Spring Bean工厂包中唯一封装Bean定义注册功能的接口。
 * 标准BeanFactory接口仅涵盖对完全配置的工厂实例的访问。
 *
 * <p>Spring的Bean定义读取器基于此接口的实现进行操作。
 * Spring核心框架中已知的实现类包括DefaultListableBeanFactory和GenericApplicationContext。
 */
public interface BeanDefinitionRegistry {

    /**
     * 向此注册表注册一个新的bean定义。必须支持RootBeanDefinition和ChildBeanDefinition。
     * @param beanName 要注册的bean实例的名称
     * @param beanDefinition 要注册的bean实例的定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
