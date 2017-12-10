package com.easyrest.example.services.business.rest;

import com.easyrest.framework.core.model.file.FileUpload;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.services.fileupload.api.FileUploadBindingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
@Service
public class FileUploadBindingServiceImpl implements FileUploadBindingService {

    @Override
    public ResponseEntity<?> customizeServiceImpl(List<FileUpload> fileUploadList) {
        return ResponseEntity.buildOkResponse(fileUploadList);
    }
}
