package com.simpacct.framework.core.services.mail.impl;

import com.simpacct.framework.core.services.mail.api.MailService;
import com.simpacct.framework.core.services.mail.common.UserConfig;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public class MailServiceImpl implements MailService {

    private static final String USERNAME = "mailservice@simpacct.com";
    private static final String PASSWORD = "12345Abcd";
    private static final int SMTP_PORT = 465;
    private static final boolean USE_SSL = true;
    private static final String SEND_SERVER = "smtp.mxhichina.com";
    private static final String CHAR_SET = "UTF8";
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    static {
        try {
            Configurations.init();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        try {
            final ImageHtmlEmail email = new ImageHtmlEmail();
            email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
            email.setSmtpPort(SMTP_PORT);
            email.setSSLOnConnect(USE_SSL);
            email.setHostName(SEND_SERVER);
            email.setCharset(CHAR_SET);
            email.addTo("891543983@qq.com");
            email.setSubject("Test");
            email.setFrom(USERNAME);
            email.setHtmlMsg("测试测试测试测试啦啦啦啦啦啦啦 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
            Model model = new Model();
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

}
