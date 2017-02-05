package com.easyrest.framework.core.services.fileupload.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public class FileSupport {

    public static String getMd5ForFile(String file) {
        File myFile = new File(file);
        return getMd5ForFile(myFile);
    }

    public static String getMd5ForFile(File file) {
        String value = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            value = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
        } catch (IOException ignored) {} finally {
            IOUtils.closeQuietly(inputStream);
        }
        return value;
    }



}
