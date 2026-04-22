package com.yang.springframework.context.event;

import com.yang.springframework.beans.factory.BeanFactory;
import com.yang.springframework.context.ApplicationEvent;
import com.yang.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
