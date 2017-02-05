package com.easyrest.framework.core.services.mail.api;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public interface MailService {

    void sendMail(String emailAddress, String subject, String username, String url);

}
