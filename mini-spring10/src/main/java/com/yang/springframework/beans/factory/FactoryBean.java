package com.yang.springframework.beans.factory;

/**
 * 由 BeanFactory 内部使用的对象实现的接口，这些对象本身就是单个对象的工厂。如果一个 bean 实现了此接口，
 * 那么它将被用作工厂来创建一个要暴露的对象，而不是直接作为自身将被暴露的 bean 实例。
 * <p>注意：实现了此接口的 bean 不能作为普通 bean 使用。FactoryBean 以 bean 的风格定义，但为 bean 引用所暴露的对象（通过 getObject() 获取）始终是其创建的对象。
 * <p>FactoryBean 可以支持单例和原型模式，并且可以按需延迟创建对象，也可以在启动时预先创建对象。SmartFactoryBean 接口允许暴露更细粒度的行为元数据。
 * <p>此接口在框架内部被广泛使用，例如用于 AOP 的 org.springframework.aop.framework.ProxyFactoryBean 或 org.springframework.jndi.JndiObjectFactoryBean。
 * 它也可以用于自定义组件；不过，这通常只适用于基础设施代码。
 * <p>FactoryBean 是一个程序化契约。实现类不应依赖注解驱动的注入或其他反射机制。getObjectType() 和 getObject() 的调用可能在启动过程的早期就发生，
 * 甚至在任何后处理器设置之前。如果需要访问其他 bean，请实现 BeanFactoryAware 并以编程方式获取它们。
 * <p>容器仅负责管理 FactoryBean 实例的生命周期，而不负责管理 FactoryBean 所创建的对象的生命周期。因此，暴露的 bean 对象上的销毁方法
 * （例如 java.io.Closeable.close()）不会被自动调用。相反，FactoryBean 应该实现 DisposableBean 并将此类关闭调用委托给底层对象。
 * <p>最后，FactoryBean 对象参与其所属 BeanFactory 对 bean 创建的同步。除了在 FactoryBean 自身内部用于延迟初始化等目的外，通常不需要内部同步。
 */
public interface FactoryBean<T> {

    /**
     * 返回此工厂所管理的对象的一个实例（可能是共享的，也可能是独立的）。
     * <p>与 BeanFactory 类似，此方法支持单例和原型两种设计模式。
     * <p>如果在调用时此 FactoryBean 尚未完全初始化（例如因为涉及循环引用），则应抛出相应的 FactoryBeanNotInitializedException 异常。
     * <p>从 Spring 2.0 开始，FactoryBean 允许返回 null 对象。工厂会将其视为可用的正常值，此时不再抛出 FactoryBeanNotInitializedException 异常。
     * 现在鼓励 FactoryBean 实现根据自身情况适时抛出 FactoryBeanNotInitializedException。
     * @return bean 的一个实例（可能为 null）
     * @throws Exception 如果创建过程中出错
     */
    T getObject() throws Exception;

    /**
     * 返回此 FactoryBean 所创建对象的类型，如果无法提前获知则返回 null。
     * <p>这样可以在不实例化对象的情况下检查特定类型的 bean，例如在自动装配时使用。
     * <p>对于创建单例对象的实现，此方法应尽可能避免单例的创建；而应尽量提前估算类型。对于原型 bean，在此处返回有意义的类型也是可取的。
     * <p>此方法可能在 FactoryBean 完全初始化之前被调用。它不能依赖于初始化期间创建的状态；当然，如果状态可用，仍然可以使用。
     * <p>注意： 自动装配会直接忽略在此处返回 null 的 FactoryBean。因此，强烈建议根据 FactoryBean 的当前状态正确实现此方法。
     * @return 此 FactoryBean 所创建对象的类型，如果在调用时未知则返回 null。
     */
    Class<?> getObjectType();

    /**
     * 此工厂管理的对象是否为单例？也就是说，getObject() 是否总是返回同一个对象（可缓存的引用）？
     * <p>注意： 如果 FactoryBean 表明其持有一个单例对象，那么从 getObject() 返回的对象可能会被所属的 BeanFactory 缓存。
     * 因此，除非 FactoryBean 始终暴露同一个引用，否则不要返回 true。
     * <p>FactoryBean 本身的单例状态通常由所属的 BeanFactory 提供；通常，它必须在其中被定义为单例。
     * <p>注意： 此方法返回 false 并不一定意味着返回的对象是独立的实例。扩展的 SmartFactoryBean 接口的实现可以通过其
     * SmartFactoryBean.isPrototype() 方法明确指示是否为独立实例。未实现此扩展接口的普通 FactoryBean 实现，如果 isSingleton() 返回 false，
     * 则简单地假定它们总是返回独立实例。
     * <p>默认实现返回 true，因为 FactoryBean 通常管理一个单例实例。
     * @return 暴露的对象是否为单例
     */
    boolean isSingleton();
}
