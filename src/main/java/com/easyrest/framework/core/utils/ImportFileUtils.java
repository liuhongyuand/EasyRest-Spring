package com.easyrest.framework.core.utils;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.core.services.i18n.common.LanguageEnum;
import org.apache.ibatis.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utils for XML files
 * Created by liuhongyu.louie on 2016/7/19.
 */
@Configuration
public class ImportFileUtils {

    private boolean isLoaded = false;
    private static final Properties i18nLoader_en = new Properties();
    private static final Properties i18nLoader_zh = new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(ImportFileUtils.class);

    static {
        LogFactory.useLog4JLogging();
    }

    @PostConstruct
    private void initSystemPropertyFile() throws FileNotFoundException {
        try {
            if (!isLoaded) {
                isLoaded = true;
                i18nLoader_en.load(ImportFileUtils.class.getClassLoader().getResourceAsStream("i18n/message_tips_en.properties"));
                putMessageToMap(i18nLoader_en, LanguageEnum.ENGLISH);
                i18nLoader_zh.load(new InputStreamReader(ImportFileUtils.class.getClassLoader().getResourceAsStream("i18n/message_tips_zh.properties"), "UTF8"));
                putMessageToMap(i18nLoader_zh, LanguageEnum.CHINESE);
                LogUtils.info("conf.properties has load success.", this.getClass());
                initPermissionTable();
                LogUtils.info("permission table has init success.", this.getClass());
                initI18NTable();
                LogUtils.info("i18n table has init success.", this.getClass());
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void initPermissionTable() throws FileNotFoundException {

    }

    private void initI18NTable() throws FileNotFoundException {
    }

    private void putMessageToMap(Properties properties, LanguageEnum languageEnum){
        Map<String, String> keyValue = new HashMap<>();
        ((Map)properties).forEach((k, v) -> keyValue.put(k.toString(), v.toString()));
        GlobalParameters.i18nMap.put(languageEnum.getLanguageType(), keyValue);
    }
}
