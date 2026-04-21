package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持需要处理 FactoryBean 实例的单例注册表的基类，与 DefaultSingletonBeanRegistry 的单例管理相集成。
 * <p>作为 AbstractBeanFactory 的基类。
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /** 由 FactoryBean 创建的单例对象缓存：FactoryBean 名称到对象的映射。 */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 从给定的 FactoryBean 中获取要暴露的对象（如果缓存中存在）。快速检查，实现最小同步。
     * @param beanName bean 的名称
     * @return 从 FactoryBean 获取的对象，如果不可用则返回 null
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT) ? object : null;
    }

    /**
     * 从给定的 FactoryBean 中获取要暴露的对象。
     * @param factory FactoryBean 实例
     * @param beanName bean 的名称
     * @return 从 FactoryBean 获取的对象
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null) ? object : NULL_OBJECT);
            }

            return (object != NULL_OBJECT) ? object : null;
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 从给定的 FactoryBean 中获取要暴露的对象。
     * @param factory FactoryBean 实例
     * @param beanName bean 的名称
     * @return 从 FactoryBean 获取的对象
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
