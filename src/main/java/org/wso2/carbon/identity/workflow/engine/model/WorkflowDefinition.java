package org.wso2.carbon.identity.workflow.engine.model;

import java.util.List;

public class WorkflowDefinition {

    private String wfId;
    private String wfName;
    private String wfDescription;
    private List<ApprovalStep> approvalSteps;
    private String approvalSubject;
    private String approvalDescription;

    public WorkflowDefinition(String wfId, String wfName, String wfDescription, String approvalSubject, String approvalDescription) {

        this.wfId = wfId;
        this.wfName = wfName;
        this.wfDescription = wfDescription;
        this.approvalSubject = approvalSubject;
        this.approvalDescription = approvalDescription;
    }

    public WorkflowDefinition(String wfName, String wfDescription, String approvalSubject, String approvalDescription) {

        this.wfName = wfName;
        this.wfDescription = wfDescription;
        this.approvalSubject = approvalSubject;
        this.approvalDescription = approvalDescription;
    }

    public WorkflowDefinition() {

    }

    public String getWfId() {

        return wfId;
    }

    public void setWfId(String wfId) {

        this.wfId = wfId;
    }

    public String getWfDescription() {

        return wfDescription;
    }

    public void setWfDescription(String wfDescription) {

        this.wfDescription = wfDescription;
    }

    public String getWfName() {

        return wfName;
    }

    public void setWfName(String wfName) {

        this.wfName = wfName;
    }

    /*public List<ApprovalStep> getApprovalStep() {

        return approvalSteps;
    }

    public void setApprovalStep(List<ApprovalStep> approvalSteps) {

        this.approvalSteps = approvalSteps;
    }*/

    public String getApprovalSubject() {

        return approvalSubject;
    }

    public void setApprovalSubject(String approvalSubject) {

        this.approvalSubject = approvalSubject;
    }

    public String getApprovalDescription() {

        return approvalDescription;
    }

    public void setApprovalDescription(String approvalDescription) {

        this.approvalDescription = approvalDescription;
    }
    
}
