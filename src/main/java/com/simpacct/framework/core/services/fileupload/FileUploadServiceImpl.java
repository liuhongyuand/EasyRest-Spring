package com.simpacct.framework.core.services.fileupload;

import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.model.file.FileUploadModel;
import com.simpacct.framework.core.model.pojo.FileUpload;
import com.simpacct.framework.core.model.response.ResponseEntity;
import com.simpacct.framework.core.services.business.api.RequestProcessService;
import com.simpacct.framework.core.services.fileupload.util.FileSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
        List<FileUpload> fileUploadList = new LinkedList<>();
        fileUpload.getFiles().forEach((file) -> {
            uploadFileNames.add(file.getRealName());
            FileUpload fileUploadObj = new FileUpload();
            fileUploadObj.setFileName(file.getRealName());
            fileUploadObj.setIsDelete(false);
            fileUploadObj.setUoloadTime(new Date());
            fileUploadObj.setMd5(FileSupport.getMd5ForFile(file.getFile()));
            fileUploadObj.setPath(file.getFile().getPath());
            fileUploadList.add(fileUploadObj);
        });
        responseEntity.setData(uploadFileNames);
        responseEntity.setSuccess();
        return responseEntity;
    }

}
