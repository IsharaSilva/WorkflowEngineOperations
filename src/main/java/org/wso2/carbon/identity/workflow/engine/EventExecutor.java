package org.wso2.carbon.identity.workflow.engine;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.jaxen.JaxenException;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineException;
import org.wso2.carbon.identity.workflow.engine.internal.WorkflowEngineServiceDataHolder;
import org.wso2.carbon.identity.workflow.engine.util.EventResultState;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.Workflow;
import org.wso2.carbon.identity.workflow.mgt.bean.WorkflowAssociation;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.exception.InternalWorkflowException;
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowException;
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowRuntimeException;
import org.wso2.carbon.identity.workflow.mgt.util.WorkflowRequestBuilder;
import org.wso2.carbon.identity.workflow.mgt.workflow.AbstractWorkflow;

import java.util.List;
import java.util.UUID;

public class EventExecutor {

    public EventExecutorResult executeWorkflow(WorkflowRequest workFlowRequest) {

        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        OMElement xmlRequest = WorkflowRequestBuilder.buildXMLRequest(workFlowRequest);
        List<WorkflowAssociation> associations;
        try {
            associations = workFlowExecutorManagerService.getWorkflowAssociationsForRequest(
                    workFlowRequest.getEventType(), workFlowRequest.getTenantId());
        } catch (InternalWorkflowException e) {
            throw new WorkflowRuntimeException("The associations are not connecting with any request");
        }
        boolean workflowEngaged = false;
        boolean requestSaved = false;
        for (WorkflowAssociation association : associations) {
            try {
                AXIOMXPath xPath = new AXIOMXPath(association.getAssociationCondition());
                if (xPath.booleanValueOf(xmlRequest)) {
                    workflowEngaged = true;
                    if (!requestSaved) {
                        int tenant = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
                        String currentUser = PrivilegedCarbonContext.getThreadLocalCarbonContext().getUsername();
                        workFlowExecutorManagerService.addWorkflowRequest(workFlowRequest, currentUser, tenant);
                        requestSaved = true;
                    }
                    Workflow workflow = workflowManagementService.getWorkflow(association.getWorkflowId());
                    AbstractWorkflow newTemplateImplementation = WorkflowEngineServiceDataHolder.getInstance()
                            .getWorkflowImpls().get(workflow.getTemplateId()).get(workflow.getWorkflowImplId());
                    List<Parameter> parameterList = workflowManagementService.getWorkflowParameters(
                            association.getWorkflowId());
                    String relationshipId = UUID.randomUUID().toString();
                    WorkflowRequest request = workFlowRequest.clone();
                    request.setUuid(relationshipId);
                    newTemplateImplementation.execute(request, parameterList);
                    workFlowExecutorManagerService.addNewRequestRelationship(relationshipId,
                            association.getWorkflowId(), workFlowRequest.getUuid(),
                            WorkflowEngineConstants.EventState.PENDING.toString(), workFlowRequest.getTenantId());
                }

            } catch (JaxenException e) {
                return new EventExecutorResult(EventResultState.FAILED, "Error when executing the xpath");
            } catch (CloneNotSupportedException e) {
                return new EventExecutorResult(EventResultState.FAILED, "Error while cloning workflowRequest");
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("not valid");
            }
        }
        if (!workflowEngaged) {
            return new EventExecutorResult(EventResultState.CONDITION_FAILED);
        }
        return new EventExecutorResult(EventResultState.STARTED_ASSOCIATION);
    }

}



