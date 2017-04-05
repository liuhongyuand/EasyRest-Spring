package com.easyrest.framework.core.services.verificationCode;

import com.easyrest.framework.core.model.image.CaptchaCode;
import com.easyrest.framework.core.model.image.util.ImageSupport;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.easyrest.EasyRest;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2017/1/2.
 */
@Service
public class VerificationCodeImpl implements RequestProcessService {

    @Override
    public Object doProcess(HttpEntity httpEntity) {
        CaptchaCode captchaCode = ImageSupport.createVerifyCode();
        EasyRest.getCaptchaBindingService().captcha(captchaCode.getCaptcha());
        return captchaCode.getBytes();
    }

}
