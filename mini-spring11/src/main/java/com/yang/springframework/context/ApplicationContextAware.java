package com.yang.springframework.context;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.Aware;

/**
 * 由任何希望获知其运行所在的ApplicationContext的对象所实现的接口。
 * <p>例如，当一个对象需要访问一组协作的bean时，实现此接口是有意义的。需要注意的是，通过bean引用进行配置优于仅为bean查找目的而实现此接口。
 * <p>如果对象需要访问文件资源（即希望调用getResource）、希望发布应用事件或需要访问MessageSource，也可以实现此接口。但在这种特定场景下，
 * 更推荐实现更具体的ResourceLoaderAware、ApplicationEventPublisherAware或MessageSourceAware接口。
 * <p>请注意，文件资源依赖也可以通过类型为org.springframework.core.io.Resource的bean属性暴露，并通过bean工厂利用字符串自动类型转换进行填充。
 * 这消除了仅为访问特定文件资源而实现任何回调接口的必要性。
 * <p>org.springframework.context.support.ApplicationObjectSupport是一个为应用对象提供的便捷基类，实现了此接口。
 * <p>有关所有bean生命周期方法的列表，请参见BeanFactory的javadoc。
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置此对象运行所在的 ApplicationContext。通常，此调用将用于初始化对象。
     * <p>在普通 bean 属性填充之后、但在初始化回调（如 org.springframework.beans.factory.InitializingBean.afterPropertiesSet()
     * 或自定义 init-method）之前调用。如果适用，将在 ResourceLoaderAware.setResourceLoader、
     * ApplicationEventPublisherAware.setApplicationEventPublisher 和 MessageSourceAware 之后调用。
     * @param applicationContext 此对象将要使用的 ApplicationContext 对象
     * @throws BeansException 如果 application context 方法抛出异常
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
