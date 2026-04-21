package com.yang.springframework.beans.factory;

/**
 * 由需要在BeanFactory设置完其所有属性后做出响应的bean实现的接口：例如，执行自定义初始化，或仅检查所有必需属性都已设置。
 * <p>实现InitializingBean的替代方案是指定自定义初始化方法，
 * 例如在XML bean定义中配置。有关所有bean生命周期方法的列表，请参见BeanFactory的Javadoc。
 */
public interface InitializingBean {

    /**
     * 在BeanFactory设置完所有bean属性并满足BeanFactoryAware、ApplicationContextAware等之后，由包含该bean的BeanFactory调用。
     * <p>当所有bean属性都设置完毕后，此方法允许bean实例对其整体配置进行验证并执行最终初始化。
     * @throws Exception 如果配置错误（例如未能设置必要属性）或由于任何其他原因导致初始化失败
     */
    void afterPropertiesSet() throws Exception;
}
