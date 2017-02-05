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

    private EasyRest easyRest = new EasyRest();

    @Autowired
    private HomeServiceImpl homeService;

    @Autowired
    private FileUploadBindingService fileUploadBindingService;

    @Override
    public void init(){
        easyRest.addModelService(HomeModel.class, homeService);
        easyRest.bindFileUploadService("C:/Users/liuhongyu.louie/Desktop/", fileUploadBindingService);
    }

}
