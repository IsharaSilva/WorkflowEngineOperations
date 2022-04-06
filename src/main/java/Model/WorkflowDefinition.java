package Model;

public class WorkflowDefinition {

    private String wfId;
    private String wfName;
   // private List<ApprovalStep> approvalStep;
    private String wfDescription;
    private String approvalSubject;
    private String approvalDescription;

    public WorkflowDefinition(String wfId, String wfName, String wfDescription, String approvalSubject, String approvalDescription) {

        this.wfId = wfId;
        this.wfName = wfName;
        this.wfDescription = wfDescription;
        //this.approvalStep = approvalSteps;
        this.approvalSubject = approvalSubject;
        this.approvalDescription = approvalDescription;
    }

    public WorkflowDefinition(String wfId) {

        this.wfId = wfId;
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

        return approvalStep;
    }

    public void setApprovalStep(List<ApprovalStep> approvalStep) {

        this.approvalStep = approvalStep;
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
