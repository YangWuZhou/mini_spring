package com.yang.springframework.context.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.core.io.DefaultResourceLoader;
import com.yang.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yang.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.yang.springframework.beans.factory.config.BeanPostProcessor;
import com.yang.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * ApplicationContext接口的抽象实现。不强制要求配置的存储类型；仅实现通用的上下文功能。采用模板方法设计模式，要求具体子类实现抽象方法。
 * <p>与普通的BeanFactory不同，ApplicationContext会检测其内部bean工厂中定义的特殊bean：因此，
 * 此类会自动注册在上下文中定义为bean的BeanFactoryPostProcessor、BeanPostProcessor和ApplicationListener。
 * <p>MessageSource也可以作为上下文中名为"messageSource"的bean提供；否则，消息解析将委托给父上下文。
 * 此外，应用程序事件的多播器可以作为类型为ApplicationEventMulticaster、名为"applicationEventMulticaster"的bean在上下文中提供；
 * 否则，将使用类型为SimpleApplicationEventMulticaster的默认多播器。
 * <p>通过扩展DefaultResourceLoader实现资源加载。因此，除非子类中重写了getResourceByPath方法，
 * 否则会将非URL资源路径视为类路径资源（支持包含包路径的完整类路径资源名称，例如"mypackage/myresource.dat"）。
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        invokeBeanFactoryPostProcessors(beanFactory);

        registerBeanPostProcessors(beanFactory);

        beanFactory.preInstantiateSingletons();
    }

    /**
     * 子类必须实现此方法以执行实际的配置加载。该方法在 refresh() 中、其他任何初始化工作之前被调用。
     * <p>子类可以创建一个新的 bean 工厂并持有其引用，或者返回其持有的单个 BeanFactory 实例。
     * 在后一种情况下，如果多次刷新上下文，通常会抛出 IllegalStateException。
     * @throws BeansException 如果 bean 工厂初始化失败
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 子类必须在此处返回其内部 bean 工厂。子类应高效地实现查找，以便能够重复调用而不会影响性能。
     * <p>注意： 子类在返回内部 bean 工厂之前应检查上下文是否仍处于活动状态。一旦上下文关闭，内部工厂通常应被视为不可用。
     * @return 此应用上下文的内部 bean 工厂（永不为 null）
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 实例化并调用所有已注册的BeanFactoryPostProcessor bean，如果指定了显式顺序则遵守该顺序。
     * <p>必须在单例实例化之前调用。
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 实例化并注册所有BeanPostProcessor bean，如果指定了显式顺序则遵守该顺序。
     * <p>必须在任何应用bean实例化之前调用。
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    /**
     * 向 JVM 运行时注册一个名为 SpringContextShutdownHook 的关闭钩子，在 JVM 关闭时关闭此上下文（除非此时上下文已被关闭）。
     * <p>实际的关闭过程委托给 doClose() 方法执行。
     */
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    /**
     * 关闭此应用上下文，销毁其 bean 工厂中的所有 bean。
     * <p>实际的关闭过程委托给 doClose() 方法执行。同时，如果已注册 JVM 关闭钩子，则会将其移除，因为不再需要它。
     */
    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }
}
