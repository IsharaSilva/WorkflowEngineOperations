package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.configuration.mgt.core.util.JdbcUtils;
import org.wso2.carbon.identity.workflow.engine.dao.WorkflowDefinitionDAO;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineSQLException;
import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowDefinitionConstants;

import java.util.List;
import java.util.Optional;

import static org.wso2.carbon.identity.workflow.engine.util.WorkflowDefinitionConstants.APPROVAL_DESCRIPTION_COLUMN;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowDefinitionConstants.APPROVAL_SUBJECT_COLUMN;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowDefinitionConstants.ErrorMessages.ERROR_CODE_SELECT_TEMPLATE_BY_WFID;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowDefinitionConstants.WF_DESCRIPTION_COLUMN;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowDefinitionConstants.WF_NAME_COLUMN;

public class WorkflowDefinitionDAOImpl implements WorkflowDefinitionDAO {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        {
            JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
            try {
                jdbcTemplate.executeUpdate(WorkflowDefinitionConstants.SqlQueries.ADD_WORKFLOW_QUERY,
                        preparedStatement -> {
                            preparedStatement.setString(1, workflowDefinition.getWfId());
                            preparedStatement.setString(2, workflowDefinition.getWfName());
                            preparedStatement.setString(3, workflowDefinition.getWfDescription());
                            preparedStatement.setString(4, workflowDefinition.getApprovalSubject());
                            preparedStatement.setString(5, workflowDefinition.getApprovalDescription());
                            preparedStatement.setInt(6, tenantId);
                        });
            } catch (DataAccessException e) {

            }
        }
        return workflowDefinition.getWfId();
    }

    @Override
    public Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId) throws WorkflowEngineSQLException {

        WorkflowDefinition workflowDefinition;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            workflowDefinition = jdbcTemplate.fetchSingleRecord(WorkflowDefinitionConstants.SqlQueries.GET_WORKFLOW,
                    ((resultSet, i) -> {
                        WorkflowDefinition workflowDefinitionResult;
                        try {
                            workflowDefinitionResult = new WorkflowDefinition(
                                    resultSet.getString(WF_NAME_COLUMN),
                                    resultSet.getString(WF_DESCRIPTION_COLUMN),
                                    resultSet.getString(APPROVAL_SUBJECT_COLUMN),
                                    resultSet.getString(APPROVAL_DESCRIPTION_COLUMN));
                        } catch (Exception e) {
                            throw new WorkflowEngineSQLException(String.format(ERROR_CODE_SELECT_TEMPLATE_BY_WFID.getMessage(),
                                    tenantId, wfId));
                        }
                        return workflowDefinitionResult;
                    }),
                    preparedStatement -> {
                        preparedStatement.setString(1, wfId);
                        preparedStatement.setInt(2, tenantId);
                    });
        } catch (DataAccessException e) {
            throw new WorkflowEngineSQLException(String.format("Error Error while checking existence of \" +\n" +
                            "workflow: %s in tenant: %s",
                    tenantId, wfId));
        }
        return Optional.ofNullable(workflowDefinition);
    }

    @Override
    public List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId) {

        return null;
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) {

    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

    }
}
