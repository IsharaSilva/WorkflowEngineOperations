package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowDefinitionDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowDefinitionDAOImpl;
import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;

import java.util.List;
import java.util.Optional;

public class WorkflowEngineDefinition implements WorkflowEngine {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        return workflowDefinitionDAO.addDefinition(workflowDefinition, tenantId);
    }

    @Override
    public Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        return workflowDefinitionDAO.getDefinition(wfId, tenantId);
    }

    @Override
    public List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        return workflowDefinitionDAO.listDefinitions(searchQuery, limit, offSet, tenantId);
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) {

        //TODO implementation
        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        workflowDefinitionDAO.deleteDefinition(wfId, tenantId);
    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

        //TODO implementation
    }
}
