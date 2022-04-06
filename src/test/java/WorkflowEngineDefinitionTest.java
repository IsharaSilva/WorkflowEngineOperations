import Model.ApprovalStep;
import Model.WorkflowDefinition;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WorkflowEngineDefinitionTest {

    WorkflowEngine workflowEngineDefinition = new WorkflowEngineDefinition();

    private static final int tenantId = 1234;

    @DataProvider(name = "addWorkflowDefinitionData")
    public Object[][] addWorkflowDefinitionData() {

        WorkflowDefinition workflowDefinition = new WorkflowDefinition();
        workflowDefinition.setWfId("1");
        workflowDefinition.setWfName("UserApproval");
        List<ApprovalStep> approvalSteps = new ArrayList<>();
        workflowDefinition.setApprovalStep(approvalSteps);
        workflowDefinition.setWfDescription("approve by user");
        workflowDefinition.setApprovalSubject("Approval pending");
        workflowDefinition.setApprovalDescription("please approve");

      /*  WorkflowDefinition workflowDefinition2 = new WorkflowDefinition();
        workflowDefinition.setWfId("2");
        workflowDefinition.setWfName("multipleApproval");
        workflowDefinition.setWfDescription("approve by multiple users & roles");
        workflowDefinition.setApprovalSubject("Approval pending");
        workflowDefinition.setApprovalDescription("please approve");*/

        return new Object[][]{{workflowDefinition, tenantId}};
    }

    @Test(dataProvider = "addWorkflowDefinitionData")
    public void addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        /*String result = workflowEngineDefinition.addDefinition((WorkflowDefinition) workflowDefinition, tenantId);
        assertEquals("1", result);*/

        workflowEngineDefinition.addDefinition(workflowDefinition, tenantId);
        WorkflowDefinition newDefinition = workflowEngineDefinition.getDefinition(workflowDefinition.getWfId(), tenantId);

        assertEquals((workflowDefinition).getWfId(), newDefinition.getWfId());
        assertEquals((workflowDefinition).getWfName(), newDefinition.getWfName());
    }

    @DataProvider(name = "listWorkflowDefinitionsDataProvider")
    public Object[][] listWorkflowDefinitionsData() {

        WorkflowDefinition workflowDefinition3 = new WorkflowDefinition();
        workflowDefinition3.setWfId("3");
        workflowDefinition3.setWfName("UserApproval");
        List<ApprovalStep> approvalSteps = new ArrayList<>();
        workflowDefinition3.setApprovalStep(approvalSteps);
        workflowDefinition3.setWfDescription("approve by user");
        workflowDefinition3.setApprovalSubject("Approval pending");
        workflowDefinition3.setApprovalDescription("please approve");

        WorkflowDefinition workflowDefinition4 = new WorkflowDefinition();
        workflowDefinition4.setWfId("4");
        workflowDefinition4.setWfName("UserApproval");
        List<ApprovalStep> approvalSteps2 = new ArrayList<>();
        workflowDefinition4.setApprovalStep(approvalSteps2);
        workflowDefinition4.setWfDescription("approve by user");
        workflowDefinition4.setApprovalSubject("Approval pending");
        workflowDefinition4.setApprovalDescription("please approve");

        return new Object[][]{
                {
                        Arrays.asList(
                                workflowDefinition3,
                                workflowDefinition4
                        ),
                        tenantId
                }
        };
    }

    @Test(dataProvider = "listWorkflowDefinitionsDataProvider")
    public void listDefinitions(List<WorkflowDefinition> workflowDefinitions, String searchQuery, int limit, int offSet, int tenantId) {

        List<WorkflowDefinition> workflowDefinitions1 = Arrays.asList(new WorkflowDefinition[5]);
        int i = 0;
        for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
            workflowDefinitions1.set(i, workflowDefinition);
            i += 1;
        }
        List<WorkflowDefinition> workflowDefinitionList = workflowEngineDefinition.listDefinitions(searchQuery, limit, offSet, tenantId);
        assertTrue(workflowDefinitionList != null && workflowDefinitionList.size() != 0,
                "Success.");
    }

    @DataProvider(name = "updateWorkflowDefinitionsDataProvider")
    public Object[][] updateWorkflowDefinitionsData() {

        WorkflowDefinition workflowDefinition5 = new WorkflowDefinition();
        workflowDefinition5.setWfId("5");
        workflowDefinition5.setWfName("UserApproval");
        List<ApprovalStep> approvalSteps = new ArrayList<>();
        workflowDefinition5.setApprovalStep(approvalSteps);
        workflowDefinition5.setWfDescription("approve by user");
        workflowDefinition5.setApprovalSubject("Approval pending");
        workflowDefinition5.setApprovalDescription("please approve");

        WorkflowDefinition workflowDefinition6 = new WorkflowDefinition();
        workflowDefinition6.setWfId("6");
        workflowDefinition6.setWfName("RoleApproval");
        List<ApprovalStep> approvalSteps2 = new ArrayList<>();
        workflowDefinition6.setApprovalStep(approvalSteps2);
        workflowDefinition6.setWfDescription("approve by user");
        workflowDefinition6.setApprovalSubject("Approval pending");
        workflowDefinition6.setApprovalDescription("please approve");
        return new Object[][]{
                {
                        workflowDefinition5,
                        tenantId
                },
                {
                        workflowDefinition6,
                        tenantId
                }
        };
    }

    @Test(dataProvider = "updateWorkflowDefinitionsDataProvider")
    public void updateDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        WorkflowDefinition workflowDef = workflowDefinition;
        String oldWfId = workflowDef.getWfId();
        if (oldWfId.equals("5")) {
            workflowDef.setWfId("");
        } else if (oldWfId.equals("6")) {
            workflowDef.setWfId("7");
        } else {
            workflowDef.setWfId("updated");
        }
        //assertNotNull(workflowEngine.getDefinition(workflowDef.getWfId(), tenantId), "");

    }

}
