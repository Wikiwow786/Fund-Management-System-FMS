package com.fms.util;

import com.fms.listener.PropertyChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BeanUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass)
    {
        return applicationContext.getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass)
    {
        return applicationContext.getBean(beanName, beanClass);
    }

    public static Collection<PropertyChangeListener> getListeners()
    {
         return applicationContext.getBeansOfType(PropertyChangeListener.class).values();
    }


}