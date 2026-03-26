package com.yang.springframework.beans.factory;

import com.yang.springframework.beans.BeansException;

import java.util.Map;

/**
 * BeanFactory接口的扩展，由能够枚举其所有bean实例的bean工厂实现，
 * 而不是像客户端请求的那样逐个按名称尝试bean查找。
 * 预加载所有bean定义的BeanFactory实现（例如基于XML的工厂）可以实现此接口。
 * <p>如果这是一个HierarchicalBeanFactory，返回值将不考虑任何BeanFactory层次结构，
 * 而仅与当前工厂中定义的bean相关。可使用BeanFactoryUtils辅助类来同时考虑祖先工厂中的bean。
 * <p>此接口中的方法仅遵守此工厂的bean定义。它们将忽略通过其他方式
 * （如org.springframework.beans.factory.config.ConfigurableBeanFactory的registerSingleton方法）注册的任何单例bean，但getBeanNamesForType和getBeansOfType除外，这两个方法也会检查此类手动注册的单例。当然，BeanFactory的getBean也确实允许透明地访问此类特殊bean。然而，在典型场景中，所有bean通常都由外部bean定义，因此大多数应用程序无需担心这种区别。
 * <p>注意：除getBeanDefinitionCount和containsBeanDefinition外，此接口中的方法并非为频繁调用而设计。
 * 实现可能较慢。
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 返回匹配给定对象类型（包括子类）的bean实例，判断依据可以是bean定义，
     * 或者在FactoryBean的情况下依据getObjectType的值。
     * <p>注意： 此方法仅检查顶级bean。它不检查可能也匹配指定类型的嵌套bean。
     * <p>确实会考虑由FactoryBeans创建的对象，这意味着FactoryBeans将被初始化。
     * 如果FactoryBean创建的对象不匹配，则原始的FactoryBean本身将与类型进行匹配。
     * <p>不考虑此工厂可能参与的任何层次结构。可使用BeanFactoryUtils的beansOfTypeIncludingAncestors
     * 来同时包含祖先工厂中的bean。
     * <p>注意： 不会忽略通过bean定义之外的其他方式注册的单例bean。
     * <p>此版本的getBeansOfType匹配所有类型的bean，无论是单例、原型还是FactoryBeans。
     * 在大多数实现中，结果与getBeansOfType(type, true, true)相同。
     * <p>此方法返回的Map应尽可能按照后端配置中的定义顺序返回bean名称和相应的bean实例。
     * @param type 要匹配的类或接口，如果为null则匹配所有具体bean
     * @return 包含匹配bean的Map，以bean名称为键，相应的bean实例为值
     * @throws BeansException 如果无法创建bean
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回在此工厂中定义的所有bean的名称。
     * <p>不考虑此工厂可能参与的任何层次结构，并忽略通过bean定义之外的其他方式注册的任何单例bean。
     * @return 此工厂中定义的所有bean的名称，如果未定义任何bean，则返回空数组
     */
    String[] getBeanDefinitionNames();
}
