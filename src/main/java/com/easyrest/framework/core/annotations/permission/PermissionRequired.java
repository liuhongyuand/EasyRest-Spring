package com.easyrest.framework.core.annotations.permission;

import com.easyrest.framework.core.services.authentication.common.PermissionRole;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/10/9.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRequired {
    PermissionRole.PermissionRequired[] role() default PermissionRole.PermissionRequired.GOD;
    String userIdFieldName() default "";
}
