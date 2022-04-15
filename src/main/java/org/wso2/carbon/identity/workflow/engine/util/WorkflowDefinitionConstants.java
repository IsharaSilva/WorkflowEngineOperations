package org.wso2.carbon.identity.workflow.engine.util;

public class WorkflowDefinitionConstants {

    public static final String WF_NAME_COLUMN = "WF_NAME";
    public static final String WF_DESCRIPTION_COLUMN = "WF_DESCRIPTION";
    public static final String APPROVAL_SUBJECT_COLUMN = "APPROVAL_SUBJECT";
    public static final String APPROVAL_DESCRIPTION_COLUMN = "APPROVAL_DESCRIPTION";

    public enum ErrorMessages {
        ERROR_CODE_SELECT_TEMPLATE_BY_WFID("", "Error occurred while retrieving workflow" +
                " from DB for tenant ID: %d and wfId: %s.");

        private final String code;
        private final String message;

        ErrorMessages(String code, String message) {

            this.code = code;
            this.message = message;
        }

        public String getCode() {

            return code;
        }

        public String getMessage() {

            return message;
        }
        @Override
        public String toString() {

            return code + " : " + message;
        }
        }
    public static class SqlQueries {

        public static final String ADD_WORKFLOW_QUERY = "INSERT INTO WF_WORKFLOW(ID, WF_NAME, DESCRIPTION, APPROVAL_SUBJECT, " +
                "APPROVAL_DESCRIPTION, TENANT_ID) VALUES (?,?, ?, ?, ?, ?)";

        public static final String GET_WORKFLOW = "SELECT WF_WORKFLOW.WF_NAME, WF_WORKFLOW.DESCRIPTION, WF_WORKFLOW" +
                ".TEMPLATE_ID, WF_WORKFLOW.IMPL_ID, WF_WORKFLOW.TENANT_ID FROM WF_WORKFLOW WHERE WF_WORKFLOW.ID = ?";

    }
}
