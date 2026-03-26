package com.yang.springframework.beans.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 用于java.net.URL定位器的Resource实现。支持解析为URL，并且在"file:"协议的情况下也支持解析为File。
 */
public class UrlResource implements Resource {
    /** 原始URL，用于实际访问。 */
     private final URL url;

    /**
     * 根据给定的URL对象创建一个新的UrlResource。
     * @param url 一个URL
     */
    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }

    /**
     * 此实现为给定的URL打开一个InputStream。它将useCaches标志设置为false，
     * 主要是为了避免在Windows上发生jar文件锁定。
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection conn = url.openConnection();
        try {
            return conn.getInputStream();
        } catch (IOException e) {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
            throw e;
        }
    }
}
