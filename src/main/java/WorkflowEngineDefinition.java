import Model.WorkflowDefinition;

import java.util.ArrayList;
import java.util.List;

public class WorkflowEngineDefinition implements WorkflowEngine {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        String wfId = workflowDefinition.getWfId();
        String wfName = workflowDefinition.getWfName();
        String approvalSubject = workflowDefinition.getApprovalSubject();
        if (wfName == null) {
            return "Workflow name cannot empty";
        } else if (approvalSubject == null) {
            return "Approval subject can not empty";
        } else {
            return wfId;
        }

    }

    @Override
    public WorkflowDefinition getDefinition(String wfId, int tenantId) {

        //TODO implementation
        return new WorkflowDefinition();
    }

    @Override
    public List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId) {

        //TODO implementation
        return new ArrayList<>();
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) {

        //TODO implementation
    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

        //TODO implementation
    }
}
