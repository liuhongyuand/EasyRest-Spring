package com.easyrest.business.model.request;

import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.core.annotations.history.HistoryRequired;
import com.easyrest.framework.core.annotations.method.Get;
import com.easyrest.framework.core.annotations.method.Post;
import com.easyrest.framework.core.annotations.parameter.NotNull;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.exception.ConditionMissingException;
import com.easyrest.framework.core.model.request.AbstractRequestModel;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
@Post({RequestPath.HOME})
@Get({RequestPath.HOME})
public class HomeModel extends AbstractRequestModel {

    private @NotNull String code;
    private String url;
    private @NotNull String message;

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
