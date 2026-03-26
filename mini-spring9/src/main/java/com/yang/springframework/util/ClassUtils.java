package com.yang.springframework.util;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Exception ignored) {}

        if (cl == null) {
            cl = ClassUtils.class.getClassLoader();
        }

        return cl;
    }
}
