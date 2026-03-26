package com.yang.springframework.beans.factory;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 大多数可列表化 bean 工厂实现的配置接口。除了 ConfigurableBeanFactory 的功能外，它还提供了分析和修改 bean 定义以及预实例化单例的能力。
 * <p>此 org.springframework.beans.factory.BeanFactory 的子接口不适用于普通应用程序代码：
 * 对于典型用例，请使用 org.springframework.beans.factory.BeanFactory 或 ListableBeanFactory。
 * 此接口仅用于框架内部的即插即用，即使需要访问 bean 工厂配置方法时也是如此。
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    /**
     * 返回指定 bean 的已注册 BeanDefinition，允许访问其属性值和构造函数参数值
     * （这些值可在 bean 工厂后置处理过程中被修改）。
     * <p>返回的 BeanDefinition 对象不应是副本，而应是在工厂中注册的原始定义对象。
     * 这意味着，如有必要，它应可转换为更具体的实现类型。
     * <p>注意：此方法不考虑祖先工厂。它仅用于访问此工厂的本地 bean 定义。
     * @param beanName bean 的名称
     * @return 已注册的 BeanDefinition
     * @throws BeansException 如果此工厂中没有定义给定名称的 bean
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 确保所有非懒加载的单例都被实例化，同时也会考虑 FactoryBean 的情况。通常在工厂设置完成时调用（如果需要）。
     * @throws BeansException 如果某个单例 bean 无法创建。注意：这可能导致工厂中部分 bean 已被初始化！在这种情况下，
     * 应调用 destroySingletons() 进行完全清理。
     */
    void preInstantiateSingletons() throws BeansException;
}
