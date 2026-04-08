package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.core.io.Resource;
import com.yang.springframework.beans.core.io.ResourceLoader;

/**
 * 用于bean定义读取器的简单接口，规定了带有Resource和String位置参数的加载方法。
 * <p>当然，具体的bean定义读取器可以针对其特定的bean定义格式，添加额外的加载和注册方法。
 * <p>请注意，bean定义读取器并不强制实现此接口。它仅为希望遵循标准命名约定的bean定义读取器提供参考建议。
 */
public interface BeanDefinitionReader {

    /**
     * 返回用于注册 bean 定义的 bean 工厂。
     * <p>该工厂通过 BeanDefinitionRegistry 接口暴露，封装了与 bean 定义处理相关的方法。
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 返回用于资源位置的 ResourceLoader。
     * <p>可以检查其是否为 ResourcePatternResolver 接口并相应地进行转换，以加载给定资源模式的多个资源。
     * <p>返回 null 值表示此 bean 定义读取器不支持绝对资源加载。
     * <p>这主要用于从 bean 定义资源内部导入更多资源，例如通过 XML bean 定义中的 "import" 标签。
     * 但建议相对于定义资源来应用此类导入；只有显式的完整资源位置才会触发基于绝对路径的资源加载。
     * <p>此外，还提供了 loadBeanDefinitions(String) 方法，用于从资源位置（或位置模式）加载 bean 定义。
     * 这是一种避免显式处理 ResourceLoader 的便捷方法。
     */
    ResourceLoader getResourceLoader();

    /**
     * 从指定的资源中加载 bean 定义。
     * @param resource 资源描述符
     * @throws BeansException 如果加载或解析过程中出错
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 从指定的资源中加载 bean 定义。
     * @param resources 资源描述符
     * @throws BeansException 如果加载或解析过程中出错
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 从指定的资源位置加载 bean 定义。
     * <p>该位置也可以是位置模式，前提是此 bean 定义读取器的 ResourceLoader 是 ResourcePatternResolver。
     * @param location 资源位置，将使用此 bean 定义读取器的 ResourceLoader（或 ResourcePatternResolver）进行加载
     * @throws BeansException 如果加载或解析过程中出错
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * 从指定的资源位置加载bean定义。
     * @param locations 资源位置，将使用此bean定义读取器的ResourceLoader（或ResourcePatternResolver）进行加载
     * @throws BeansException 如果加载或解析过程中出错
     */
    void loadBeanDefinitions(String... locations) throws BeansException;
}
