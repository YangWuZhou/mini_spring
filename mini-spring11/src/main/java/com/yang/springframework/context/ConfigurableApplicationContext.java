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

    /**
     * 向 JVM 运行时注册一个关闭钩子，该钩子将在 JVM 关闭时关闭此上下文（除非此时上下文已被关闭）。
     * <p>此方法可被多次调用。对于每个上下文实例，最多只会注册一个关闭钩子。
     * <p>自 Spring Framework 5.2 起，关闭钩子线程的名称应为 SHUTDOWN_HOOK_THREAD_NAME。
     */
    void registerShutdownHook();

    /**
     * 关闭此应用上下文，释放其实现可能持有的所有资源和锁。这包括销毁所有缓存的单例 bean。
     * <p>注意：不会调用父上下文的 close 方法；父上下文拥有独立于自身的生命周期。
     * <p>此方法可多次调用且无副作用：后续对已关闭上下文的 close 调用将被忽略。
     */
    void close();
}
