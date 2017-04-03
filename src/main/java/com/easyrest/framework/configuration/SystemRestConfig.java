package com.easyrest.framework.configuration;

import com.easyrest.framework.core.model.ModelFactory;
import com.easyrest.framework.core.model.file.FileUploadModel;
import com.easyrest.framework.core.model.image.VerificationCode;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.core.services.fileupload.FileUploadServiceImpl;
import com.easyrest.framework.core.services.verificationCode.VerificationCodeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;


/**
 * This is the rest enum and it contained mapping between url path and services.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@Configuration
public class SystemRestConfig {

    private final static Set<ModelFactory> REST_SET = new HashSet<>();

    @Autowired
    private FileUploadServiceImpl fileUploadService;

    @Autowired
    private VerificationCodeImpl verificationCode;

    public static void addModelService(Class model, RequestProcessService service){
        REST_SET.add(new ModelFactory(model, service));
    }

    private void init(){
        REST_SET.add(new ModelFactory(VerificationCode.class, verificationCode));
        REST_SET.add(new ModelFactory(FileUploadModel.class, fileUploadService));
    }

    public Set<ModelFactory> initAndGetMapping(){
        init();
        return REST_SET;
    }

}
