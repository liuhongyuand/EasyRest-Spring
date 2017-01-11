package com.simpacct.framework.core.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Utils for XML files
 * Created by liuhongyu.louie on 2016/7/19.
 */
@Configuration
public class ImportFileUtils {

    private static final Properties propertiesParameters = new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(ImportFileUtils.class);

    @PostConstruct
    private void initSystemPropertyFile() throws FileNotFoundException {
        ThreadPoolResources.EXECUTOR_SERVICE.execute(() -> {
            try {
                propertiesParameters.load(ImportFileUtils.class.getClassLoader().getResourceAsStream("conf.properties"));
                LogUtils.info("conf.properties has load success.");
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        });
    }

    public static Properties getPropertiesParameters() {
        return propertiesParameters;
    }
}
