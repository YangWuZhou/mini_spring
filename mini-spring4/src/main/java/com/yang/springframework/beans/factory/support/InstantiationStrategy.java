package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * <p>负责根据根bean定义创建实例的接口。
 * <p>由于可能存在多种方法（包括使用CGLIB动态创建子类以支持方法注入），因此将其提取为一种策略。
 */
public interface InstantiationStrategy {

    /**
     * 在此工厂中返回具有给定名称的bean实例，通过给定的构造函数创建它。
     * @param beanDefinition bean定义
     * @param beanName 在此上下文中创建bean时的名称。如果正在自动装配不属于该工厂的bean，则该名称可以为null。
     * @param ctor 要使用的构造函数
     * @param args 要应用的构造函数参数
     * @return 此bean定义的bean实例
     * @throws BeansException 如果实例化尝试失败
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
