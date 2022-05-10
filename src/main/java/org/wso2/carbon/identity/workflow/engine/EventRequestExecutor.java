package org.wso2.carbon.identity.workflow.engine;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.workflow.engine.dao.impl.EventRequestExecutorDAO;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.RequestParameter;
import org.wso2.carbon.identity.workflow.mgt.bean.metadata.InputData;
import org.wso2.carbon.identity.workflow.mgt.bean.metadata.ParameterMetaData;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowRuntimeException;
import org.wso2.carbon.identity.workflow.mgt.util.WorkflowRequestStatus;
import org.wso2.carbon.identity.workflow.mgt.workflow.AbstractWorkflow;
import org.wso2.carbon.identity.workflow.mgt.workflow.TemplateInitializer;
import org.wso2.carbon.identity.workflow.mgt.workflow.WorkFlowExecutor;

import java.util.List;
import java.util.Map;

public class EventRequestExecutor extends AbstractWorkflow {

    public EventRequestExecutor(Class<? extends TemplateInitializer> templateInitializerClass, Class<?
            extends WorkFlowExecutor> workFlowExecutorClass, String metaDataXML, Map<String, List<Object>> listTypeParams) throws WorkflowRuntimeException {

        super(templateInitializerClass, workFlowExecutorClass, metaDataXML);
        this.listTypeParams = listTypeParams;
    }

    @Override
    protected InputData getInputData(ParameterMetaData parameterMetaData) {

        return null;
    }

    @Override
    public void execute(WorkflowRequest workFlowRequest, List<Parameter> parameterList) {

       /* for (RequestParameter parameter : workFlowRequest.getRequestParameters()) {
            if (parameter.isRequiredInWorkflow()) {
                requestExecutor.addListTypeParam(parameter.getName(), (List<Object>) parameter.getValue());
            }
        }
        String requestId = WorkflowRequest.getUuid()..toString();
        Workflow workflow = workflowDAO.getWorkflow(association.getWorkflowId());
        String approverType = RequestParameter.getName();
        String approver = RequestParameter.getValue();
        List<String> statesOfRequest = workflowRequestAssociationDAO.getWorkflowStatesOfRequest(requestId);
        for (String state : statesOfRequest) {
            if (state.equals(WorkflowRequestStatus.APPROVED.toString())) {
                workflowRequestDAO.updateStatusOfRequest(requestId, state);
            } else {
                EventRequestExecutorDAO eventRequestExecutorDAO = new EventRequestExecutorDAO();
                eventRequestExecutorDAO.addRequestApprover(workflow, requestId, approverType, approver);
            }
        }*/

        WorkFlowExecutorManagerService workFlowExecutorManagerService=new WorkFlowExecutorManagerService();
        String workflowId=workFlowExecutorManagerService.addWorkflowEntry();
        String requestId;
        for (RequestParameter parameter : workFlowRequest.getRequestParameters()) {
            if (parameter.isRequiredInWorkflow()) {
                String approverType=parameter.getName();
                List<Object> approver= (List<Object>) parameter.getValue();
                EventRequestExecutorDAO eventRequestExecutorDAO = new EventRequestExecutorDAO();
                eventRequestExecutorDAO.addApproversOfRequest(workflowId, requestId, approverType, approver);

            }
        }
    }
    private Map<String, List<Object>> listTypeParams;

    public EventRequestExecutor addListTypeParam(String key, List<Object> value) {

        if (StringUtils.isNotBlank(key) && (value != null)) {
            listTypeParams.put(key, value);
            return this;
        }
        return null;
    }
}

