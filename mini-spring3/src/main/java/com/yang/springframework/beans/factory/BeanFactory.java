package com.yang.springframework.beans.factory;

import com.yang.springframework.beans.BeansException;

/**
 * 用于访问Spring bean容器的根接口。
 *
 * <p>这是bean容器的基本客户端视图；为满足特定需求，还提供了其他接口，
 * 例如ListableBeanFactory和org.springframework.beans.factory.config.ConfigurableBeanFactory。
 *
 * <p>该接口由包含多个bean定义的对象实现，每个bean定义通过唯一的字符串名称标识。
 * 根据bean定义的不同，工厂将返回被包含对象的独立实例（原型设计模式），
 * 或返回单个共享实例（优于单例设计模式的方案，在此模式下实例在工厂作用域内为单例）。
 * 具体返回哪种类型的实例取决于bean工厂的配置，而API是相同的。
 * 自Spring 2.0起，根据具体应用上下文（例如Web环境中的"request"和"session"作用域），还可使用更多作用域。
 *
 * <p>这种做法的核心在于，BeanFactory是应用组件的中央注册中心，
 * 并集中管理应用组件的配置（例如，各个对象不再需要单独读取属性文件）。
 * 有关此方法优势的讨论，请参阅《Expert One-on-One J2EE Design and Development》第4章和第11章。
 *
 * <p>请注意，通常更推荐通过依赖注入（“推送”配置）的方式，
 * 借助setter或构造函数来配置应用对象，而不是使用任何形式的“拉取”配置（例如BeanFactory查找）。
 * Spring的依赖注入功能正是通过此BeanFactory接口及其子接口实现的。
 *
 * <p>通常，BeanFactory会加载存储在配置源（如XML文档）中的bean定义，
 * 并使用org.springframework.beans包来配置bean。
 * 然而，实现类也可以直接在Java代码中按需创建并返回Java对象。
 * bean定义的存储方式没有限制：可以是LDAP、关系型数据库、XML、属性文件等。
 * 鼓励实现类支持bean之间的引用（依赖注入）。
 *
 * <p>与ListableBeanFactory中的方法不同，如果当前工厂是HierarchicalBeanFactory，
 * 则本接口中的所有操作也会检查父工厂。
 * 如果在此工厂实例中未找到bean，则会向直接父工厂查询。
 * 此工厂实例中的bean应覆盖任何父工厂中同名的bean。
 *
 * <p>Bean工厂实现应尽可能支持标准的bean生命周期接口。完整的初始化方法及其标准顺序如下：
 * <ol>
 * <li>BeanNameAware's {@code setBeanName}
 * <li>BeanClassLoaderAware's {@code setBeanClassLoader}
 * <li>BeanFactoryAware's {@code setBeanFactory}
 * <li>EnvironmentAware's {@code setEnvironment}
 * <li>EmbeddedValueResolverAware's {@code setEmbeddedValueResolver}
 * <li>ResourceLoaderAware's {@code setResourceLoader}
 * (仅适用于在应用上下文中运行的情况)
 * <li>ApplicationEventPublisherAware's {@code setApplicationEventPublisher}
 * (仅适用于在应用上下文中运行的情况)
 * <li>MessageSourceAware's {@code setMessageSource}
 * (仅适用于在应用上下文中运行的情况)
 * <li>ApplicationContextAware's {@code setApplicationContext}
 * (仅适用于在应用上下文中运行的情况)
 * <li>ServletContextAware's {@code setServletContext}
 * (o仅适用于在Web应用上下文中运行的情况)
 * <li>{@code postProcessBeforeInitialization} methods of BeanPostProcessors
 * <li>InitializingBean's {@code afterPropertiesSet}
 * <li>a custom {@code init-method} definition
 * <li>{@code postProcessAfterInitialization} methods of BeanPostProcessors
 * </ol>
 *
 * <p>在bean工厂关闭时，以下生命周期方法适用：
 * <ol>
 * <li>DestructionAwareBeanPostProcessors的postProcessBeforeDestruction方法
 * <li>DisposableBean的destroy
 * <li>自定义的destroy-method定义
 * </ol>
 */
public interface BeanFactory {

    /**
     * <p>返回指定bean的实例，该实例可能是共享的或独立的。
     *
     * <p>此方法允许将Spring BeanFactory用作单例或原型设计模式的替代方案。
     * 对于单例bean，调用者可以保留对返回对象的引用。
     *
     * <p>该方法会将别名解析为对应的规范bean名称。
     *
     * <p>如果在本工厂实例中找不到该bean，将会向父工厂查询。
     *
     * @param name 要获取的bean名称
     * @return bean的实例。注意返回值永远不会为null，但可能是工厂方法返回的空值存根（可通过equals(null)进行检查）。
     * 对于可选依赖项的解析，建议考虑使用getBeanProvider(Class)方法。
     *
     * @throws BeansException 如果无法获取该bean
     */
    Object getBean(String name) throws BeansException;

    /**
     * <p>返回指定bean的一个实例，该实例可能是共享的或独立的。
     * <p>允许指定显式的构造函数参数/工厂方法参数，覆盖bean定义中指定的默认参数（如果有）。
     * 请注意，所提供的参数需要按照声明的参数顺序与特定的候选构造函数/工厂方法匹配。
     * @param name 要获取的bean名称
     * @param args 使用显式参数创建bean实例时要使用的参数（仅在创建新实例而非获取现有实例时适用）
     * @return bean的实例。
     * @throws BeansException 如果无法获取该bean
     */
    Object getBean(String name, Object... args) throws BeansException;
}
