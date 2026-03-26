package com.yang.springframework.context.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.config.BeanPostProcessor;
import com.yang.springframework.context.ApplicationContext;
import com.yang.springframework.context.ApplicationContextAware;

/**
 * 实现了 BeanPostProcessor 接口的处理器，用于向实现了 EnvironmentAware、EmbeddedValueResolverAware、ResourceLoaderAware、
 * ApplicationEventPublisherAware、MessageSourceAware、ApplicationStartupAware 和/或 ApplicationContextAware 接口的 bean
 * 提供 ApplicationContext、Environment、StringValueResolver 或 ApplicationStartup 等对象。
 * <p>上述接口将按照列出的顺序依次得到满足。
 * <p>应用上下文会自动将此处理器注册到底层的 bean 工厂中。应用程序不应直接使用此类。
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    /**
     * 为给定的上下文创建一个新的 ApplicationContextAwareProcessor。
     */
    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
