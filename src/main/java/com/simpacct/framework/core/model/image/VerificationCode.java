package com.simpacct.framework.core.model.image;

import com.simpacct.framework.core.annotations.method.Get;
import com.simpacct.framework.core.annotations.method.Post;
import com.simpacct.framework.core.model.request.AbstractRequestModel;
import com.simpacct.business.configuration.RequestPath;

/**
 * Created by liuhongyu.louie on 2017/1/2.
 */
@Get(RequestPath.System.VERIFICATION_CODE)
@Post(RequestPath.System.VERIFICATION_CODE)
public class VerificationCode extends AbstractRequestModel{

}
