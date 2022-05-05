package org.wso2.carbon.identity.workflow.engine.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.workflow.engine.DefaultWorkflowEngineImpl;
import org.wso2.carbon.identity.workflow.engine.WorkflowEngine;
import org.wso2.carbon.identity.workflow.mgt.WorkFlowExecutorManager;

public class WorkflowEngineServiceComponent {

   @Activate
   protected void activate(ComponentContext context){
       BundleContext bundleContext = context.getBundleContext();
       WorkflowEngine workflowEngine = new DefaultWorkflowEngineImpl();
       bundleContext.registerService(WorkflowEngine.class, workflowEngine, null);
       WorkflowEngineServiceDataHolder.getInstance().setWorkflowService(workflowEngine);
       WorkflowEngineServiceDataHolder.getInstance().setBundleContext(bundleContext);
   }

    @Reference(
            name = "org.wso2.carbon.identity.workflow.mgt",
            service = org.wso2.carbon.identity.workflow.mgt.WorkFlowExecutorManager.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "")
    protected void setWorkFlowExecutorManager(WorkFlowExecutorManager workFlowExecutorManager) {
        WorkflowEngineServiceDataHolder.getInstance().setWorkFlowExecutorManager(workFlowExecutorManager);
    }
}
