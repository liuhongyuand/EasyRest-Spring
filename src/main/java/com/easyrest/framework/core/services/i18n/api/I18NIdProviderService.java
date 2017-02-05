package com.easyrest.framework.core.services.i18n.api;

import com.easyrest.framework.core.services.i18n.common.LanguageEnum;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
public interface I18NIdProviderService {

    LanguageEnum getLanguageSettingById(String Id);

}
