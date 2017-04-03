package com.easyrest.business;

import com.easyrest.business.model.request.HomeModel;
import com.easyrest.business.services.business.rest.HomeServiceImpl;
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
    private HomeServiceImpl homeService;

    @Autowired
    private FileUploadBindingService fileUploadBindingService;

    @Override
    public void init(){
        EASY_REST.setEnabledAutoTransaction(false);
        EASY_REST.addModelService(HomeModel.class, homeService);
        EASY_REST.bindFileUploadService("C:/Users/liuhongyu.louie/Desktop/", fileUploadBindingService);
    }

}
