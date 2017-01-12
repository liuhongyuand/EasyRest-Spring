package com.simpacct.business.configuration;

import java.util.LinkedList;

import static com.simpacct.business.configuration.RequestPath.System.*;

/**
 * Created by liuhongyu.louie on 2016/9/28.
 */
public class RequestPath1234 {

    private static final SkipList<String> SKIP_LIST = new SkipList<>();

    public class System {
        public static final String SystemName = "/DataInterface";
        public static final String REST = "/rest";
        public static final String REQUEST_ENTRANCE = "/*";
        public static final String UPLOAD = "/upload";
        public static final String IMAGE = "/image";
        public static final String VERIFICATION_CODE = "/verification_code";
    }

    /**
     *  For business request rest url
     */
    public static final String HOME = "/home";

    public RequestPath(){
        SKIP_LIST.addToList(REST)
                  .addToList(REQUEST_ENTRANCE);
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
