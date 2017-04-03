package com.easyrest.framework.core.services.workStep;

import com.easyrest.framework.core.services.workStep.after.history.api.HistoryRecordStep;
import com.easyrest.framework.core.services.workStep.after.transaction.api.TransactionCommitStep;
import com.easyrest.framework.core.services.workStep.after.transaction.impl.TransactionCommitStepImpl;
import com.easyrest.framework.core.services.workStep.api.AfterServiceStep;
import com.easyrest.framework.core.services.workStep.api.BeforeServiceStep;
import com.easyrest.framework.core.services.workStep.before.customized.api.CustomizedCheckStep;
import com.easyrest.framework.core.services.workStep.before.fieldCheck.api.FieldDefinedCheckStep;
import com.easyrest.framework.core.services.workStep.before.fieldInject.api.ValueInjectStep;
import com.easyrest.framework.core.services.workStep.before.permission.api.PermissionCheckStep;
import com.easyrest.framework.core.services.workStep.before.prepared.api.RequestPreparedStep;
import com.easyrest.framework.core.services.workStep.before.requestCheck.api.RequestValidateStep;
import com.easyrest.framework.core.services.workStep.before.transaction.api.TransactionPreparedStep;
import com.easyrest.framework.core.services.workStep.before.transaction.impl.TransactionPreparedWorkStepImpl;
import com.easyrest.framework.core.utils.BeanOperationUtils;
import com.easyrest.framework.easyrest.EasyRest;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liuhongyu.louie on 2017/4/3.
 */
@Component
public class WorkStaticProvider {

    private static RequestPreparedStep requestPreparedStep;

    private static RequestValidateStep requestValidateStep;

    private static ValueInjectStep valueInjectStep;

    private static PermissionCheckStep permissionCheckStep;

    private static FieldDefinedCheckStep fieldDefinedCheckStep;

    private static TransactionPreparedStep transactionPreparedStep;

    private static CustomizedCheckStep customizedCheckStep;

    private static HistoryRecordStep historyRecordStep;

    private static TransactionCommitStep transactionCommitStep;

    @Autowired
    public WorkStaticProvider(RequestPreparedStep requestPreparedStep, RequestValidateStep requestValidateStep, ValueInjectStep valueInjectStep, PermissionCheckStep permissionCheckStep, FieldDefinedCheckStep fieldDefinedCheckStep, CustomizedCheckStep customizedCheckStep, HistoryRecordStep historyRecordStep) {
        WorkStaticProvider.requestPreparedStep = requestPreparedStep;
        WorkStaticProvider.requestValidateStep = requestValidateStep;
        WorkStaticProvider.valueInjectStep = valueInjectStep;
        WorkStaticProvider.permissionCheckStep = permissionCheckStep;
        WorkStaticProvider.fieldDefinedCheckStep = fieldDefinedCheckStep;
        WorkStaticProvider.customizedCheckStep = customizedCheckStep;
        WorkStaticProvider.historyRecordStep = historyRecordStep;
    }

    public static List<BeforeServiceStep> getBeforeServiceSteps(){
        List<BeforeServiceStep> beforeServiceSteps = Lists.newArrayList(
                requestPreparedStep,
                requestValidateStep,
                valueInjectStep,
                permissionCheckStep,
                fieldDefinedCheckStep,
                customizedCheckStep
        );
        if (EasyRest.getEnabledAutoTransaction()) {
            WorkStaticProvider.transactionPreparedStep = BeanOperationUtils.registerBean("transactionPrepared", TransactionPreparedWorkStepImpl.class);
            beforeServiceSteps.add(transactionPreparedStep);
        }
        return beforeServiceSteps;
    }

    public static List<AfterServiceStep> getAfterServiceSteps(){
        List<AfterServiceStep> afterServiceSteps = Lists.newArrayList(
                historyRecordStep
        );
        if (EasyRest.getEnabledAutoTransaction()) {
            WorkStaticProvider.transactionCommitStep = BeanOperationUtils.registerBean("transactionCommit", TransactionCommitStepImpl.class);
            afterServiceSteps.add(transactionCommitStep);
        }
        return afterServiceSteps;
    }

}
