package com.easyrest.framework.core.model;

import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.core.annotations.method.Delete;
import com.easyrest.framework.core.annotations.method.Get;
import com.easyrest.framework.core.annotations.method.Post;
import com.easyrest.framework.core.annotations.method.Put;
import com.easyrest.framework.core.model.image.VerificationCode;
import com.easyrest.framework.core.model.request.RequestModel;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.exception.ParameterException;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class ModelFactory {

    private Class model;
    private RequestModel requestModel;
    private RequestProcessService service;
    private Map<String, List<Class>> listMap = new HashMap<>();

    /**
     * For multi instance
     * @param model model
     * @param service service
     */
    public ModelFactory(Class model, RequestProcessService service){
        this.model = model;
        this.service = service;
        checkModel();
    }

    /**
     * For single instance.
     * @param requestModel model
     * @param service service
     */
    public ModelFactory(RequestModel requestModel, RequestProcessService service){
        this.requestModel = requestModel;
        this.service = service;
        checkModel();
    }

    public Map<String, List<Class>> getURLMapping() {
        Annotation[] annotations = model != null ? model.getAnnotations() : requestModel.getClass().getAnnotations();
        for(Annotation annotation : annotations){
            if (annotation.annotationType().equals(Get.class)){
                addToMap(((Get)annotation).value(), Get.class);
            }
            if (annotation.annotationType().equals(Post.class)){
                addToMap(((Post)annotation).value(), Post.class);
            }
            if (annotation.annotationType().equals(Put.class)){
                addToMap(((Put)annotation).value(), Put.class);
            }
            if (annotation.annotationType().equals(Delete.class)){
                addToMap(((Delete)annotation).value(), Delete.class);
            }
        }
        return listMap;
    }

    private void addToMap(String[] paths, Class aClass){
        for (String path : paths){
            if (model.equals(VerificationCode.class)){
                addToMap(RequestPath.System.IMAGE + path, aClass);
            } else {
                addToMap(RequestPath.System.REST + path, aClass);
            }
        }
    }

    private void addToMap(String path, Class aClass){
        if (listMap.containsKey(path)){
            if (!listMap.get(path).contains(aClass)){
                listMap.get(path).add(aClass);
            }
        } else {
            List<Class> annotationList = new LinkedList<>();
            annotationList.add(aClass);
            listMap.put(path, annotationList);
        }
    }

    public RequestModel createModel() {
        try {
            return (RequestModel) Class.forName(model.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {}
        return null;
    }

    public RequestProcessService getService() {
        return service;
    }

    private void checkModel(){
        if (model == null && requestModel == null){
            throw new ParameterException("Model can not be null.");
        }
    }

}
