package com.yang.springframework.context.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yang.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 用于支持多次调用refresh()的ApplicationContext实现的基类，每次刷新都会创建一个新的内部bean工厂实例。
 * 通常（但不一定）此类上下文将由一组用于加载bean定义的配置位置驱动。
 * <p>子类唯一需要实现的方法是loadBeanDefinitions，该方法在每次刷新时被调用。具体实现应将bean定义加载到
 * 给定的DefaultListableBeanFactory中，通常委托给一个或多个特定的bean定义读取器。
 * <p>请注意，WebApplicationContext也有一个类似的基类。
 * org.springframework.web.context.support.AbstractRefreshableWebApplicationContext提供了相同的子类化策略，
 * 但额外预先实现了Web环境下的所有上下文功能。此外，对于Web上下文，还有一种预定义的方式来接收配置位置。
 * <p>此基类的具体独立子类，用于读取特定bean定义格式，包括ClassPathXmlApplicationContext和FileSystemXmlApplicationContext，
 * 它们都派生自共同的AbstractXmlApplicationContext基类；org.springframework.context.annotation.AnnotationConfigApplicationContext
 * 支持将@Configuration注解的类作为bean定义的来源。
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    /** 此上下文的 Bean 工厂。 */
    private DefaultListableBeanFactory beanFactory;

    /**
     * 此实现会实际刷新上下文底层的 bean 工厂，关闭之前的 bean 工厂（如果有），并为上下文生命周期的下一阶段初始化一个新的 bean 工厂。
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 为此上下文创建一个内部 bean 工厂。每次调用 refresh() 时都会执行此操作。
     * <p>默认实现会创建一个 DefaultListableBeanFactory，并将此上下文的父级内部 bean 工厂作为其父 bean 工厂。
     * 子类可以覆盖此方法，例如用于自定义 DefaultListableBeanFactory 的设置。
     * @return 此上下文的 bean 工厂
     */
    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 将 bean 定义加载到给定的 bean 工厂中，通常通过委托给一个或多个 bean 定义读取器来完成。
     * @param beanFactory 要加载 bean 定义的目标 bean 工厂
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
