package com.yang.springframework.beans.factory.config;

/**
 * 大多数 bean 工厂实现的配置接口。除了 BeanFactory 接口中的 bean 工厂客户端方法外，还提供配置 bean 工厂的功能。
 *
 * <p>此 bean 工厂接口不适用于普通应用程序代码：对于典型需求，请使用 BeanFactory 或
 * org.springframework.beans.factory.ListableBeanFactory。
 * 此扩展接口仅用于框架内部的即插即用以及对 bean 工厂配置方法的特殊访问。
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    /**
     * 标准单例作用域的作用域标识符："singleton"。
     * <p>可以通过 registerScope 添加自定义作用域。
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 标准原型作用域的作用域标识符："prototype"。
     * <p>可以通过 registerScope 添加自定义作用域。
     */
    String SCOPE_PROTOTYPE = "prototype";
}
