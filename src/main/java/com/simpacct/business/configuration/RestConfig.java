package com.simpacct.business.configuration;

import com.simpacct.framework.core.model.ModelFactory;
import com.simpacct.framework.core.model.file.FileUploadModel;
import com.simpacct.framework.core.model.image.VerificationCode;
import com.simpacct.framework.core.services.fileupload.FileUploadServiceImpl;
import com.simpacct.framework.core.services.verificationCode.VerificationCodeImpl;
import com.simpacct.business.model.request.HomeModel;
import com.simpacct.business.services.business.rest.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;


/**
 * This is the rest enum and it contained mapping between url path and services.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@Configuration
public class RestConfig {

    private final static Set<ModelFactory> REST_SET = new HashSet<>();

    @Autowired
    private HomeServiceImpl homeService;

    @Autowired
    private FileUploadServiceImpl fileUploadService;

    @Autowired
    private VerificationCodeImpl verificationCode;

    private void init(){
        REST_SET.add(new ModelFactory(VerificationCode.class, verificationCode));
        REST_SET.add(new ModelFactory(FileUploadModel.class, fileUploadService));
        REST_SET.add(new ModelFactory(HomeModel.class, homeService));
    }

    public Set<ModelFactory> initAndGetMapping(){
        init();
        return REST_SET;
    }

}
