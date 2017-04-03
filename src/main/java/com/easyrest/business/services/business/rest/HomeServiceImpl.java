package com.easyrest.business.services.business.rest;

import com.easyrest.framework.core.model.request.RequestModel;
import com.easyrest.business.model.request.HomeModel;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
@Service
public class HomeServiceImpl implements RequestProcessService {

    @Override
    public Object doProcess(HttpEntity httpEntity) {
        HomeModel model = (HomeModel) httpEntity.getRequestModel();
        ResponseEntity<RequestModel> requestModelResponseEntity = new ResponseEntity<>();
        requestModelResponseEntity.setData(model);
        return requestModelResponseEntity;
    }

}
