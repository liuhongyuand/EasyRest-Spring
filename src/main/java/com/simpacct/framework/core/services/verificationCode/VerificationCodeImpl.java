package com.simpacct.framework.core.services.verificationCode;

import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.model.image.VerificationCode;
import com.simpacct.framework.core.model.image.util.ImageSupport;
import com.simpacct.framework.core.services.business.api.RequestProcessService;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2017/1/2.
 */
@Service
public class VerificationCodeImpl implements RequestProcessService {

    @Override
    public Object doProcess(HttpEntity httpEntity) {
        VerificationCode model = (VerificationCode) httpEntity.getRequestModel();
        return ImageSupport.createVerifyCode(httpEntity.getRequest());
    }

}
