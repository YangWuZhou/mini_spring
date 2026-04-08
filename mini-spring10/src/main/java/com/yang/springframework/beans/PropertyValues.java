package com.yang.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含一个或多个PropertyValue对象的持有者，通常构成对特定目标bean的一次更新。
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        propertyValues.add(pv);
    }

    /**
     * 返回此对象中所持有的PropertyValue对象数组。
     */
    public PropertyValue[] getPropertyValues() {
        return propertyValues.toArray(new PropertyValue[0]);
    }

    /**
     * 返回具有给定名称的属性值（如果有）。
     * @param propertyName 要搜索的名称
     * @return 属性值，如果没有则返回 null
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValues) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }
}
