package org.wso2.carbon.identity.workflow.engine.util;

public class WorkflowEngineConstants {

    public static final String WF_NAME_COLUMN = "WF_NAME";
    public static final String WF_DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String APPROVAL_SUBJECT_COLUMN = "APPROVAL_SUBJECT";
    public static final String APPROVAL_DESCRIPTION_COLUMN = "APPROVAL_DESCRIPTION";
    public static final String CURRENT_STEP_COLUMN = "CURRENT_STEP";

    public static class SqlQueries {

        public static final String ADD_WORKFLOW_QUERY = "INSERT INTO WF_WORKFLOW(ID, WF_NAME, DESCRIPTION, " +
                "APPROVAL_SUBJECT,APPROVAL_DESCRIPTION, TENANT_ID) VALUES (?,?, ?, ?, ?, ?)";

        public static final String GET_WORKFLOW = "SELECT WF_WORKFLOW.WF_NAME, WF_WORKFLOW.DESCRIPTION, WF_WORKFLOW" +
                ".APPROVAL_SUBJECT, WF_WORKFLOW.APPROVAL_DESCRIPTION, WF_WORKFLOW.TENANT_ID " +
                "FROM WF_WORKFLOW WHERE WF_WORKFLOW.ID = ? AND TENANT_ID=?";

        public static final String DELETE_WORKFLOW_QUERY = "DELETE FROM WF_WORKFLOW WHERE ID = ? AND TENANT_ID=?";

        public static final String LIST_WORKFLOWS_QUERY = "SELECT ID, WF_NAME, DESCRIPTION, APPROVAL_SUBJECT, " +
                "APPROVAL_DESCRIPTION FROM WF_WORKFLOW WHERE TENANT_ID = ? ORDER BY " +
                "ASC LIMIT? OFFSET?";

        public static final String UPDATE_WORKFLOW_QUERY = "UPDATE WF_WORKFLOW SET WF_NAME=?, DESCRIPTION=?, " +
                "TEMPLATE_ID=?, IMPL_ID=?  WHERE ID=? ";

        public static final String LOAD_WORKFLOW_DEFINITION_FROM_TENANT_ID_AND_NAME =
                "SELECT WF_NAME, DESCRIPTION, APPROVAL_SUBJECT, APPROVAL_DESCRIPTION, TENANT_ID FROM WF_WORKFLOW " +
                        "WHERE TENANT_ID = ? AND WF_NAME = ?";

        public static final String ADD_APPROVAL_LIST_RELATED_TO_USER = "INSERT INTO WF_WORKFLOW_APPROVAL_RELATION (TASK_ID,EVENT_ID,WORKFLOW_ID,APPROVER_TYPE,APPROVER_NAME) VALUES (?,?,?,?,?)";
        public static final String ADD_CURRENT_STEP_FOR_EVENT = "INSERT INTO WF_WORKFLOW_APPROVAL_STATE (EVENT_ID,WORKFLOW_ID, CURRENT_STEP) VALUES (?,?,?)";

        public static final String GET_CURRENT_STEP = "SELECT CURRENT_STEP FROM WF_WORKFLOW_APPROVAL_STATE WHERE EVENT_ID = ? AND WORKFLOW_ID = ?";

        public static final String UPDATE_STATE_OF_REQUEST = "UPDATE WF_WORKFLOW_APPROVAL_STATE SET CURRENT_STEP=? WHERE EVENT_ID = ? AND WORKFLOW_ID = ?";
    }

    public enum ErrorMessage {
        ERROR_REQUIRE_WORKFLOW_DEFINITION_NAME("Workflow Definition name is required"),
        ERROR_REQUIRE_APPROVAL_SUBJECT("Approval subject is required"),
        ERROR_ALREADY_EXIST_WORKFLOW_DEFINITION_NAME("Already a Workflow Definition available with the name: %s.");
        private final String message;

        ErrorMessage(String message) {

            this.message = message;
        }

        public String getMessage() {

            return message;
        }

        @Override
        public String toString() {

            return message;
        }
    }

    public enum EventState{
        PENDING,
        APPROVED
    }

    public static class ParameterName {
        public static final String USER_AND_ROLE_STEP = "UserAndRole" ;
        public static final String REQUEST_ID = "REQUEST ID" ;
    }
}
