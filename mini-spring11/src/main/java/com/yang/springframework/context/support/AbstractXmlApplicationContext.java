package com.yang.springframework.context.support;

import com.yang.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yang.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 用于ApplicationContext实现的便捷基类，从包含XmlBeanDefinitionReader所能理解的bean定义的XML文档中获取配置。
 * <p>子类只需实现getConfigResources和/或getConfigLocations方法。此外，它们可以重写getResourceByPath钩子，以环境特定的方式解释相对路径，
 * 和/或重写getResourcePatternResolver以扩展模式解析功能。
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 通过 XmlBeanDefinitionReader 加载 bean 定义。
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 返回一个资源位置数组，指向构建此上下文时应使用的 XML bean 定义文件。也可以包含位置模式，这些模式将通过 ResourcePatternResolver 进行解析。
     * <p>默认实现返回 null。子类可以重写此方法，以提供一组用于加载 bean 定义的资源位置。
     * @return 资源位置数组，如果没有则返回 null
     */
    protected abstract String[] getConfigLocations();
}
