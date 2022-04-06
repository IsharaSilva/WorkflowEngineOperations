import Model.WorkflowDefinition;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class WorkflowEngineDefinitionTest {

    WorkflowEngine workflowEngineDefinition = new WorkflowEngineDefinition();

    private static final int tenantId = 1234;

    @DataProvider(name = "addWorkflowDefinitionData")
    public Object[][] addWorkflowDefinitionData() {

        WorkflowDefinition workflowDefinition1 = new WorkflowDefinition("1", "WfName 1", "Wf Description 1", "Approval subject 1", " Approve Description 1");
        WorkflowDefinition workflowDefinition2 = new WorkflowDefinition("2", "WfName 2", "Wf Description 2", "Approval subject 2", " Approve Description 2");

        return new Object[][]{
                {
                        workflowDefinition1,
                        tenantId
                },
                {
                        workflowDefinition2,
                        tenantId
                }
        };
    }

    @Test(dataProvider = "addWorkflowDefinitionData")
    public void addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        /*String result = workflowEngineDefinition.addDefinition((WorkflowDefinition) workflowDefinition, tenantId);
        assertEquals("1", result);*/

        workflowEngineDefinition.addDefinition(workflowDefinition, tenantId);
        WorkflowDefinition addedDefinition = workflowEngineDefinition.getDefinition(workflowDefinition.getWfId(), tenantId);

        assertEquals(addedDefinition.getWfId(), workflowDefinition.getWfId());
        assertEquals(addedDefinition.getWfName(), workflowDefinition.getWfName());
    }

    @DataProvider(name = "listWorkflowDefinitionsDataProvider")
    public Object[][] listWorkflowDefinitionsData() {

        WorkflowDefinition workflowDefinition1 = new WorkflowDefinition("1", "WfName 1", "Wf Description 1", "Approval subject 1", " Approve Description 1");
        WorkflowDefinition workflowDefinition2 = new WorkflowDefinition("2", "WfName 2", "Wf Description 2", "Approval subject 2", " Approve Description 2");

        return new Object[][]{
                {
                        Arrays.asList(
                                workflowDefinition1,
                                workflowDefinition2
                        ),
                        "searchQuery1",
                        0,
                        0,
                        tenantId
                }
        };
    }

    @Test(dataProvider = "listWorkflowDefinitionsDataProvider")
    public void listDefinitions(List<WorkflowDefinition> workflowDefinitions, String searchQuery, int limit, int offSet, int tenantId) {

        List<WorkflowDefinition> definitionList = Arrays.asList(new WorkflowDefinition[3]);
        int i = 0;
        for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
            definitionList.set(i, workflowDefinition);
            i += 1;
        }
        List<WorkflowDefinition> workflowDefinitionList = workflowEngineDefinition.listDefinitions(searchQuery, limit, offSet, tenantId);

        assertEquals(workflowDefinitionList.size(), workflowDefinitions.size());
        //assertEquals("1", definitionList.get(0));
    }

    @DataProvider(name = "updateWorkflowDefinitionsDataProvider")
    public Object[][] updateWorkflowDefinitionsData() {

        WorkflowDefinition workflowDefinition1 = new WorkflowDefinition("1", "WfName 1", "Wf Description 1", "Approval subject 1", " Approve Description 1");
        WorkflowDefinition workflowDefinition2 = new WorkflowDefinition("2", "WfName 2", "Wf Description 2", "Approval subject 2", " Approve Description 2");
        WorkflowDefinition newWorkflowDefinition1 = new WorkflowDefinition("1 updated", "WfName 1 updated", "Wf Description 1 updated", "Approval subject 1 updated", " Approve Description 1 updated");
        WorkflowDefinition newWorkflowDefinition2 = new WorkflowDefinition("2 updated", "WfName 2 updated", "Wf Description 2", "Approval subject 2 updated", " Approve Description 2 updated");

        return new Object[][]{
                {
                        workflowDefinition1,
                        newWorkflowDefinition1,
                        tenantId
                },
                {

                        workflowDefinition2,
                        newWorkflowDefinition2,
                        tenantId
                }
        };
    }

    @Test(dataProvider = "updateWorkflowDefinitionsDataProvider")
    public void updateDefinition(WorkflowDefinition oldWorkflowDefinition, WorkflowDefinition newWorkflowDefinition, int tenantId) {

        WorkflowDefinition workflowDef = oldWorkflowDefinition;
        String oldWfId = workflowDef.getWfId();
        WorkflowDefinition updatedWorkflowDefinition = workflowEngineDefinition.updateDefinition(oldWfId, newWorkflowDefinition, tenantId);
        assertEquals((newWorkflowDefinition).getWfId(), updatedWorkflowDefinition.getWfId());
        assertEquals((newWorkflowDefinition).getWfName(), updatedWorkflowDefinition.getWfName());
    }

    @DataProvider(name = "deleteWorkflowDefinitionData")
    public Object[][] deleteWorkflowDefinitionData() {

        WorkflowDefinition workflowDefinition1 = new WorkflowDefinition("1", "WfName 1", "Wf Description 1", "Approval subject 1", " Approve Description 1");
        WorkflowDefinition workflowDefinition2 = new WorkflowDefinition("2", "WfName 2", "Wf Description 2", "Approval subject 2", " Approve Description 2");

        return new Object[][]{
                {
                        workflowDefinition1,
                        tenantId
                },
                {
                        workflowDefinition2,
                        tenantId
                }
        };
    }

    @Test(dataProvider = "deleteWorkflowDefinitionData")
    public void deleteDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        WorkflowDefinition workflowDefinitionResult = workflowEngineDefinition.getDefinition(workflowDefinition.getWfId(), tenantId);
        assertEquals(workflowDefinitionResult.getWfId(), workflowDefinition.getWfId());
        workflowEngineDefinition.deleteDefinition(workflowDefinitionResult.getWfId(), tenantId);
    }
}
