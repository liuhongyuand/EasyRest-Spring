package com.easyrest.framework.core.annotations.session;

import com.easyrest.framework.core.annotations.transaction.TransactionRequired;
import com.easyrest.framework.core.services.session.SessionParameters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuhongyu.louie on 2017/1/19.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@TransactionRequired
public @interface SessionRequired {
    SessionParameters[] sessionKey() default {};
    String[] sessionRecordFieldName() default {};
}
