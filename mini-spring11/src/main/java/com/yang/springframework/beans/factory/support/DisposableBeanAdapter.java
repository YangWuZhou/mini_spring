package com.yang.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.DisposableBean;
import com.yang.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 实现了DisposableBean和Runnable接口的适配器，用于对给定的bean实例执行各种销毁步骤：
 * <ul>
 *     <li>销毁感知的Bean后处理器（DestructionAwareBeanPostProcessors）；
 *     <li>bean自身实现DisposableBean接口；
 *     <li>bean定义中指定的自定义销毁方法。
 * </ul>
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
            Method destroyMethod;
            try {
                destroyMethod = bean.getClass().getMethod(destroyMethodName);
            } catch (Exception e) {
                throw new BeansException("Could not find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }

            destroyMethod.invoke(bean);
        }
    }
}
