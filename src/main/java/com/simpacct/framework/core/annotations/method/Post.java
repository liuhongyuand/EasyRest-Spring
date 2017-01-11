package com.simpacct.framework.core.annotations.method;

import com.simpacct.business.configuration.RequestPath;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/10/23.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Post {
    String[] value() default RequestPath.System.REQUEST_ENTRANCE;
}
