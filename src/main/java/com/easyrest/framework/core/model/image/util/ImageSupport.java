package com.easyrest.framework.core.model.image.util;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.session.SessionParameters;
import com.easyrest.framework.core.services.session.SessionSupport;
import com.easyrest.framework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by liuhongyu.louie on 2017/1/2.
 */
public class ImageSupport {

    private static byte[] imageBytes = new byte[0];
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageSupport.class);

    static {
        BufferedImage bufferedImage = new BufferedImage(100, 20, BufferedImage.TYPE_INT_RGB);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Graphics graphics = bufferedImage.createGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageBytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static byte[] getWhiteImage(){
        return imageBytes;
    }

    public static byte[] createVerifyCode(HttpEntity httpEntity){
        Cage cage = new GCage();
        String token = StringUtils.getRandomCaptcha();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cage.draw(token, byteArrayOutputStream);
            if (SessionSupport.put(httpEntity, SessionParameters.CAPTCHA.getParameter(), token)) {
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return getWhiteImage();
    }

}
