package com.yang.springframework.beans.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>用于资源描述符的接口，它对底层资源的实际类型（如文件或类路径资源）进行了抽象。
 * <p>如果资源以物理形式存在，则可以为每个资源打开一个InputStream，但对于某些资源，
 * 可能只返回一个URL或File句柄。实际行为是特定于实现的。
 */
public interface Resource {

    /**
     * <p>返回底层资源内容的InputStream。
     * <p>通常期望每次调用都会创建一个新的流。
     * <p>当考虑像JavaMail这样的API时，这一要求尤为重要，因为在创建邮件附件时需要能够多次读取流。对于此类用例，要求每次getInputStream()调用都返回一个新的流。
     * @return 底层资源的输入流（不能为null）
     * @throws IOException 如果无法打开内容流
     */
    InputStream getInputStream() throws IOException;
}
