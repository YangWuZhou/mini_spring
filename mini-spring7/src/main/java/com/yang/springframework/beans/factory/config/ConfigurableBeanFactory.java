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

    /**
     * 添加一个新的BeanPostProcessor，该处理器将应用于此工厂创建的bean。在工厂配置期间调用。
     * <p>注意： 此处提交的后处理器将按注册顺序应用；通过实现org.springframework.core.Ordered接口表达的任何排序语义都将被忽略。
     * 请注意，自动检测到的后处理器（例如作为ApplicationContext中的bean）将始终在编程式注册的后处理器之后应用。
     * @param beanPostProcessor 要注册的后处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
