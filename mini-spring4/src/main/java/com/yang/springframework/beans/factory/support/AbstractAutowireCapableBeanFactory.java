package com.yang.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.PropertyValue;
import com.yang.springframework.beans.PropertyValues;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * <p>抽象bean工厂超类，实现了默认的bean创建机制，并具备RootBeanDefinition类所指定的完整能力。
 * 除了继承AbstractBeanFactory的createBean方法外，还实现了AutowireCapableBeanFactory接口。
 *
 * <p>提供bean创建（包含构造函数解析）、属性填充、装配（包括自动装配）以及初始化功能。
 * 能够处理运行时bean引用、解析托管集合、调用初始化方法等。
 * 支持按构造函数自动装配、按名称属性自动装配以及按类型属性自动装配。
 *
 * <p>子类需要实现的主要模板方法是resolveDependency(DependencyDescriptor, String, Set, TypeConverter)，
 * 用于自动装配。对于能够搜索其bean定义的org.springframework.beans.factory.ListableBeanFactory，
 * 通常通过此类搜索实现bean匹配；否则，可以简化匹配逻辑的实现。
 *
 * <p>请注意，此类并不假定或实现bean定义注册表功能。
 * 有关org.springframework.beans.factory.ListableBeanFactory和BeanDefinitionRegistry接口的实现，
 * 请参考DefaultListableBeanFactory——它们分别代表了此类工厂的API视图和SPI视图。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    /** 创建bean实例的策略。*/
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 为指定的bean创建一个新实例，使用适当的实例化策略：工厂方法、构造函数自动装配或简单实例化。
     * @param beanDefinition bean的bean定义
     * @param beanName bean的名称
     * @param args 用于构造函数或工厂方法调用的显式参数
     * @return 新实例的Bean
     * @throws BeansException
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeansException {
        Constructor constructorToUse = null;
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (args != null && constructor.getParameterTypes().length == args.length) {
                constructorToUse = constructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * 应用给定的属性值，解析此bean工厂中指向其他bean的所有运行时引用。必须使用深拷贝，以免永久修改此属性。
     * @param beanName 传递的bean名称，用于提供更好的异常信息
     * @param bean bean对象
     * @param beanDefinition 合并的bean定义
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
