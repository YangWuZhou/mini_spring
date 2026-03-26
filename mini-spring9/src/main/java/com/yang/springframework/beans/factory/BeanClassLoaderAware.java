package com.yang.springframework.beans.factory;

/**
 * 回调接口，允许 bean 感知 bean 类加载器；即当前 bean 工厂用于加载 bean 类的类加载器。
 * <p>此接口主要供框架类实现，这些框架类需要按名称获取应用程序类，尽管它们自身可能从共享类加载器加载。
 * <p>有关所有 bean 生命周期方法的列表，请参见 BeanFactory 的 Javadoc。
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * 回调接口，用于将 bean 类加载器提供给 bean 实例。
     * <p>在普通 bean 属性填充完成后、初始化回调（如 InitializingBean 的 afterPropertiesSet() 方法或自定义 init-method）之前调用。
     * @param classLoader 所属的类加载器
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
