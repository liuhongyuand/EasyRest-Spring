package com.easyrest.framework.core.annotations.parameter;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2017/1/15.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Optional {
}
