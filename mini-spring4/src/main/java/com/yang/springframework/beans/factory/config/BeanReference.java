package com.yang.springframework.beans.factory.config;

/**
 * <p>以抽象方式暴露对 bean 名称的引用的接口。该接口不一定表示对实际 bean 实例的引用；
 * 它仅表达对 bean 名称的逻辑引用。
 * <p>作为任何类型的 bean 引用持有者（例如 RuntimeBeanReference 和 RuntimeBeanNameReference）
 * 所实现的公共接口。
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    /**
     * 返回此引用指向的目标 bean 名称（不为 null）。
     */
    public String getBeanName() {
        return beanName;
    }

}
