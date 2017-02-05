package com.easyrest.framework.core.services.verificationCode;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.image.VerificationCode;
import com.easyrest.framework.core.model.image.util.ImageSupport;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2017/1/2.
 */
@Service
public class VerificationCodeImpl implements RequestProcessService {

    @Override
    public Object doProcess(HttpEntity httpEntity) {
        VerificationCode model = (VerificationCode) httpEntity.getRequestModel();
        return ImageSupport.createVerifyCode(httpEntity);
    }

}
