package com.easyrest.framework.easyrest;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.configuration.SystemRestConfig;
import com.easyrest.framework.core.services.authentication.api.AuthenticateBindingService;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.core.services.exception.api.ExceptionBindingService;
import com.easyrest.framework.core.services.fileupload.api.FileUploadBindingService;
import com.easyrest.framework.core.services.history.api.HistoryBindingService;
import com.easyrest.framework.core.services.verificationCode.CaptchaBindingService;
import com.easyrest.framework.exception.ConditionMissingException;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
public class EasyRest {

    private static String systemName = "/EasyRest";

    private static String crossAllow = "*";

    private static List<String> rootPackageName = Lists.newArrayList("com.easyrest");

    private static boolean enabledAutoTransaction = false;

    private static AuthenticateBindingService authenticateBindingService;

    private static FileUploadBindingService fileUploadBindingService;

    private static HistoryBindingService historyBindingService;

    private static CaptchaBindingService captchaBindingService;

    private static ExceptionBindingService exceptionBindingService;

    public EasyRest setSystemName(String _systemName){
        systemName = _systemName;
        return this;
    }

    public EasyRest addRootPackageName(String _rootPackageName){
        if (!rootPackageName.contains(_rootPackageName)) {
            rootPackageName.add(_rootPackageName);
        }
        return this;
    }

    public EasyRest setEnabledAutoTransaction(boolean isEnabled){
        enabledAutoTransaction = isEnabled;
        return this;
    }

    public EasyRest setCrossAllow(String host){
        crossAllow = host;
        return this;
    }

    public EasyRest addModelService(Class model, RequestProcessService service){
        SystemRestConfig.addModelService(model, service);
        return this;
    }

    public EasyRest addInterfaceToSkipList(String url){
        RequestPath.addSkipURL(url);
        return this;
    }

    public EasyRest bindAuthenticateService(AuthenticateBindingService _authenticateBindingService){
        authenticateBindingService = _authenticateBindingService;
        return this;
    }

    public EasyRest bindFileUploadService(String uploadPath, FileUploadBindingService _fileUploadBindingService){
        GlobalParameters.setUploadFileDir(uploadPath);
        fileUploadBindingService = _fileUploadBindingService;
        return this;
    }

    public EasyRest bindHistoryService(HistoryBindingService _historyBindingService){
        historyBindingService = _historyBindingService;
        return this;
    }

    public EasyRest bindCaptchaService(CaptchaBindingService _captchaBindingService){
        captchaBindingService = _captchaBindingService;
        return this;
    }

    public EasyRest bindExceptionService(ExceptionBindingService _exceptionBindingService){
        exceptionBindingService = _exceptionBindingService;
        return this;
    }

    public static AuthenticateBindingService getAuthenticateBindingService(){
        if (authenticateBindingService == null){
            throw new ConditionMissingException("AuthenticateBindingService can not be null.");
        }
        return authenticateBindingService;
    }

    public static FileUploadBindingService getFileUploadBindingService(){
        if (fileUploadBindingService == null){
            throw new ConditionMissingException("FileUploadBindingService can not be null.");
        }
        return fileUploadBindingService;
    }

    public static HistoryBindingService getHistoryBindingService(){
        if (historyBindingService == null){
            throw new ConditionMissingException("HistoryBindingService can not be null.");
        }
        return historyBindingService;
    }

    public static CaptchaBindingService getCaptchaBindingService(){
        if (captchaBindingService == null){
            throw new ConditionMissingException("CaptchaBindingService can not be null.");
        }
        return captchaBindingService;
    }

    public static ExceptionBindingService getExceptionBindingService(){
        return exceptionBindingService;
    }

    public static String getSystemName(){
        return systemName;
    }

    public static List<String> getRootPackageName() {
        return rootPackageName;
    }

    public static boolean getEnabledAutoTransaction(){
        return enabledAutoTransaction;
    }

    public static String getCrossAllow(){
        return crossAllow;
    }

}
