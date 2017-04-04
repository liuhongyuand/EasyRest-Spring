package com.easyrest.framework.configuration;

import com.easyrest.framework.core.annotations.bean.BindService;
import com.easyrest.framework.core.model.ModelFactory;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.core.utils.BeanOperationUtils;
import com.easyrest.framework.easyrest.EasyRest;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * This is the rest enum and it contained mapping between url path and services.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@Configuration
public class SystemRestConfig {

    private final static Set<ModelFactory> REST_SET = new HashSet<>();

    public static void addModelService(Class model, RequestProcessService service){
        REST_SET.add(new ModelFactory(model, service));
    }

    private void injectService(){
        List<String> filter = new LinkedList<>();
        EasyRest.getRootPackageName().forEach((rootName) -> {
            List<ClassLoader> classLoadersList = new LinkedList<>();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(rootName))));
            Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
            classes.forEach((aClass -> {
                if (aClass.isAnnotationPresent(BindService.class) && !filter.contains(aClass.getName())){
                    filter.add(aClass.getName());
                    BindService service = aClass.getAnnotation(BindService.class);
                    REST_SET.add(new ModelFactory(aClass, (RequestProcessService) BeanOperationUtils.getBean(service.value())));
                }
            }));
        });
    }

    public Set<ModelFactory> initAndGetMapping(){
        injectService();
        return REST_SET;
    }

}
