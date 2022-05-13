package org.wso2.carbon.identity.workflow.engine;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.workflow.engine.dao.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowEventRequestDAOImpl;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineRuntimeException;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.WorkflowAssociation;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.exception.InternalWorkflowException;
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowException;

import java.util.Arrays;
import java.util.List;

public class DefaultWorkflowEventRequest {
    private List<Parameter> parameterList;
    public void executeWorkflow(WorkflowRequest request, String approverType, String approverName) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        List<WorkflowAssociation> associations;
        WorkflowExecutorManagerService WorkflowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        try {
            associations = WorkflowExecutorManagerService.getWorkflowAssociationsForRequest(
                    request.getEventType(), request.getTenantId());
        } catch (InternalWorkflowException e) {
            throw new WorkflowEngineRuntimeException("The associations are not connecting with any request");
        }
        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        String eventId = request.getUuid();
        for (WorkflowAssociation association : associations) {
            String workflowId;
            try {
                workflowId = String.valueOf(workflowManagementService.getWorkflow(association.getWorkflowId()));
            } catch (WorkflowException e) {
                throw new WorkflowEngineRuntimeException("Thw workflow Id is not valid");
            }
            for (Parameter parameter : this.parameterList) {
                if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.STEPS_USER_AND_ROLE)) {
                    //use split method to get string last word EX: UserAndRole-step-1-roles -> lastWord=roles
                    String[] stepName = parameter.getqName().split("-");
                    approverType = stepName[stepName.length - 1];
                }
                String approver = parameter.getParamValue();
                String[] approvers = null;
                if (StringUtils.isNotBlank(approver)) {
                    approvers = approver.split(",");
                }
                if (approvers != null) {
                    List<String> approverList = Arrays.asList(approver);
                    for (String name : approverList) {
                        approverName = name;
                    }
                }
            }
            workflowEventRequestDAO.addApproversOfRequest(workflowId, eventId, approverType, approverName);
        }
    }

    public void createStatesOfRequest(String eventId, int currentStep) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        for(int i=1;i<=parameterList.size();i++)
        {

        }
        workflowEventRequestDAO.createStatesOfRequest(eventId, currentStep);
    }

}
