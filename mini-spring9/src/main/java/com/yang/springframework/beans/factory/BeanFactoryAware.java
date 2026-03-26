package com.yang.springframework.beans.factory;

import com.yang.springframework.beans.BeansException;

/**
 * 由希望感知其所属 BeanFactory 的 bean 实现的接口。
 * <p>例如，bean 可以通过工厂（依赖查找）来查找协作的 bean。需要注意的是，大多数 bean 会选择通过对应的 bean 属性或构造器参数（依赖注入）来接收对协作 bean 的引用。
 * <p>有关所有 bean 生命周期方法的列表，请参见 BeanFactory 的 Javadoc。
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 回调接口，用于将所属工厂提供给 bean 实例。
     * <p>在普通 bean 属性填充完成后、初始化回调（如 InitializingBean.afterPropertiesSet() 或自定义 init-method）之前调用。
     * @param beanFactory 所属的 BeanFactory（永远不为 null）。该 bean 可以立即调用工厂中的方法。
     * @throws BeansException 如果初始化过程中出错
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
