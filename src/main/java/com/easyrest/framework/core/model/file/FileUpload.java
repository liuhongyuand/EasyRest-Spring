package com.easyrest.framework.core.model.file;

import java.util.Date;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
public class FileUpload {

    private String fileName;
    private long uploadTime;
    private String md5;
    private String path;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime.getTime();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
