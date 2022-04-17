package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.apache.commons.lang.StringUtils;
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
                if(StringUtils.isEmpty(WF_NAME_COLUMN)){
                    return "Error occurred while not adding Workflow Name";
                }
                if(StringUtils.isEmpty(APPROVAL_SUBJECT_COLUMN)){
                    return "Error occurred while not adding Approval Subject";
                }
            } catch (DataAccessException e) {

            }
        }
        return workflowDefinition.getWfId();
    }

    @Override
    public Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId) {

        WorkflowDefinition workflowDefinition = null;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            workflowDefinition = jdbcTemplate.fetchSingleRecord(WorkflowDefinitionConstants.SqlQueries.GET_WORKFLOW,
                    ((resultSet, i) ->
                            new WorkflowDefinition(
                                    resultSet.getString(WF_NAME_COLUMN),
                                    resultSet.getString(WF_DESCRIPTION_COLUMN),
                                    resultSet.getString(APPROVAL_SUBJECT_COLUMN),
                                    resultSet.getString(APPROVAL_DESCRIPTION_COLUMN))),

                    preparedStatement -> {
                        preparedStatement.setString(1, wfId);
                        preparedStatement.setInt(2, tenantId);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineSQLException(String.format("Error occurred while retrieving workflow" +
                                "workflow Id: %s in tenant Id: %d",
                        wfId, tenantId), e);
            } catch (WorkflowEngineSQLException ex) {
                ex.printStackTrace();
            }
        }
        return Optional.ofNullable(workflowDefinition);
    }

    @Override
    public List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId) {

        List<WorkflowDefinition> workflowDefinitionList = null;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            workflowDefinitionList = jdbcTemplate.executeQuery(WorkflowDefinitionConstants.SqlQueries.
                            LIST_WORKFLOWS_QUERY, (resultSet, rowNumber) -> new WorkflowDefinition(
                            resultSet.getString(WF_NAME_COLUMN),
                            resultSet.getString(WF_DESCRIPTION_COLUMN),
                            resultSet.getString(APPROVAL_SUBJECT_COLUMN),
                            resultSet.getString(APPROVAL_DESCRIPTION_COLUMN)),
                    preparedStatement -> {
                        preparedStatement.setString(1, searchQuery);
                        preparedStatement.setInt(2, limit);
                        preparedStatement.setInt(3, offSet);
                        preparedStatement.setInt(4, tenantId);
                    });
        } catch (DataAccessException e) {
            String errorMessage = "Error occurred while retrieving all workflow Definitions";
            try {
                throw new WorkflowEngineSQLException(errorMessage, e);
            } catch (WorkflowEngineSQLException ex) {
                ex.printStackTrace();
            }
        }
        return workflowDefinitionList;
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowDefinitionConstants.SqlQueries.DELETE_WORKFLOW_QUERY,
                    preparedStatement -> {
                        preparedStatement.setString(1, wfId);
                        preparedStatement.setInt(2, tenantId);
                    });
        } catch (DataAccessException e) {
            try {
                String errorMessage = String.format("Error while deleting the workflow from wfId:%s, in tenant Id: %d."
                        , wfId, tenantId);
                throw new WorkflowEngineSQLException(errorMessage, e);
            } catch (WorkflowEngineSQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

    }
}
