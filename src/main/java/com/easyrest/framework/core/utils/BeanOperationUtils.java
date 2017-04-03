package com.easyrest.framework.core.utils;

import com.easyrest.framework.core.controllers.RequestServiceEntranceController;
import com.easyrest.framework.core.services.workStep.WorkStaticProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by liuhongyu.louie on 2017/4/3.
 */
@Component
public class BeanOperationUtils implements ApplicationContextAware {

    private static BeanDefinition bean;

    private static DefaultListableBeanFactory defaultListableBeanFactory;

    public static <T>T registerBean(String beanName, Class<T> aClass){
        bean.setBeanClassName(aClass.getName());
        defaultListableBeanFactory.registerBeanDefinition(beanName, bean);
        return getBean(beanName, aClass);
    }

    public static <T>T getBean(String beanName, Class<T> beanClass){
        return defaultListableBeanFactory.getBean(beanName, beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        bean = new GenericBeanDefinition();
        defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        RequestServiceEntranceController.setBeforeServiceSteps(WorkStaticProvider.getBeforeServiceSteps());
        RequestServiceEntranceController.setAfterServiceSteps(WorkStaticProvider.getAfterServiceSteps());
    }
}
