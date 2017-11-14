package com.hunter.dtu.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomizedPropertyUtil {
    public static Map<String, Object> ctxPropertiesMap = new ConcurrentHashMap<String, Object>();

    public static String getContextProperty(String name) {
        return (String) ctxPropertiesMap.get(name);
    }
}
