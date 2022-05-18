package org.wso2.carbon.identity.workflow.engine.exception;

import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;

import java.sql.SQLException;

public class WorkflowEngineServerException extends SQLException {

    public WorkflowEngineServerException(String message) {

        super(message);
    }
    public WorkflowEngineServerException(String message, DataAccessException e) {

        super(message);
    }

}
