package exception;

public class WorkflowEngineException extends Exception {

    private String message;

    public WorkflowEngineException(String message) {

        this.message = message;

    }
}
