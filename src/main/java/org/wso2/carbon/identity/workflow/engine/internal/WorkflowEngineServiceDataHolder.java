package org.wso2.carbon.identity.workflow.engine.internal;

import org.wso2.carbon.identity.workflow.engine.WorkflowEngine;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService;

public class WorkflowEngineServiceDataHolder {

    private static WorkflowEngineServiceDataHolder instance = new WorkflowEngineServiceDataHolder();

    private WorkflowManagementService workflowManagementService;

    private WorkflowEngine workflowEngine = null;
    private WorkflowExecutorManagerService workflowExecutorManagerService;

    private WorkflowEngineServiceDataHolder() {

    }

    public static WorkflowEngineServiceDataHolder getInstance() {

        return instance;
    }

    public WorkflowManagementService getWorkflowManagementService() {
        return workflowManagementService;
    }

    public void setWorkflowManagementService(
            WorkflowManagementService workflowManagementService) {
        this.workflowManagementService = workflowManagementService;
    }

    public WorkflowEngine getWorkflowService() {

        return workflowEngine;
    }

    public void setWorkflowService(WorkflowEngine workflowEngine) {

        this.workflowEngine = workflowEngine;
    }

    public WorkflowExecutorManagerService getWorkflowExecutorManagerService() {

        return workflowExecutorManagerService;
    }

    public void setWorkflowExecutorManagerService(
            WorkflowExecutorManagerService workflowExecutorManagerService) {
        this.workflowExecutorManagerService = workflowExecutorManagerService;
    }
}
