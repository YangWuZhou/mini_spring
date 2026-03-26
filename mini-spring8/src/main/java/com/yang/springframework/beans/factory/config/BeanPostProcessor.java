package com.yang.springframework.beans.factory.config;

import com.yang.springframework.beans.BeansException;

/**
 * 工厂钩子，允许对新建的 bean 实例进行自定义修改 — 例如，检查标记接口或使用代理包装 bean。
 * <p>通常，通过标记接口等方式填充 bean 的后处理器会实现 postProcessBeforeInitialization，
 * 而使用代理包装 bean 的后处理器通常会实现 postProcessAfterInitialization。
 * <h3>注册</h3>
 * ApplicationContext 可以在其 bean 定义中自动检测 BeanPostProcessor bean，并将这些后处理器应用于随后创建的所有 bean。
 * 而普通的 BeanFactory 允许以编程方式注册后处理器，并将它们应用于通过该 bean 工厂创建的所有 bean。
 * <h3>排序</h3>
 * 在 ApplicationContext 中自动检测到的 BeanPostProcessor bean 将按照 org.springframework.core.PriorityOrdered 和
 * org.springframework.core.Ordered 语义进行排序。相比之下，通过编程方式注册到 BeanFactory 的 BeanPostProcessor bean 将按照注册顺序应用；
 * 对于编程式注册的后处理器，通过实现 PriorityOrdered 或 Ordered 接口表达的任何排序语义都将被忽略。
 * 此外，@Order 注解不适用于 BeanPostProcessor bean。
 */
public interface BeanPostProcessor {

    /**
     * 将此BeanPostProcessor应用于给定的新bean实例，在任何bean初始化回调（如InitializingBean的afterPropertiesSet或自定义的init-method）之前执行。
     * 该bean此时已完成属性值的填充。返回的bean实例可能是原始实例的包装器。
     * <p>默认实现按原样返回给定的bean。
     * @param bean 新的bean实例
     * @param beanName bean的名称
     * @return 要使用的bean实例，可以是原始实例或包装后的实例；如果返回null，则后续的BeanPostProcessor将不会被调用
     * @throws BeansException 如果发生错误
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在任何bean初始化回调（如InitializingBean的afterPropertiesSet或自定义的init-method）之后，
     * 将此BeanPostProcessor应用于给定的新bean实例。该bean此时已完成属性值的填充。返回的bean实例可能是原始实例的包装器。
     * <p>对于FactoryBean的情况，此回调将同时应用于FactoryBean实例和由FactoryBean创建的对象（自Spring 2.0起）。
     * 后处理器可以通过相应的bean instanceof FactoryBean检查，决定是应用于FactoryBean、创建的对象，还是两者都应用。
     * <p>与所有其他BeanPostProcessor回调不同，此回调也会在由InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation
     * 方法触发的短路机制之后被调用。
     * <p>默认实现按原样返回给定的bean。
     * @param bean 新的bean实例
     * @param beanName bean的名称
     * @return 要使用的bean实例，可以是原始实例或包装后的实例；如果返回null，则后续的BeanPostProcessor将不会被调用
     * @throws BeansException 如果发生错误
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
