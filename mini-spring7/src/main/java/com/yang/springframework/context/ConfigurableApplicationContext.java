package com.yang.springframework.context;

import com.yang.springframework.beans.BeansException;

/**
 * 大多数（即便不是全部）应用程序上下文都应实现的 SPI 接口。除了 ApplicationContext 接口中的应用程序上下文客户端方法之外，
 * 该接口还提供了配置应用程序上下文的设施。
 * <p>配置和生命周期方法在此处被封装起来，以避免对 ApplicationContext 客户端代码可见。当前方法应仅由启动和关闭代码使用。
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 加载或刷新配置的持久化表示，该配置可以来自基于Java的配置、XML文件、属性文件、关系数据库模式或其他格式。
     * <p>由于这是一个启动方法，如果失败，它应销毁已创建的单例，以避免产生悬挂的资源。换句话说，在调用此方法后，应该要么全部单例都被实例化，要么一个都不被实例化。
     * @throws BeansException 如果 bean 工厂无法初始化
     */
    void refresh() throws BeansException;
}
