package com.easyrest.framework.core.model.file;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.core.annotations.method.Post;
import com.easyrest.framework.core.annotations.parameter.SkipInject;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.request.AbstractRequestModel;
import com.easyrest.framework.core.utils.LogUtils;
import com.easyrest.framework.core.utils.StringUtils;
import com.easyrest.framework.easyrest.EasyRest;
import com.easyrest.framework.exception.ConditionMissingException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhongyu.louie on 2016/12/30.
 */
@Post({RequestPath.System.UPLOAD})
@SkipInject
public class FileUploadModel extends AbstractRequestModel {

    private List<FileUploaded> fileList = new ArrayList<>();

    private Map<String, String> parameters = new HashMap<>();

    private static final String UPLOAD_MESSAGE = "%s is uploaded success, locate at %s and renamed %s";

    public List<FileUploaded> getFiles() {
        return fileList;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException {
        try {
            EasyRest.getFileUploadBindingService();
            parse(httpEntity.getRequest());
        } catch (FileUploadException ignored) {}
    }

    private void parse(HttpServletRequest request) throws FileUploadException {
        boolean multipart = FileUpload.isMultipartContent(request);
        if (multipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(20 * 1024 * 1024);
            List items = upload.parseRequest(request);
            for (Object item1 : items) {
                FileItem item = (FileItem) item1;
                if (item.isFormField()) {
                    try {
                        parameters.put(item.getFieldName(), item.getString(GlobalParameters.getCharacterEncoding()));
                    } catch (UnsupportedEncodingException ignored) {}
                } else {
                    try {
                        FileUploaded uploaded = new FileUploaded();
                        uploaded.setRealName(item.getName());
                        File file = new File((GlobalParameters.getUploadFileDir().endsWith("/") ? GlobalParameters.getUploadFileDir() : (GlobalParameters.getUploadFileDir() + "/")) + uploaded.getNewName());
                        FileUtils.writeByteArrayToFile(file, item.get());
                        uploaded.setFile(file);
                        LogUtils.info(String.format(UPLOAD_MESSAGE, uploaded.getRealName(), uploaded.getFile().getAbsolutePath(), uploaded.getNewName()), this.getClass());
                        if (uploaded.getFile().length() != 0) {
                            fileList.add(uploaded);
                        }
                    } catch (IOException ignored) {}
                }
            }
        }
    }

    public class FileUploaded{

        private String newName;

        private String realName;

        private File file;

        public String getNewName() {
            return newName;
        }

        public void setNewName(String newName) {
            this.newName = newName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
            setNewName(StringUtils.getTimeWithUUID() + "_" + realName);
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }

}
