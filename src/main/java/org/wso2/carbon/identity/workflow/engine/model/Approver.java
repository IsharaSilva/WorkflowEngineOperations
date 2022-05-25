package org.wso2.carbon.identity.workflow.engine.model;

public class Approver {
    String approverType ;
    String approverName ;

    public String getApproverType() {

        return approverType;
    }

    public void setApproverType(String approverType) {

        this.approverType = approverType;
    }

    public String getApproverName() {

        return approverName;
    }

    public void setApproverName(String approverName) {

        this.approverName = approverName;
    }
}
