package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowDefinitionDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowDefinitionDAOImpl;
import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkflowEngineDefinition implements WorkflowEngine {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDao = new WorkflowDefinitionDAOImpl();
        Optional<WorkflowDefinition> workflowName = workflowDefinitionDao.getDefinition(workflowDefinition.getWfName(), tenantId);

        // It returns value of an Optional.
        // If workflowName value is not present, it throws
        // an NoSuchElementException
        //workflowName.get();
        if (!workflowName.isPresent()) {
            return "Workflow name cannot null";
        } else if (workflowName.equals(workflowDefinition.getWfName())) {
            return "Workflow name cannot duplicate";
        } else {
            workflowDefinition.getWfName();
        }

        Optional<WorkflowDefinition> approvalSubject = workflowDefinitionDao.getDefinition(workflowDefinition.getApprovalSubject(), tenantId);
        //If there is no value present in this Optional, then returns false
        // else true.
        if (!approvalSubject.isPresent()) {
            return "Approval subject cannot empty";
        } else {
            workflowDefinition.getApprovalSubject();
        }

        return workflowDefinitionDao.addDefinition(workflowDefinition, tenantId);
    }

    @Override
    public Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId) {

        //TODO implementation
        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        return workflowDefinitionDAO.getDefinition(wfId, tenantId);
        //return Optional.of(new WorkflowDefinition());
    }

    @Override
    public List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId) {

        //TODO implementation
        return new ArrayList<>();
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) {

        //TODO implementation
    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

        //TODO implementation
    }
}
