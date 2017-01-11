package com.simpacct.framework.core.model.response;


import com.simpacct.framework.core.utils.StringUtils;

/**
 * Created by SEELE on 2016/10/2.
 */
public class ResponseEntity<T>  {
    private String code = "1";
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public ResponseEntity<T> setFailed(){
        setFailed("Failed");
        return this;
    }

    public ResponseEntity<T> setFailed(String message){
        setFailed("-1", message);
        return this;
    }

    public void setFailed(String code, String message){
        setCode(code);
        setMessage(message);
    }

    public void setSuccess(){
        setSuccess("Success");
    }

    public void setSuccess(String message){
        setSuccess("1", message);
    }

    public void setSuccess(String code, String message){
        setCode(code);
        setMessage(message);
    }

    public void setData(T data) {
        this.data = data;
        if(StringUtils.isEmptyString(message)) {
            if (this.data == null || ((data.getClass().equals(String.class)) && StringUtils.isEmptyString(String.valueOf(data)))){
                setFailed("-1","The data is null.");
            } else {
                setSuccess();
            }
        }
    }

}
