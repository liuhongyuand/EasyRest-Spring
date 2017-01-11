package com.simpacct.framework.core.annotations.permission;

import com.simpacct.framework.core.model.PermissionRole;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/10/9.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRequired {
    PermissionRole.PermissionRequired[] value() default PermissionRole.PermissionRequired.Normal;
}
