package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.configuration.mgt.core.util.JdbcUtils;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineSQLException;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;

public class EventRequestExecutorDAO {

    public void addApproversOfRequest(String workflowId, String requestId, String approvertype, String approver) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.ADD_APPROVAL_LIST_RELATED_TO_USER,
                    preparedStatement -> {
                        preparedStatement.setString(1, workflowId);
                        preparedStatement.setString(2, requestId);
                        preparedStatement.setString(3, approvertype);
                        preparedStatement.setString(4, approver);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineSQLException(String.format("Error occurred while adding request details" +
                        "in request Id: %d", requestId), e);
            } catch (WorkflowEngineSQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createStatesOfRequest(String eventId, int currentStep) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.ADD_APPROVAL_LIST_RELATED_TO_USER,
                    preparedStatement -> {
                        preparedStatement.setString(1, eventId);
                        preparedStatement.setInt(2, currentStep);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineSQLException(String.format("Error occurred while adding request approval steps" +
                        "in event Id: %d", eventId), e);
            } catch (WorkflowEngineSQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}