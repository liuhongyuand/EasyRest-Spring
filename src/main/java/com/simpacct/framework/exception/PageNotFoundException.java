package com.simpacct.framework.exception;

/**
 * Page not found exception.
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class PageNotFoundException extends NullPointerException {

    public static PageNotFoundException getException(){
        return new PageNotFoundException();
    }

}
