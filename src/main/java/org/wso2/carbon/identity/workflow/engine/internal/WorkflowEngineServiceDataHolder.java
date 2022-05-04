package org.wso2.carbon.identity.workflow.engine.internal;

import org.osgi.framework.BundleContext;
import org.wso2.carbon.identity.workflow.engine.WorkflowEngine;
import org.wso2.carbon.identity.workflow.mgt.WorkFlowExecutorManager;

public class WorkflowEngineServiceDataHolder {

    private static WorkflowEngineServiceDataHolder instance = new WorkflowEngineServiceDataHolder();

    private BundleContext bundleContext;

    private WorkFlowExecutorManager workFlowExecutorManager;
    private WorkflowEngine workflowEngine = null;

    private WorkflowEngineServiceDataHolder() {

    }

    public static WorkflowEngineServiceDataHolder getInstance() {

        return instance;
    }

    public BundleContext getBundleContext() {

        return bundleContext;
    }

    public void setBundleContext(BundleContext bundleContext) {

        this.bundleContext = bundleContext;
    }

    public WorkFlowExecutorManager workFlowExecutorManager() {

        return workFlowExecutorManager;
    }

    public void setWorkFlowExecutorManager(WorkFlowExecutorManager workFlowExecutorManager) {

        this.workFlowExecutorManager = workFlowExecutorManager;
    }

    public WorkflowEngine getWorkflowService() {

        return workflowEngine;
    }

    public void setWorkflowService(WorkflowEngine workflowEngine) {

        this.workflowEngine = workflowEngine;
    }
}
