package com.easyrest.framework.configuration;

import com.easyrest.framework.easyrest.EasyRest;

import java.util.LinkedList;

/**
 * Created by liuhongyu.louie on 2016/9/28.
 */
public class RequestPath {

    private static final SkipList<String> SKIP_LIST = new SkipList<>();

    public static class System {
        public static final String SystemName = "/" + EasyRest.getSystemName();
        public static final String REST = "/rest";
        public static final String REQUEST_ENTRANCE = "/*";
        public static final String UPLOAD = "/upload";
        public static final String IMAGE = "/image";
        public static final String VERIFICATION_CODE = "/verification_code";
    }

    public RequestPath(){
        SKIP_LIST.addToList(System.REST)
                  .addToList(System.REQUEST_ENTRANCE);
    }

    public static void addSkipURL(String url){
        SKIP_LIST.addToList(url);
    }

    public static boolean isOpenInterface(String path){
        return !SKIP_LIST.contains(path);
    }

    private static class SkipList<T> extends LinkedList<T>{

        SkipList<T> addToList(T t) {
            super.add(t);
            return this;
        }
    }

}
