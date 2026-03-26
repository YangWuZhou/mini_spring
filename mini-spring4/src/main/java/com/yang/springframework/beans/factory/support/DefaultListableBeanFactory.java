package com.yang.springframework.beans.factory.support;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Spring对ConfigurableListableBeanFactory和BeanDefinitionRegistry接口的默认实现：
 * 一个基于bean定义元数据的完整bean工厂，可通过后置处理器进行扩展。
 *
 * <p>典型用法是在访问bean之前先注册所有bean定义（可能从bean定义文件中读取）。
 * 因此，在本地bean定义表中按名称查找bean是一种开销较低的操作，因为它操作的是预先解析的bean定义元数据对象。
 *
 * <p>请注意，特定bean定义格式的读取器通常单独实现，而不是作为bean工厂的子类：
 * 例如可参考org.springframework.beans.factory.xml.XmlBeanDefinitionReader。
 *
 * <p>对于org.springframework.beans.factory.ListableBeanFactory接口的另一种实现，
 * 可查看StaticListableBeanFactory，它管理现有的bean实例而不是根据bean定义创建新实例。
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    /** bean定义对象的映射，以bean名称为键。*/
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
