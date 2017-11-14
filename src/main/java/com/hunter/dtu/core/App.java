/* 
 * Copyright (C), 2015-2017
 * File Name: @(#)App.java
 * Encoding UTF-8
 * Author: hunter@linmuxi.com
 * Version: 1.0
 * Date: 2017年11月13日
 */
package com.hunter.dtu.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述
 * 
 * <p>
 * <a href="App.java"><i>View Source</i></a>
 * 
 * @author hunter@linmuxi.com
 * @version 1.0
 * @since 1.0
 * @date 2017年11月13日 下午2:45:49
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * 函数的目的/功能
     * 
     * @param args
     */
    public static void main(String[] args) {
        initParam(args);
        MainFrame.run();
    }
    
    /**
     * 函数的目的/功能
     * @param args
    */
    private static void initParam(String[] args) {
        LOGGER.info("开始加载初始化参数{}" ,args);
        InputStream is = null;
        if (args != null && args.length > 0) {
            LOGGER.info("取自定义配置文件");
            try {
                is = new FileInputStream(new File(args[0]).getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            LOGGER.info("取默认配置文件");
            is = App.class.getClassLoader().getResourceAsStream("config.properties");
        }

        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = CustomizedPropertyUtil.ctxPropertiesMap;
        for (Object k : prop.keySet()) {
            Object v = prop.get(k);
            String key = String.valueOf(k), value = String.valueOf(v);
            map.put(key, value);
        }
        LOGGER.info("加载初始化参数完成,{}", CustomizedPropertyUtil.ctxPropertiesMap);
    }
}
