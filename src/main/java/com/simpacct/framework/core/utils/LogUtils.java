package com.simpacct.framework.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liuhongyu.louie on 2016/10/19.
 */
public class LogUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static <T>T printGsonObject(final T t){
        try {
            return t;
        }finally {
            LOGGER.info(GSON.toJson(t));
        }
    }

    public static void debug(String string){
        LOGGER.debug(string);
    }

    public static void debug(Object obj){
        LOGGER.debug(GSON.toJson(obj));
    }

    public static void info(String string){
        LOGGER.info(string);
    }

    public static void info(Object obj){
        LOGGER.info(GSON.toJson(obj));
    }

}
