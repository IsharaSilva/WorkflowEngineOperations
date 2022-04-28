package org.wso2.carbon.identity.workflow.engine;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.workflow.engine.dao.WorkflowDefinitionDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowDefinitionDAOImpl;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineClientException;
import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineExceptionManagementUtil;

import java.util.List;
import java.util.Optional;

public class DefaultWorkflowEngineImpl implements WorkflowEngine {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();

        try {
            validateInputs(workflowDefinition);
            if (workflowDefinitionDAO.isWorkflowDefinitionExits(workflowDefinition.getWfName(), tenantId)) {

                throw WorkflowEngineExceptionManagementUtil.handleClientException(
                        WorkflowEngineConstants.ErrorMessage.ERROR_ALREADY_EXIST_WORKFLOW_DEFINITION_NAME);
            }
        } catch (WorkflowEngineClientException e) {
            throw new RuntimeException(e);
        }
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

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        workflowDefinitionDAO.deleteDefinition(wfId, tenantId);
    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        WorkflowDefinition workflowDefinition = new WorkflowDefinition();

        try {
            validateInputs(updatedWorkflowDefinition);

            if (!updatedWorkflowDefinition.getWfName().equals(workflowDefinition.getWfName()) &&
                    workflowDefinitionDAO.isWorkflowDefinitionExits(workflowDefinition.getWfName(), tenantId)) {

                throw WorkflowEngineExceptionManagementUtil.handleClientException(
                        WorkflowEngineConstants.ErrorMessage.ERROR_ALREADY_EXIST_WORKFLOW_DEFINITION_NAME);
            }
        } catch (WorkflowEngineClientException e) {
            throw new RuntimeException(e);
        }
        workflowDefinitionDAO.updateDefinition(wfId, updatedWorkflowDefinition, tenantId);
    }

    private void validateInputs(WorkflowDefinition workflowDefinition) throws WorkflowEngineClientException {

        if (StringUtils.isBlank(workflowDefinition.getWfName())) {
            throw WorkflowEngineExceptionManagementUtil.handleClientException(
                    WorkflowEngineConstants.ErrorMessage.ERROR_REQUIRE_WORKFLOW_DEFINITION_NAME);
        } else if (StringUtils.isBlank(workflowDefinition.getApprovalSubject())) {
            throw WorkflowEngineExceptionManagementUtil.handleClientException(
                    WorkflowEngineConstants.ErrorMessage.ERROR_REQUIRE_APPROVAL_SUBJECT);
        }

    }

    public boolean isWorkflowDefinitionExits(String wfName, int tenantId) {

        WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAOImpl();
        return workflowDefinitionDAO.isWorkflowDefinitionExits(wfName, tenantId);
    }
}
