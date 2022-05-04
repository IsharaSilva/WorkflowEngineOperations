package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.configuration.mgt.core.util.JdbcUtils;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineSQLException;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;

public class EventRequestExecutorDAO {

    public void addRequestApprover(String workflowId, String requestId, String approvertype, String approver,
                                          int tenantId) {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(WorkflowEngineConstants.SqlQueries.ADD_APPROVAL_LIST_RELATED_TO_USER,
                    preparedStatement -> {
                        preparedStatement.setString(1, workflowId);
                        preparedStatement.setString(2, requestId);
                        preparedStatement.setString(3, approvertype);
                        preparedStatement.setString(4, approver);
                        preparedStatement.setInt(6, tenantId);
                    });
        } catch (DataAccessException e) {
            try {
                throw new WorkflowEngineSQLException(String.format("Error occurred while adding definition" +
                        "in tenant Id: %d", tenantId), e);
            } catch (WorkflowEngineSQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
