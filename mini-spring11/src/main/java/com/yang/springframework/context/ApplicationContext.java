package com.yang.springframework.context;

import com.yang.springframework.beans.core.io.ResourceLoader;
import com.yang.springframework.beans.factory.HierarchicalBeanFactory;
import com.yang.springframework.beans.factory.ListableBeanFactory;

/**
 * 为应用程序提供配置的核心接口。当应用程序运行时，此接口为只读状态，但如果实现支持，也可以重新加载。
 * <p>ApplicationContext 提供以下功能：
 * <ul>
 *     <li>用于访问应用程序组件的 Bean 工厂方法，继承自 ListableBeanFactory。
 *     <li>以通用方式加载文件资源的能力，继承自 org.springframework.core.io.ResourceLoader 接口。
 *     <li>向注册的监听器发布事件的能力，继承自 ApplicationEventPublisher 接口。
 *     <li>解析消息的能力，支持国际化，继承自 MessageSource 接口。
 *     <li>支持从父上下文继承。后代上下文中的定义始终具有优先权。这意味着，例如，整个 Web 应用程序可以使用一个单一的父上下文，
 *     而每个 Servlet 拥有自己独立的子上下文，且该子上下文与其他 Servlet 的子上下文相互独立。
 * </ul>
 * 除了标准的 org.springframework.beans.factory.BeanFactory 生命周期功能外，ApplicationContext 的实现还会检测并调用 ApplicationContextAware
 * 类型的 Bean，以及 ResourceLoaderAware、ApplicationEventPublisherAware 和 MessageSourceAware 类型的 Bean。
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
