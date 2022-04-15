package org.wso2.carbon.identity.workflow.engine.exception;

import java.sql.SQLException;

public class WorkflowEngineSQLException extends SQLException {

        private String errorCode;

    public WorkflowEngineSQLException(String message) {

        super(message, cause);
        this.errorCode = errorCode;
    }

        public int getErrorCode() {

        return Integer.getInteger(errorCode);
    }

        protected void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }

}
