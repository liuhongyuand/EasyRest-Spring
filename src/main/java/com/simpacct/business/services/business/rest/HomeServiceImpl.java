package com.simpacct.business.services.business.rest;

import com.simpacct.framework.core.model.request.RequestModel;
import com.simpacct.business.model.request.HomeModel;
import com.simpacct.framework.core.model.response.ResponseEntity;
import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.services.business.api.RequestProcessService;
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
