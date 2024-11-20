package com.fms.fund_management_system.util;

import com.fms.fund_management_system.listener.PropertyChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public BeanUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }

    public static Collection<com.fms.fund_management_system.listener.PropertyChangeListener> getListeners() {
        return applicationContext.getBeansOfType(PropertyChangeListener.class).values();
    }
}
