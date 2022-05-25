package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.configuration.mgt.core.util.JdbcUtils;
import org.wso2.carbon.identity.workflow.engine.dao.WorkflowDefinitionDAO;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineRuntimeException;
import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;

import java.util.List;
import java.util.Optional;

import static org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants.APPROVAL_DESCRIPTION_COLUMN;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants.APPROVAL_SUBJECT_COLUMN;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants.WF_DESCRIPTION_COLUMN;
import static org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants.WF_NAME_COLUMN;

public class WorkflowDefinitionDAOImpl implements WorkflowDefinitionDAO {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

            JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
            try {
                jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.ADD_WORKFLOW_QUERY,
                        preparedStatement -> {
                            preparedStatement.setString(1, workflowDefinition.getWfId());
                            preparedStatement.setString(2, workflowDefinition.getWfName());
                            preparedStatement.setString(3, workflowDefinition.getWfDescription());
                            preparedStatement.setString(4, workflowDefinition.getApprovalSubject());
                            preparedStatement.setString(5, workflowDefinition.getApprovalDescription());
                            preparedStatement.setInt(6, tenantId);
                        });
            } catch (DataAccessException e) {
                try {
                    throw new WorkflowEngineRuntimeException(String.format("Error occurred while adding definition" +
                            "in tenant Id: %d", tenantId), e);
                } catch (WorkflowEngineRuntimeException ex) {
                    ex.printStackTrace();
                }
            }
            return workflowDefinition.getWfId();
    }

    @Override
    public Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId) {

        WorkflowDefinition workflowDefinition = null;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            workflowDefinition = jdbcTemplate.fetchSingleRecord(WorkflowEngineConstants.SqlQueries.GET_WORKFLOW,
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
                throw new WorkflowEngineRuntimeException(String.format("Error occurred while retrieving workflow from" +
                                "workflow Id: %s in tenant Id: %d", wfId, tenantId), e);
            } catch (WorkflowEngineRuntimeException ex) {
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
            workflowDefinitionList = jdbcTemplate.executeQuery(WorkflowEngineConstants.SqlQueries.
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
            try {
                throw new WorkflowEngineRuntimeException(String.format("Error occurred while retrieving all workflow " +
                        "Definitions in tenant Id: %d.", tenantId),e);
            } catch (WorkflowEngineRuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return workflowDefinitionList;
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.DELETE_WORKFLOW_QUERY,
                    preparedStatement -> {
                        preparedStatement.setString(1, wfId);
                        preparedStatement.setInt(2, tenantId);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineRuntimeException(String.format("Error while deleting the workflow from " +
                                "wfId:%s, in tenant Id: %d.", wfId, tenantId),e);
            } catch (WorkflowEngineRuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.UPDATE_WORKFLOW_QUERY,
                    (preparedStatement -> {
                        preparedStatement.setString(1, wfId);
                        preparedStatement.setString(2, updatedWorkflowDefinition.getWfName());
                        preparedStatement.setString(3, updatedWorkflowDefinition.getWfDescription());
                        preparedStatement.setString(4, updatedWorkflowDefinition.getApprovalSubject());
                        preparedStatement.setString(5, updatedWorkflowDefinition.getApprovalDescription());
                        preparedStatement.setInt(6, tenantId);
                    }));
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineRuntimeException(String.format("Error occurred while updating definition from" +
                        "wfId:%s, in tenant Id: %d.", wfId, tenantId), e);
            } catch (WorkflowEngineRuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean isWorkflowDefinitionExits(String wfName, int tenantId) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        String workflowName = null;
        try {
            workflowName = jdbcTemplate.fetchSingleRecord(WorkflowEngineConstants.SqlQueries.
                            LOAD_WORKFLOW_DEFINITION_FROM_TENANT_ID_AND_NAME,
                    (resultSet, rowNumber) ->
                            resultSet.getString(wfName),
                    preparedStatement -> {
                        preparedStatement.setString(1, wfName);
                        preparedStatement.setInt(2, tenantId);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineRuntimeException(String.format("Error occurred while retrieving workflow from" +
                        "workflow name: %s in tenant Id: %d", wfName, tenantId), e);
            } catch (WorkflowEngineRuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return workflowName != null;
    }
}

