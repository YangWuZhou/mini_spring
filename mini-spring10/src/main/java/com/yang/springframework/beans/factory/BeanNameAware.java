package com.yang.springframework.beans.factory;

/**
 * 由希望感知其在 bean 工厂中 bean 名称的 bean 实现的接口。需要注意的是，通常不建议对象依赖其 bean 名称，
 * 因为这种依赖既可能对外部配置敏感，也可能是对 Spring API 的不必要依赖。
 * <p>有关所有 bean 生命周期方法的列表，请参见 BeanFactory 的 Javadoc。
 */
public interface BeanNameAware extends Aware {

    /**
     * 设置创建此bean的bean工厂中的bean名称。
     * <p>在普通bean属性填充完成后、初始化回调（如InitializingBean.afterPropertiesSet()或自定义的init-method）之前调用。
     * @param name bean在工厂中的名称。请注意，此名称是工厂中实际使用的bean名称，可能与最初指定的名称不同：特别是对于内部bean，
     *             实际bean名称可能通过添加"#..."后缀进行了唯一化处理。如果需要，可以使用BeanFactoryUtils.originalBeanName(String)
     *             方法来提取原始bean名称（不带后缀）。
     */
    void setBeanName(String name);
}
