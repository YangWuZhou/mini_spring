package com.yang.springframework.beans.core.io;

/**
 * <p>用于加载资源（例如类路径或文件系统资源）的策略接口。
 * 要求org.springframework.context.ApplicationContext提供此功能以及扩展的
 * org.springframework.core.io.support.ResourcePatternResolver支持。
 * <p>DefaultResourceLoader是一个独立的实现，既可在ApplicationContext之外使用，也被ResourceEditor采用。
 * <p>在ApplicationContext中运行时，Resource和Resource[]类型的Bean属性可以通过字符串填充，
 * 具体使用该上下文的资源加载策略。
 */
public interface ResourceLoader {
    /** 用于从类路径加载的伪URL前缀："classpath:"。*/
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 返回指定资源位置的Resource句柄。
     * <p>该句柄应始终是可重用的资源描述符，允许进行多次Resource.getInputStream()调用。
     * </p><ul>
     * <li>必须支持完整的URL，例如"file:C:/test.dat"。
     * <li>必须支持类路径伪URL，例如"classpath:test.dat"。
     * <li>应支持相对文件路径，例如"WEB-INF/test.dat"。（这将是特定于实现的，通常由ApplicationContext实现提供。）
     * </ul>
     * <p>请注意，Resource句柄并不表示资源一定存在；您需要调用Resource.exists()来检查其是否存在。
     * @param location 资源位置
     * @return 相应的Resource句柄（从不返回null）
     */
    Resource getResource(String location);
}
