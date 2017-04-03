package com.easyrest.framework.core.services.mail.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yu on 9/24/16.
 */
public class Model {
    private final Map<String, Object> root = new HashMap<>();

    public void add(final String key, final Object value) {
        root.put(key, value);

    }

    public Map<String, Object> getRoot() {
        return root;
    }
}
