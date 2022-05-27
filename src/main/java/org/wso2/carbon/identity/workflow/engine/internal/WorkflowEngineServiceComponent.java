package org.wso2.carbon.identity.workflow.engine.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.workflow.engine.DefaultApprovalWorkflow;
import org.wso2.carbon.identity.workflow.engine.DefaultWorkflowEngineImpl;
import org.wso2.carbon.identity.workflow.engine.DefaultWorkflowExecutor;
import org.wso2.carbon.identity.workflow.engine.WorkflowEngine;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService;
import org.wso2.carbon.identity.workflow.mgt.bean.metadata.MetaData;
import org.wso2.carbon.identity.workflow.mgt.workflow.AbstractWorkflow;

import java.io.StringWriter;
import javax.xml.bind.JAXB;

public class WorkflowEngineServiceComponent {
    @Activate
    protected void activate(ComponentContext context) {

        BundleContext bundleContext = context.getBundleContext();
        WorkflowEngine workflowEngine = new DefaultWorkflowEngineImpl();
        bundleContext.registerService(WorkflowEngine.class, workflowEngine, null);
        bundleContext.registerService(AbstractWorkflow.class, new DefaultApprovalWorkflow(DefaultWorkflowExecutor.class,
                getMetaDataXML()), null);
        WorkflowEngineServiceDataHolder.getInstance().setWorkflowService(workflowEngine);
    }

    private String getMetaDataXML() {

        StringWriter stringWriter = new StringWriter();
        MetaData metaData = new MetaData();
        MetaData.WorkflowImpl workflowImpl=new MetaData.WorkflowImpl();
        workflowImpl.setWorkflowImplId("newWorkflowImpl");
        metaData.setWorkflowImpl(workflowImpl);
        MetaData.Template template = new MetaData.Template();
        template.setTemplateId("newTemplate");
        metaData.setTemplate(template);
        JAXB.marshal(metaData, stringWriter);
        return stringWriter.toString();
    }

    @Reference(
            name = "org.wso2.carbon.identity.workflow.mgt",
            service = org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetWorkflowManagementService")
    protected void setWorkflowManagementService(WorkflowManagementService workflowManagementService) {

        WorkflowEngineServiceDataHolder.getInstance().setWorkflowManagementService(workflowManagementService);
    }

    protected void unsetWorkflowManagementService(WorkflowManagementService workflowManagementService) {
        WorkflowEngineServiceDataHolder.getInstance().setWorkflowManagementService(null);
    }

    @Reference(
            name = "org.wso2.carbon.identity.workflow.mgt",
            service = org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetWorkflowExecutorManagerService")
    protected void setWorkflowExecutorManagerService(WorkflowExecutorManagerService workflowExecutorManagerService) {
        WorkflowEngineServiceDataHolder.getInstance().setWorkflowExecutorManagerService(workflowExecutorManagerService);
    }

    protected void unsetWorkflowExecutorManagerService(WorkflowExecutorManagerService workflowExecutorManagerService) {
        WorkflowEngineServiceDataHolder.getInstance().setWorkflowExecutorManagerService(workflowExecutorManagerService);
    }
}
