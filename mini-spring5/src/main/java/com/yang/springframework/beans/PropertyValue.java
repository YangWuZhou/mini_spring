package com.yang.springframework.beans;

/**
 * <p>用于保存单个bean属性的信息和值的对象。此处使用对象而非简单地将所有属性存储在按属性名称键控的映射中，
 * 这样可以提供更高的灵活性，并能够以优化方式处理索引属性等。
 * <p>请注意，该值不必是最终所需的类型：BeanWrapper实现应处理任何必要的转换，因为此对象对将要应用到的对象一无所知。
 */
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
