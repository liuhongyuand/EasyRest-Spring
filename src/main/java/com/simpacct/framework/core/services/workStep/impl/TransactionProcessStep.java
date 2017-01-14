package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.core.annotations.history.HistoryRequired;
import com.simpacct.framework.core.annotations.transaction.TransactionRequired;
import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.model.TransactionContext;
import com.simpacct.framework.core.services.workStep.api.WorkStep;
import com.simpacct.framework.core.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Function;

/**
 * Created by liuhongyu.louie on 2017/1/12.
 */
public class TransactionProcessStep implements WorkStep {

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public <T> T executeStep(T t) {
        HttpEntity httpEntity = (HttpEntity) t;
        if (httpEntity.getRequestModel().getClass().isAnnotationPresent(HistoryRequired.class) || httpEntity.getRequestModel().getClass().isAnnotationPresent(TransactionRequired.class)) {
            TransactionDefinition txDefinition = new DefaultTransactionDefinition();
            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
            Function<TransactionStatus, Boolean> transactionCommit = transactionStatus -> {
                try {
                    txManager.commit(transactionStatus);
                    LogUtils.info("Transaction commit success");
                    return transactionStatus.isCompleted();
                } catch (TransactionException e) {
                    LogUtils.error(e.getMessage(), e);
                    return false;
                }
            };
            Function<TransactionStatus, Boolean> transactionRollback = transactionStatus -> {
                try {
                    txManager.rollback(transactionStatus);
                    LogUtils.info("Transaction rollback success");
                    return transactionStatus.isCompleted();
                } catch (TransactionException e) {
                    LogUtils.error(e.getMessage(), e);
                    return false;
                }
            };
            TransactionContext transactionContext = new TransactionContext(txStatus, transactionCommit, transactionRollback);
            ((HttpEntity) t).setTransactionContext(transactionContext);
        } else {
            TransactionContext transactionContext = new TransactionContext();
            ((HttpEntity) t).setTransactionContext(transactionContext);
        }
        return t;
    }


}
