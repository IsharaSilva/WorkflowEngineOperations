package org.wso2.carbon.identity.workflow.engine.exception;

public class WorkflowEngineException extends Exception {

    private String errorCode;

    // private String message;

    public WorkflowEngineException(String message) {

        super(message);

    }
}
