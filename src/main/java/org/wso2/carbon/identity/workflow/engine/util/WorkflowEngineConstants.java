package org.wso2.carbon.identity.workflow.engine.util;

public class WorkflowEngineConstants {

    public static final String WF_NAME_COLUMN = "WF_NAME";
    public static final String WF_DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String APPROVAL_SUBJECT_COLUMN = "APPROVAL_SUBJECT";
    public static final String APPROVAL_DESCRIPTION_COLUMN = "APPROVAL_DESCRIPTION";

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

        public static final String LOAD_WORKFLOW_DEFINITION_FROM_TENANTID_AND_NAME =
                "SELECT WF_NAME, DESCRIPTION, APPROVAL_SUBJECT, APPROVAL_DESCRIPTION, TENANT_ID FROM WF_WORKFLOW " +
                        "WHERE TENANT_ID = ? AND WF_NAME = ?";

        public static final String ADD_WORKFLOW_REQUEST_RELATIONSHIP = "INSERT INTO WF_WORKFLOW_REQUEST_RELATION " +
                "(RELATIONSHIP_ID, WORKFLOW_ID, REQUEST_ID, UPDATED_AT, STATUS, TENANT_ID) VALUES (?, ?, ?, ?, ?, ?)";

        public static final String GET_ASSOCIATIONS_FOR_EVENT = "SELECT WF_WORKFLOW_ASSOCIATION.WORKFLOW_ID, " +
                "WF_WORKFLOW_ASSOCIATION.ID,WF_WORKFLOW_ASSOCIATION.ASSOC_NAME,WF_WORKFLOW_ASSOCIATION.ASSOC_CONDITION " +
                "FROM WF_WORKFLOW, WF_WORKFLOW_ASSOCIATION WHERE WF_WORKFLOW_ASSOCIATION.EVENT_ID = ? AND " +
                "WF_WORKFLOW_ASSOCIATION .WORKFLOW_ID = WF_WORKFLOW.ID AND WF_WORKFLOW.TENANT_ID = ? AND " +
                "WF_WORKFLOW_ASSOCIATION.IS_ENABLED = '1'";

        public static final String ADD_APPROVAL_LIST_RELATED_TO_USER = "INSERT INTO WF_WORKFLOW_APPROVAL_RELATION " +
                "(WORKFLOW_ID, REQUEST_ID, APPROVER_TYPE, APPROVER_NAME,  TENANT_ID) VALUES (?, ?, ?, ?,?) ";

    }

    public enum ErrorMessage {
        ERROR_REQUIRE_WORKFLOW_DEFINITION_NAME("Workflow Definition name is required"),
        ERROR_REQUIRE_APPROVAL_SUBJECT("SApproval subject is required"),
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
}
