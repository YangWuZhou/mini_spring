package com.yang.springframework.beans.factory.config;

import com.yang.springframework.beans.PropertyValues;

/**
 * <p>BeanDefinition描述了一个bean实例，该实例包含属性值、构造器参数值以及由具体实现提供的进一步信息。
 *
 * <p>这只是一个最小化接口：其主要目的是允许BeanFactoryPostProcessor对属性值及其他bean元数据进行内省和修改。
 */
public class BeanDefinition {
    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
