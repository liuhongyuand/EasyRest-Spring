package com.easyrest.framework.core.services.history.api;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public interface HistoryService {

    void record(String message);

    void record(String user, String message);

}
