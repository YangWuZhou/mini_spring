package com.yang.springframework.context;

import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
