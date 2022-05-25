package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.configuration.mgt.core.util.JdbcUtils;
import org.wso2.carbon.identity.workflow.engine.dao.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineRuntimeException;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;

import static org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants.CURRENT_STEP_COLUMN;

public class WorkflowEventRequestDAOImpl implements WorkflowEventRequestDAO {

    @Override
    public String addApproversOfRequest(String taskId, String eventId, String workflowId, String approverType, String approverName) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.ADD_APPROVAL_LIST_RELATED_TO_USER,
                    preparedStatement -> {
                        preparedStatement.setString(1, taskId);
                        preparedStatement.setString(2, eventId);
                        preparedStatement.setString(3, workflowId);
                        preparedStatement.setString(4, approverType);
                        preparedStatement.setString(5, approverName);
                    });
        } catch (DataAccessException e) {
            String errorMessage = String.format("Error occurred while adding request details" +
                    "in eventId: %s  & workflowId: %s", eventId, workflowId);
            throw new WorkflowEngineRuntimeException(errorMessage);
        }
        return eventId;
    }

    @Override
    public void createStatesOfRequest(String eventId, String workflowId, int currentStep) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.ADD_CURRENT_STEP_FOR_EVENT,
                    preparedStatement -> {
                        preparedStatement.setString(1, eventId);
                        preparedStatement.setString(2, workflowId);
                        preparedStatement.setInt(3, currentStep);
                    });
        } catch (DataAccessException e) {
            String errorMessage = String.format("Error occurred while adding request approval steps" +
                    "in event Id: %s", eventId);
            throw new WorkflowEngineRuntimeException(errorMessage);
        }
    }

    @Override
    public void getCurrentStep(String eventId) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.fetchSingleRecord(WorkflowEngineConstants.SqlQueries.GET_CURRENT_STEP,
                    ((resultSet, i) -> (
                            resultSet.getInt(CURRENT_STEP_COLUMN))),

                    preparedStatement -> {
                        preparedStatement.setString(1, eventId);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineRuntimeException(String.format("Error occurred while retrieving currentStep from" +
                        "event Id: %s", eventId), e);
            } catch (WorkflowEngineRuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }
}