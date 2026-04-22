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

    public static boolean isCglibProxyClass(Class<?> clazz) {
        return clazz != null && isCglibProxyClassName(clazz.getName());
    }

    private static boolean isCglibProxyClassName(String className) {
        return className != null && className.contains("$$");
    }
}
