package com.simpacct.framework.core.services.fileupload;

import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.model.file.FileUploadModel;
import com.simpacct.framework.core.model.response.ResponseEntity;
import com.simpacct.framework.core.services.business.api.RequestProcessService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongyu.louie on 2016/12/30.
 */
@Service
public class FileUploadServiceImpl implements RequestProcessService {

    @Override
    public Object doProcess(HttpEntity httpEntity) {
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>();
        FileUploadModel fileUpload = (FileUploadModel) httpEntity.getRequestModel();
        List<String> uploadFileNames = new ArrayList<>();
        responseEntity.setData(uploadFileNames);
        responseEntity.setSuccess();
        return responseEntity;
    }

}
