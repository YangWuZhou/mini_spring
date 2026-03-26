package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.core.io.DefaultResourceLoader;
import com.yang.springframework.beans.core.io.ResourceLoader;

/**
 * 实现 BeanDefinitionReader 接口的 bean 定义读取器的抽象基类。
 * <p>提供通用属性，例如要操作的 bean 工厂以及用于加载 bean 类的类加载器。
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private final ResourceLoader resourceLoader;

    /**
     * 为给定的 bean 工厂创建一个新的 AbstractBeanDefinitionReader。
     * <p>如果传入的 bean 工厂不仅实现了 BeanDefinitionRegistry 接口，还实现了 ResourceLoader 接口，
     * 则它也将用作默认的 ResourceLoader。对于 org.springframework.context.ApplicationContext 的实现来说，通常情况如此。
     * <p>如果给定的是普通的 BeanDefinitionRegistry，则默认的 ResourceLoader 将是 PathMatchingResourcePatternResolver。
     * <p>如果传入的 bean 工厂还实现了 EnvironmentCapable，则该 reader 将使用其环境；否则，reader 将初始化并使用 StandardEnvironment。
     * 所有 ApplicationContext 实现都是 EnvironmentCapable 的，而普通的 BeanFactory 实现则不是。
     * @param registry 以 BeanDefinitionRegistry 形式存在的 BeanFactory，用于加载 bean 定义
     */
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }
}
