package com.yang.springframework.beans.factory.config;

/**
 * <p>定义共享bean实例注册表的接口。
 * 可由org.springframework.beans.factory.BeanFactory的实现类继承，以便以统一的方式公开其单例管理功能。
 *
 * <p>ConfigurableBeanFactory接口扩展了此接口。
 */
public interface SingletonBeanRegistry {

    /**
     * <p>返回在给定名称下注册的（原始）单例对象。
     *
     * <p>此方法仅检查已实例化的单例；对于尚未实例化的单例bean定义，不会返回对象。
     *
     * <p>该方法的主要用途是访问手动注册的单例（参见registerSingleton）。
     * 也可用于以原始方式访问已根据bean定义创建的单例。
     *
     * <p>注意： 此查找方法不识别FactoryBean前缀或别名。在获取单例实例之前，需要先解析出规范的bean名称。
     *
     * @param beanName 要查找的bean名称
     * @return 已注册的单例对象，如果未找到则返回null
     */
    Object getSingleton(String beanName);
}
