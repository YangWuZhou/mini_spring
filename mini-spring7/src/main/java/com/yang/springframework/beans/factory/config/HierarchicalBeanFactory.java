package com.yang.springframework.beans.factory.config;

import com.yang.springframework.beans.factory.BeanFactory;

/**
 * 可以由作为层次结构一部分的bean工厂实现的子接口。
 * <p>对应的setParentBeanFactory方法（用于允许以可配置方式设置父工厂的bean工厂）
 * 可以在ConfigurableBeanFactory接口中找到。
 */
public interface HierarchicalBeanFactory extends BeanFactory {
}
