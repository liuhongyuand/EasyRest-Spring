package com.easyrest.business;

import com.easyrest.framework.core.services.fileupload.api.FileUploadBindingService;
import com.easyrest.framework.easyrest.EasyRest;
import com.easyrest.framework.easyrest.SystemStartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
@Controller
public class Startup implements SystemStartupService {

    private static final EasyRest EASY_REST = new EasyRest();

    @Autowired
    private FileUploadBindingService fileUploadBindingService;

    @Override
    public void init(){
        EASY_REST
                .setCrossAllow("*")
                .setSystemName("MyRestServer")
                .setEnabledAutoTransaction(false)
                .bindFileUploadService("C:/Users/liuhongyu.louie/Desktop/", fileUploadBindingService);
    }

}
