package com.easyrest.framework.core.services.workStep.after.transaction.impl;

import com.easyrest.framework.core.annotations.session.SessionRequired;
import com.easyrest.framework.core.model.TransactionContext;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.model.response.ResponseObj;
import com.easyrest.framework.core.model.response.ResponseStatus;
import com.easyrest.framework.core.services.session.SessionSupport;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.after.transaction.api.TransactionCommitStep;

/**
 * Created by liuhongyu.louie on 2017/1/12.
 */
public class TransactionCommitStepImpl extends AbstractWorkStep implements TransactionCommitStep{

    @Override
    public <T> T executeStep(T t) {
        ResponseObj responseObj = (ResponseObj) t;
        TransactionContext transactionContext = responseObj.getHttpEntity().getTransactionContext();
        if (transactionContext.isTransactionStart() && !transactionContext.getTransactionStatus().isCompleted()){
            transactionContext.getTransactionCommit().apply(transactionContext.getTransactionStatus());
            if (((ResponseObj) t).getResponseObject() instanceof ResponseEntity) {
                ResponseEntity responseEntity = (ResponseEntity) ((ResponseObj) t).getResponseObject();
                if (ResponseStatus.SUCCESS.getCode().equals(responseEntity.getCode()) && responseObj.getHttpEntity().getRequestModel().getClass().isAnnotationPresent(SessionRequired.class)) {
                    SessionRequired sessionRequired = responseObj.getHttpEntity().getRequestModel().getClass().getAnnotation(SessionRequired.class);
                    if (sessionRequired.sessionKey().length > 0) {
                        for (int i = 0; i < sessionRequired.sessionKey().length; i++) {
                            String value = responseObj.getHttpEntity().getRequestModel().getFieldValue(sessionRequired.sessionRecordFieldName()[i]);
                            if (value != null) {
                                SessionSupport.put(responseObj.getHttpEntity(), sessionRequired.sessionKey()[i], value);
                            }
                        }
                    }
                }
            }
        }
        return t;
    }

}
