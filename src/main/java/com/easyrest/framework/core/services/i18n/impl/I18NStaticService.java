package com.easyrest.framework.core.services.i18n.impl;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.i18n.common.LanguageEnum;
import com.easyrest.framework.core.services.session.SessionParameters;
import com.easyrest.framework.core.services.session.SessionSupport;
import com.easyrest.framework.easyrest.EasyRest;
import org.springframework.stereotype.Component;

/**
 * Created by liuhongyu.louie on 2017/1/14.
 */
@Component
public class I18NStaticService {

    public static LanguageEnum setLanguage(String userId, LanguageEnum languageEnum) {
        return null;
    }

    public static LanguageEnum getLanguage(String userId) {
        if (userId == null){
            return LanguageEnum.CHINESE;
        }
        Integer i18nId  = EasyRest.getI18NProvider().getLanguageSettingById(userId).getLanguageId();
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.getLanguageId() == i18nId){
                return languageEnum;
            }
        }
        return LanguageEnum.CHINESE;
    }

    public static String getMessage(HttpEntity httpEntity, String messageKey) {
        String userId = String.valueOf(SessionSupport.get(httpEntity, SessionParameters.UserId.getParameter()));
        return getMessage(userId, messageKey);
    }

    public static String getMessage(String userId, String messageKey) {
        LanguageEnum languageEnum = getLanguage(userId);
        return GlobalParameters.i18nMap.get(languageEnum.getLanguageType()).get(messageKey);
    }

    public static String getMessage(String messageKey) {
        return GlobalParameters.i18nMap.get(LanguageEnum.DEFAULT.getLanguageType()).get(messageKey);
    }

}
