package com.yang.springframework.beans.factory.config;

/**
 * BeanFactory接口的扩展，由能够进行自动装配的bean工厂实现，前提是它们希望为现有bean实例公开此功能。
 * <p>此BeanFactory的子接口不适用于普通应用程序代码：
 * 对于典型用例，请直接使用BeanFactory或org.springframework.beans.factory.ListableBeanFactory。
 * <p>其他框架的集成代码可以利用此接口来装配和填充Spring不控制生命周期的现有bean实例。
 * 例如，这对于WebWork Actions和Tapestry Page对象特别有用。
 * <p>请注意，此接口并非由org.springframework.context.ApplicationContext外观实现，
 * 因为应用程序代码几乎从不使用它。尽管如此，它也可从应用程序上下文中获取，
 * 通过ApplicationContext的org.springframework.context.ApplicationContext.getAutowireCapableBeanFactory()方法访问。
 * <p>您还可以实现org.springframework.beans.factory.BeanFactoryAware接口，
 * 该接口即使在ApplicationContext中运行时也会暴露内部BeanFactory，
 * 从而获得AutowireCapableBeanFactory：只需将传入的BeanFactory转换为AutowireCapableBeanFactory即可。
 */
public interface AutowireCapableBeanFactory {
}
