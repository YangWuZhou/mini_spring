package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>共享bean实例的通用注册表，实现了SingletonBeanRegistry接口。
 * 允许注册应在注册表的所有调用者之间共享的单例实例，这些实例可通过bean名称获取。
 *
 * <p>同时支持注册DisposableBean实例（这些实例可能与已注册的单例对应，也可能不对应），
 * 以便在注册表关闭时销毁它们。可以注册bean之间的依赖关系，以确保正确的关闭顺序。
 *
 * <p>此类主要作为org.springframework.beans.factory.BeanFactory实现类的基类，
 * 将单例bean实例的通用管理功能抽象出来。
 * 请注意，org.springframework.beans.factory.config.ConfigurableBeanFactory接口
 * 扩展了SingletonBeanRegistry接口。
 *
 * <p>与继承自它的AbstractBeanFactory和DefaultListableBeanFactory不同，
 * 此类既不假设bean定义的概念，也不限定bean实例的具体创建过程。它也可以作为嵌套辅助类进行委托调用。
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /** 单例对象的缓存：bean名称到bean实例的映射。*/
    Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * <p>将指定的单例对象添加至本工厂的单例缓存中。
     *
     * <p>此方法用于预先注册单例对象。
     *
     * @param beanName bean名称
     * @param singletonObject 单例对象
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
