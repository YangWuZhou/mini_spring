package com.yang.springframework.beans.core.io;

import cn.hutool.core.lang.Assert;
import com.yang.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于类路径资源的Resource实现。使用给定的ClassLoader或Class来加载资源。
 * <p>如果类路径资源位于文件系统中，则支持解析为java.io.File，但对于JAR中的资源不支持。始终支持解析为java.net.URL。
 */
public class ClassPathResource implements Resource {
    /** 用户提供的原始路径的内部表示，用于创建相对路径以及解析URL和InputStream。 */
    private final String path;

    private final ClassLoader classLoader;

    /**
     * 为 ClassLoader 使用创建一个新的 ClassPathResource。
     * <p>前导斜杠将被移除，因为 ClassLoader 资源访问方法不接受它。
     * <p>将使用默认类加载器来加载资源。
     * @param path 类路径中的绝对路径
     */
    public ClassPathResource(String path) {
        this(path, null);
    }

    /**
     * 为ClassLoader使用创建一个新的ClassPathResource。
     * <p>前导斜杠将被移除，因为ClassLoader资源访问方法不接受它。
     * <p>如果提供的ClassLoader为null，则将使用默认类加载器来加载资源。
     * @param path 类路径中的绝对路径
     * @param classLoader 用于加载资源的类加载器
     */
    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
        this.classLoader = classLoader == null ?
                ClassUtils.getDefaultClassLoader() : classLoader;
    }

    /**
     * 此实现为基础类路径资源打开一个InputStream（如果可用）。
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(
                    path + " cannot be opened because it does not exist");
        }
        return is;
    }
}
