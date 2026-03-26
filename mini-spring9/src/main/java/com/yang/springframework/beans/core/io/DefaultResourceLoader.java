package com.yang.springframework.beans.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ResourceLoader接口的默认实现。
 * <p>由ResourceEditor使用，并作为org.springframework.context.support.AbstractApplicationContext的基类。也可以单独使用。
 * <p>如果位置值是URL，则返回UrlResource；如果是非URL路径或"classpath:"伪URL，则返回ClassPathResource。
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
