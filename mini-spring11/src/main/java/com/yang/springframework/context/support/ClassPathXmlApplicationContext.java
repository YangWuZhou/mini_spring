package com.yang.springframework.context.support;

/**
 * 独立的 XML 应用上下文，从类路径中读取上下文定义文件，将普通路径解释为包含包路径的类路径资源名称（例如 "mypackage/myresource.txt"）。
 * 适用于测试环境以及嵌入 JAR 包中的应用上下文。
 * <p>配置位置的默认值可通过 getConfigLocations 进行覆盖。配置位置既可以表示具体的文件，如 "/myfiles/context.xml"，
 * 也可以表示 Ant 风格的模式，如 "/myfiles/*-context.xml"（模式的具体细节请参见 org.springframework.util.AntPathMatcher 的 Javadoc）。
 * <p>注意：当提供多个配置位置时，后加载的文件中定义的 bean 会覆盖先加载的文件中定义的 bean。这一特性可用于通过额外的 XML 文件有意覆盖某些 bean 定义。
 * <p>这是一个简单的一站式便捷 ApplicationContext。若需要更灵活的上下文设置，可考虑将 GenericApplicationContext 与
 * org.springframework.beans.factory.xml.XmlBeanDefinitionReader 结合使用。
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configLocations;

    /**
     * 创建一个新的 ClassPathXmlApplicationContext，从给定的 XML 文件中加载定义，并自动刷新上下文。
     * @param configLocation 资源位置
     */
    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    /**
     * 创建一个新的 ClassPathXmlApplicationContext，从给定的 XML 文件中加载定义，并自动刷新上下文。
     * @param configLocations 资源位置数组
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
