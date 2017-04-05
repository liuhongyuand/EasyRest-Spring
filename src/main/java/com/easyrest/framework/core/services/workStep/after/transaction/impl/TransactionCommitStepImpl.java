package com.easyrest.framework.core.services.workStep.after.transaction.impl;

import com.easyrest.framework.core.model.TransactionContext;
import com.easyrest.framework.core.model.response.ResponseObj;
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
        }
        return t;
    }

}
