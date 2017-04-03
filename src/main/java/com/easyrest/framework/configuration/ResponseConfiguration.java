package com.easyrest.framework.configuration;

import com.easyrest.framework.easyrest.EasyRest;
import org.springframework.http.MediaType;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by liuhongyu.louie on 2016/10/3.
 */
public class ResponseConfiguration {
    public static final String JSON = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";
    public static final String IMAGE = MediaType.IMAGE_PNG_VALUE;

    public enum Header{
        AccessControlAllowOrigin(new AbstractMap.SimpleEntry<>("Access-Control-Allow-Origin", EasyRest.getCrossAllow())),
        AccessControlAllowMethods(new AbstractMap.SimpleEntry<>("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")),
        AccessControlMaxAge(new AbstractMap.SimpleEntry<>("Access-Control-Max-Age", "13600")),
        AccessControlAllowHeaders(new AbstractMap.SimpleEntry<>("Access-Control-Allow-Headers", "x-requested-with")),
        AccessControlAllowCredentials(new AbstractMap.SimpleEntry<>("Access-Control-Allow-Credentials", "true"));

        private Map.Entry<String, String> headers;

        Header(Map.Entry<String, String> headers){
            this.headers = headers;
        }

        public Map.Entry<String, String> getEntry(){
            return this.headers;
        }
    }

}
