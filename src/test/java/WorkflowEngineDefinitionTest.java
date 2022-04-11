import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.workflow.engine.WorkflowEngine;
import org.wso2.carbon.identity.workflow.engine.WorkflowEngineDefinition;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class WorkflowEngineDefinitionTest {

    WorkflowEngine workflowEngineDefinition = new WorkflowEngineDefinition();

    private static final int tenantId = 1234;

    @DataProvider(name = "addWorkflowDefinitionData")
    public Object[][] addWorkflowDefinitionData() {

        WorkflowDefinition workflowDefinition1 = new WorkflowDefinition("1", "WfName 1", "Wf Description 1", "Approval subject 1", " Approve Description 1");
        WorkflowDefinition workflowDefinition2 = new WorkflowDefinition("2", "", "Wf Description 2", "Approval subject 2", " Approve Description 2");

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
    public void testAddDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

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
                                workflowDefinition1, workflowDefinition2
                        ),
                        "searchQuery1",
                        0,
                        0,
                        tenantId
                },
                {
                        Arrays.asList(
                                workflowDefinition1, workflowDefinition2
                        ),
                        "WfName",
                        1,
                        0,
                        tenantId
                },
                {
                        Arrays.asList(
                                workflowDefinition1, workflowDefinition2
                        ),
                        "WfName",
                        1,
                        1,
                        tenantId
                }
        };
    }

    @Test(dataProvider = "listWorkflowDefinitionsDataProvider", dependsOnMethods = "testAddDefinition")
    public void testListDefinitions(List<WorkflowDefinition> workflowDefinitions, String searchQuery, int limit, int offSet, int tenantId) {

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

        WorkflowDefinition newWorkflowDefinition1 = new WorkflowDefinition("WfName 1 updated", "WfDescription 1 updated", "Approval subject1 updated", " Approval Description1 updated");
        WorkflowDefinition newWorkflowDefinition2 = new WorkflowDefinition("WfName 2 updated", "WfDescription 2", "Approval subject2 updated", " Approval Description2 updated");

        return new Object[][]{
                {"1", newWorkflowDefinition1, tenantId},
                {"2", newWorkflowDefinition2, tenantId}
        };
    }

    @Test(dataProvider = "updateWorkflowDefinitionsDataProvider", dependsOnMethods = {"testListDefinitions"})
    public void testUpdateDefinition(String wfId, WorkflowDefinition newWorkflowDefinition, int tenantId) {

        workflowEngineDefinition.updateDefinition(wfId, newWorkflowDefinition, tenantId);
        WorkflowDefinition updatedWorkflowDefinition = workflowEngineDefinition.getDefinition(wfId, tenantId);

        assertEquals(updatedWorkflowDefinition.getWfName(),newWorkflowDefinition.getWfName());
        assertEquals(updatedWorkflowDefinition.getWfDescription(),newWorkflowDefinition.getWfDescription());
    }

    @Test(dataProvider = "addWorkflowDefinitionData", dependsOnMethods = {"testUpdateDefinition"})
    public void testDeleteDefinition(String wfId, int tenantId) throws WorkflowEngineException {

        workflowEngineDefinition.deleteDefinition(wfId, tenantId);
        WorkflowDefinition oldWorkflowDefinition=workflowEngineDefinition.getDefinition(wfId, tenantId);
        assertEquals( aaa,"WorkflowId is Invalid");
    }
}
