package com.sandeep.config;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Properties;

@Slf4j
public class FrameworkConfig {
    private static volatile FrameworkConfig instance = new FrameworkConfig();
    private Properties frameworkProperties;

    private FrameworkConfig() {
        frameworkProperties = new PropertyReader(new File(String
                .format("%s/src/main/resources/frameworkConfig.properties", System.getProperty("user.dir"))))
                .getPropertyFile();
    }

    public static FrameworkConfig getInstance() {
        return instance;
    }

    public Properties getConfigProperties() {
        return frameworkProperties;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(String.format("Cloning not allowed for %s class", FrameworkConfig.class));
    }
}
