package com.easyrest.framework.core.model.image;

/**
 * Created by liuhongyu.louie on 2017/4/5.
 */
public class CaptchaCode {

    private String captcha;

    private byte[] bytes;

    public CaptchaCode(String captcha, byte[] bytes) {
        this.captcha = captcha;
        this.bytes = bytes;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
