package org.wso2.carbon.identity.workflow.engine.util;

public class SQLConstants {
    public static final String ADD_WORKFLOW_QUERY = "INSERT INTO WF_WORKFLOW(ID, WF_NAME, DESCRIPTION, TEMPLATE_ID, " +
            "IMPL_ID, TENANT_ID) VALUES (?,?, ?, ?, ?, ?)";

}
