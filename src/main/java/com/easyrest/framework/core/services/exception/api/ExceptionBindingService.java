package com.easyrest.framework.core.services.exception.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuhongyu.louie on 2017/4/3.
 */
public interface ExceptionBindingService {

    void exception(HttpServletRequest request, HttpServletResponse response, Exception e);

}
