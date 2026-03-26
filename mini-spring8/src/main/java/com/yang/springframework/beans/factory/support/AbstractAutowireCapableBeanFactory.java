package com.yang.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.PropertyValue;
import com.yang.springframework.beans.PropertyValues;
import com.yang.springframework.beans.factory.DisposableBean;
import com.yang.springframework.beans.factory.InitializingBean;
import com.yang.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.BeanPostProcessor;
import com.yang.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    /** 创建bean实例的策略。*/
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            applyPropertyValues(beanName, bean, beanDefinition);

            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 将给定的bean添加到此工厂的可销毁bean列表中，注册其DisposableBean接口和/或给定的销毁方法，以便在工厂关闭时调用（如果适用）。仅适用于单例。
     * @param beanName bean的名称
     * @param bean bean实例
     * @param beanDefinition bean的bean定义
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 为指定的bean创建一个新实例，使用适当的实例化策略：工厂方法、构造函数自动装配或简单实例化。
     * @param beanDefinition bean的bean定义
     * @param beanName bean的名称
     * @param args 用于构造函数或工厂方法调用的显式参数
     * @return 新实例的Bean
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
                if (value instanceof BeanReference beanReference) {
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    /**
     * 返回用于创建 bean 实例的实例化策略。
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    /**
     * 设置用于创建 bean 实例的实例化策略。默认为 CglibSubclassingInstantiationStrategy。
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 初始化给定的 bean 实例，应用工厂回调、初始化方法以及 bean 后处理器。
     * <p>对于传统定义的 bean，该方法由 createBean 调用；对于现有 bean 实例，则由 initializeBean 调用。
     * @param beanName 工厂中的 bean 名称（用于调试目的）
     * @param bean 需要初始化的新 bean 实例
     * @param beanDefinition 创建该 bean 所使用的 bean 定义（如果给定的是现有 bean 实例，则可为 null）
     * @return 初始化后的 bean 实例（可能被包装）
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null)
                return result;
            result = current;
        }
        return result;
    }

    /**
     * 让bean有机会在它的所有属性设置完成后进行初始化，并有机会了解它所属的bean工厂（即此对象）。
     * <p>这意味着检查该bean是否实现了InitializingBean接口，或是否定义了任何自定义的初始化方法，如果实现了，则调用相应的回调方法。
     * @param beanName 工厂中的bean名称（用于调试目的）
     * @param bean 可能需要初始化的新bean实例
     * @param beanDefinition 创建该bean时使用的合并后的bean定义（如果给定的是现有bean实例，则可以为null）
     * @throws Exception 如果初始化方法或调用过程抛出异常
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod;
            try {
                initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            } catch (Exception e) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }

            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null)
                return result;
            result = current;
        }
        return result;
    }
}
