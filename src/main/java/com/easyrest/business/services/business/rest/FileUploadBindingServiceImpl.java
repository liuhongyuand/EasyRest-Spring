package com.easyrest.business.services.business.rest;

import com.easyrest.framework.core.model.FileUpload;
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
        ResponseEntity<List<FileUpload>> responseEntity = new ResponseEntity<>();
        responseEntity.setData(fileUploadList);
        return responseEntity;
    }
}
