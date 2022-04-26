package org.wso2.carbon.identity.workflow.engine.util;

public class WorkflowDefinitionConstants {

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
                "APPROVAL_DESCRIPTION FROM WF_WORKFLOW WHERE TENANT_ID = ? ORDER BY "+
                "ASC LIMIT? OFFSET?";
    }
}
