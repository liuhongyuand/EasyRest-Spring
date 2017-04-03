package com.easyrest.framework.core.services.fileupload.api;

import com.easyrest.framework.core.model.file.FileUpload;
import com.easyrest.framework.core.model.response.ResponseEntity;

import java.util.List;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
public interface FileUploadBindingService {

    ResponseEntity<?> customizeServiceImpl(List<FileUpload> fileUploadList);

}
