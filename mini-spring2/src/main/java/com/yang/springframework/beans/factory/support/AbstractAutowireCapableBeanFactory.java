package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.config.BeanDefinition;

/**
 * <p>抽象bean工厂超类，实现了默认的bean创建机制，并具备RootBeanDefinition类所指定的完整能力。
 * 除了继承AbstractBeanFactory的createBean方法外，还实现了AutowireCapableBeanFactory接口。
 *
 * <p>提供bean创建（包含构造函数解析）、属性填充、装配（包括自动装配）以及初始化功能。
 * 能够处理运行时bean引用、解析托管集合、调用初始化方法等。
 * 支持按构造函数自动装配、按名称属性自动装配以及按类型属性自动装配。
 *
 * <p>子类需要实现的主要模板方法是resolveDependency(DependencyDescriptor, String, Set, TypeConverter)，
 * 用于自动装配。对于能够搜索其bean定义的org.springframework.beans.factory.ListableBeanFactory，
 * 通常通过此类搜索实现bean匹配；否则，可以简化匹配逻辑的实现。
 *
 * <p>请注意，此类并不假定或实现bean定义注册表功能。
 * 有关org.springframework.beans.factory.ListableBeanFactory和BeanDefinitionRegistry接口的实现，
 * 请参考DefaultListableBeanFactory——它们分别代表了此类工厂的API视图和SPI视图。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }
}
