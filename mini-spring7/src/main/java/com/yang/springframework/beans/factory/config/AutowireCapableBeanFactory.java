package com.yang.springframework.beans.factory.config;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.BeanFactory;

/**
 * BeanFactory接口的扩展，由能够进行自动装配的bean工厂实现，前提是它们希望为现有bean实例公开此功能。
 * <p>此BeanFactory的子接口不适用于普通应用程序代码：
 * 对于典型用例，请直接使用BeanFactory或org.springframework.beans.factory.ListableBeanFactory。
 * <p>其他框架的集成代码可以利用此接口来装配和填充Spring不控制生命周期的现有bean实例。
 * 例如，这对于WebWork Actions和Tapestry Page对象特别有用。
 * <p>请注意，此接口并非由org.springframework.context.ApplicationContext外观实现，
 * 因为应用程序代码几乎从不使用它。尽管如此，它也可从应用程序上下文中获取，
 * 通过ApplicationContext的org.springframework.context.ApplicationContext.getAutowireCapableBeanFactory()方法访问。
 * <p>您还可以实现org.springframework.beans.factory.BeanFactoryAware接口，
 * 该接口即使在ApplicationContext中运行时也会暴露内部BeanFactory，
 * 从而获得AutowireCapableBeanFactory：只需将传入的BeanFactory转换为AutowireCapableBeanFactory即可。
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 将 BeanPostProcessors 应用于给定的现有 bean 实例，
     * 调用它们的 postProcessBeforeInitialization 方法。返回的 bean 实例可能是原始实例的包装器。
     * <p>已弃用
     * <p>自 6.1 起，推荐通过 initializeBean(Object, String) 进行隐式后置处理
     * @param existingBean 现有的 bean 实例
     * @param beanName bean 的名称，必要时传递给处理器（仅传递给 BeanPostProcessors；可以遵循 ORIGINAL_INSTANCE_SUFFIX 约定以强制返回给定实例，即不返回代理等）
     * @return 要使用的 bean 实例，可以是原始实例或包装后的实例
     * @throws BeansException 如果任何后置处理失败
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 将 BeanPostProcessors 应用于给定的现有 bean 实例，
     * 调用它们的 postProcessAfterInitialization 方法。返回的 bean 实例可能是原始实例的包装器。
     * <p>已弃用
     * <p>自 6.1 起，推荐通过 initializeBean(Object, String) 进行隐式后置处理
     * @param existingBean 现有的 bean 实例
     * @param beanName bean 的名称，必要时传递给处理器（仅传递给 BeanPostProcessors；可以遵循 ORIGINAL_INSTANCE_SUFFIX 约定以强制返回给定实例，即不返回代理等）
     * @return 要使用的 bean 实例，可以是原始实例或包装后的实例
     * @throws BeansException 如果任何后置处理失败
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
