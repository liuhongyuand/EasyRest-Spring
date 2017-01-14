package com.simpacct.framework.core.model;

import org.springframework.transaction.TransactionStatus;

import java.util.function.Function;

/**
 * Created by liuhongyu.louie on 2017/1/12.
 */
public class TransactionContext {

    private boolean isTransactionStart = false;
    private TransactionStatus transactionStatus;
    private Function<TransactionStatus, Boolean> transactionCommit;
    private Function<TransactionStatus, Boolean> transactionRollback;

    public TransactionContext(){}

    public TransactionContext(TransactionStatus transactionStatus, Function<TransactionStatus, Boolean> transactionCommit, Function<TransactionStatus, Boolean> transactionRollback){
        this.transactionStatus = transactionStatus;
        this.transactionCommit = transactionCommit;
        this.transactionRollback = transactionRollback;
        this.isTransactionStart = true;
    }

    public boolean isTransactionStart() {
        return isTransactionStart;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public Function<TransactionStatus, Boolean> getTransactionCommit() {
        return transactionCommit;
    }

    public Function<TransactionStatus, Boolean> getTransactionRollback() {
        return transactionRollback;
    }
}
