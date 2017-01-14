package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.core.model.TransactionContext;
import com.simpacct.framework.core.model.response.ResponseObj;
import com.simpacct.framework.core.services.workStep.api.WorkStep;

/**
 * Created by liuhongyu.louie on 2017/1/12.
 */
public class TransactionCommitStep implements WorkStep {

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
