package com.easyrest.framework.core.services.mail.impl;

import com.easyrest.framework.core.services.mail.api.MailService;
import com.easyrest.framework.core.utils.LogUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
@Service
public class MailServiceImpl implements MailService {

    private static final String USERNAME = "mailservice@simpacct.com";
    private static final String PASSWORD = "12345Abcd";
    private static final int SMTP_PORT = 465;
    private static final boolean USE_SSL = true;
    private static final String SEND_SERVER = "smtp.mxhichina.com";
    private static final String CHAR_SET = "UTF8";

    @PostConstruct
    private void initService() {
        try {
            Configurations.init();
        } catch (IOException e) {
            LogUtils.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendMail(String emailAddress, String subject, String username, String url) {
        try {
            final ImageHtmlEmail email = new ImageHtmlEmail();
            email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
            email.setSmtpPort(SMTP_PORT);
            email.setSSLOnConnect(USE_SSL);
            email.setHostName(SEND_SERVER);
            email.setCharset(CHAR_SET);
            email.addTo(emailAddress);
            email.setSubject(subject);
            email.setFrom(USERNAME);
            Model model = new Model();
            model.add("username", username);
            model.add("img", "https://service.dbgsoftware.tech/Simpacct/Bitmap.png");
            model.add("url", url);
            email.setHtmlMsg(Configurations.render("email.html", model));
            email.send();
        } catch (EmailException e) {
            LogUtils.error(e.getMessage(), e);
        }

    }
}
