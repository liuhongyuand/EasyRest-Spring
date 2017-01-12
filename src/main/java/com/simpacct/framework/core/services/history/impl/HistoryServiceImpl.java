package com.simpacct.framework.core.services.history.impl;

import com.simpacct.framework.core.services.history.api.HistoryService;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public class HistoryServiceImpl implements HistoryService {

    private static final String DEFAULT_USER = "Not a special user";


    @Override
    public void record(String message) {
        record(DEFAULT_USER, message);
    }

    @Override
    public void record(String user, String message) {
    }

}
