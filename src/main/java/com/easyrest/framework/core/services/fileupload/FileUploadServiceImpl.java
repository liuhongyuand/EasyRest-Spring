package com.easyrest.framework.core.services.fileupload;

import com.easyrest.framework.core.model.file.FileUpload;
import com.easyrest.framework.core.model.file.FileUploadModel;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.core.services.fileupload.util.FileSupport;
import com.easyrest.framework.easyrest.EasyRest;
import org.springframework.stereotype.Service;

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
        FileUploadModel fileUpload = (FileUploadModel) httpEntity.getRequestModel();
        List<FileUpload> fileUploadList = new LinkedList<>();
        fileUpload.getFiles().forEach((file) -> {
            FileUpload fileUploadObj = new FileUpload();
            fileUploadObj.setFileName(file.getRealName());
            fileUploadObj.setUploadTime(new Date());
            fileUploadObj.setMd5(FileSupport.getMd5ForFile(file.getFile()));
            fileUploadObj.setPath(file.getFile().getPath());
            fileUploadList.add(fileUploadObj);
        });
        return EasyRest.getFileUploadBindingService().customizeServiceImpl(fileUploadList);
    }

}
