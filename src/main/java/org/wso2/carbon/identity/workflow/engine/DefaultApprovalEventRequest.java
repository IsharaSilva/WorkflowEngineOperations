package org.wso2.carbon.identity.workflow.engine;

public interface DefaultApprovalEventRequest {

    /**
     * @param eventId
     * @param status
     * @param approverName
     */
    void handleCallBack(String eventId, String status, String approverName);
}
