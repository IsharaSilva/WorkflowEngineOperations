package org.wso2.carbon.identity.workflow.engine.exception;

import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;

import java.sql.SQLException;

public class WorkflowEngineSQLException extends SQLException {

    public WorkflowEngineSQLException(String message, DataAccessException e) {

        super(message);
    }

}
