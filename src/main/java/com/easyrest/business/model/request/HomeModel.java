package com.easyrest.business.model.request;

import com.easyrest.business.services.business.rest.HomeServiceImpl;
import com.easyrest.framework.core.annotations.async.AsyncRequest;
import com.easyrest.framework.core.annotations.bean.BindService;
import com.easyrest.framework.core.annotations.method.Get;
import com.easyrest.framework.core.annotations.method.Post;
import com.easyrest.framework.core.annotations.parameter.AllDefined;
import com.easyrest.framework.core.annotations.parameter.Optional;
import com.easyrest.framework.core.model.request.AbstractRequestModel;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.exception.ConditionMissingException;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
@Post({"/home"})
@Get({"/home"})
//@TransactionRequired
//@HistoryRequired({"message"})
@AsyncRequest
@AllDefined
@BindService(HomeServiceImpl.class)
public class HomeModel extends AbstractRequestModel {

    private @Optional String code;
    private String url;
    private String message;

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException {
        if (url.equals("123")) throw new ConditionMissingException("url can not be 123");
    }
}
