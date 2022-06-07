package org.wso2.carbon.identity.workflow.engine;

public interface DefaultApprovalEvent {

    //void getApprovalData(String eventId, String status);

    void updateApproverDetails(String eventId, String status, String approverName);
}
