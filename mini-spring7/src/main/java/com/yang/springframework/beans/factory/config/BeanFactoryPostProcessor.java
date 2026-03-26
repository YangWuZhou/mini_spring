package com.yang.springframework.beans.factory.config;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 工厂钩子，允许对应用上下文的bean定义进行自定义修改，从而调整上下文底层bean工厂中的bean属性值。
 * <p>对于面向系统管理员的自定义配置文件（用于覆盖应用上下文中配置的bean属性）非常有用。
 * 有关解决此类配置需求的现成解决方案，请参见PropertyResourceConfigurer及其具体实现。
 * <p>BeanFactoryPostProcessor可以与bean定义交互并对其进行修改，但绝不能操作bean实例。这样做可能导致bean过早实例化，
 * 违反容器规则并引发意外的副作用。如果需要与bean实例交互，请考虑实现BeanPostProcessor。
 * <h3>注册</h3>
 * ApplicationContext会自动检测其bean定义中的BeanFactoryPostProcessor bean，并在任何其他bean创建之前应用它们。
 * BeanFactoryPostProcessor也可以通过编程方式注册到ConfigurableApplicationContext。
 * <h3>排序</h3>
 * 在ApplicationContext中自动检测到的BeanFactoryPostProcessor bean将按照org.springframework.core.PriorityOrdered和
 * org.springframework.core.Ordered语义进行排序。相比之下，通过编程方式注册到ConfigurableApplicationContext的
 * BeanFactoryPostProcessor bean将按照注册顺序应用；对于编程式注册的后处理器，
 * 通过实现PriorityOrdered或Ordered接口表达的任何排序语义都将被忽略。此外，@Order注解不适用于BeanFactoryPostProcessor bean。
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在其标准初始化之后修改应用上下文的内部 bean 工厂。此时所有 bean 定义均已加载，但尚未实例化任何 bean。
     * 这使得即使对于预先初始化的 bean，也可以覆盖或添加属性。
     * @param beanFactory 应用上下文使用的 bean 工厂
     * @throws BeansException 如果发生错误
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
