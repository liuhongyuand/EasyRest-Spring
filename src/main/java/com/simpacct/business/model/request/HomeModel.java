package com.simpacct.business.model.request;

import com.simpacct.framework.core.annotations.history.HistoryRequired;
import com.simpacct.framework.core.annotations.method.Get;
import com.simpacct.framework.core.annotations.method.Post;
import com.simpacct.framework.core.annotations.parameter.NotNull;
import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.exception.ConditionMissingException;
import com.simpacct.business.configuration.RequestPath;
import com.simpacct.framework.core.model.request.AbstractRequestModel;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
@Post({RequestPath.HOME})
@Get({RequestPath.HOME})
@HistoryRequired
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
