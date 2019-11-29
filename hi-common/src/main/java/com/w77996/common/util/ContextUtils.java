package com.w77996.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w77996
 */
@Component
public class ContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;


    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getBeans(Class<T> beanType) {
        String[] beanNames = applicationContext.getBeanNamesForType(beanType);
        List<T> beans = new ArrayList<T>();
        for (String beanName : beanNames) {
            beans.add((T) applicationContext.getBean(beanName));
        }
        return beans;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ContextUtils.applicationContext = applicationContext;
    }

}