package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.FactoryBean;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.BeanPostProcessor;
import com.yang.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.yang.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>BeanFactory实现类的抽象基类，提供ConfigurableBeanFactory SPI的完整功能。
 * 不预设为可列举的bean工厂：因此也可作为从某些后端资源（其中bean定义访问是昂贵操作）获取bean定义的bean工厂实现的基类。
 *
 * <p>该类提供单例缓存（通过其基类DefaultSingletonBeanRegistry）、单例/原型判定、
 * FactoryBean处理、别名、子bean定义的bean定义合并以及bean销毁
 * （通过org.springframework.beans.factory.DisposableBean接口和自定义销毁方法）。
 * 此外，通过实现org.springframework.beans.factory.HierarchicalBeanFactory接口，
 * 它能够管理bean工厂层级结构（在遇到未知bean时委托给父工厂处理）。
 *
 * <p>子类需要实现的主要模板方法是getBeanDefinition和createBean，
 * 分别用于根据给定bean名称检索bean定义，以及根据给定bean定义创建bean实例。
 * 这些操作的默认实现可在DefaultListableBeanFactory和AbstractAutowireCapableBeanFactory中找到。
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /** 要应用的 BeanPostProcessor。 */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String name) throws BeansException {
        return this.doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return this.doGetBean(name, args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 返回指定bean的实例，该实例可能是共享的或独立的。
     * @param name 要获取的bean的名称
     * @param args 使用显式参数创建bean实例时使用的参数（仅在创建新实例而非获取现有实例时适用）
     * @return bean的实例
     */
    @SuppressWarnings("unchecked")
    protected <T> T doGetBean(final String name, final Object[] args) {
         Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = (T) createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean<?> factoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    /**
     * <p>返回给定 bean 名称对应的 bean 定义。子类通常应实现缓存，
     * 因为本类每次需要 bean 定义元数据时都会调用此方法。
     *
     * <p>根据具体 bean 工厂实现的性质，此操作可能开销较大（例如，由于需要查询外部注册表）。
     * 但对于可列举的 bean 工厂，这通常仅相当于一次本地哈希查找：
     * 因此该操作属于其公共接口的一部分。在这种情况下，同一实现可同时用于此模板方法及其公共接口方法。
     *
     * @param beanName 需要查找定义的 bean 名称
     * @return 该原型名称对应的 BeanDefinition（永不为 null）
     * @throws BeansException 发生错误时抛出
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * <p>为给定的合并bean定义（及参数）创建bean实例。若为子定义，该bean定义将已与父定义合并。
     *
     * <p>所有bean获取方法最终都委托此方法进行实际的bean创建。
     *
     * @param beanName bean的名称
     * @param beanDefinition bean的合并bean定义
     * @param args 使用显式参数创建bean实例时使用的参数（仅在创建新实例而非获取现有实例时适用）
     * @return bean的新实例
     * @throws BeansException 如果无法创建该bean
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 返回将应用于此工厂所创建的 bean 的 BeanPostProcessor 列表。
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
