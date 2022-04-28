package org.wso2.carbon.identity.workflow.engine.dao;

import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;

import java.util.List;
import java.util.Optional;

public interface WorkflowDefinitionDAO {

    /**
     * Add a new workflow definition.
     *
     * @param workflowDefinition workflowDefinition object from workflowDefinition class.
     * @param tenantId           Tenant ID.
     * @return String wfId workflow ID.
     */
    String addDefinition(WorkflowDefinition workflowDefinition, int tenantId);

    /**
     * Retrieve a workflow definition.
     *
     * @param wfId     ID of workflow to retrieve.
     * @param tenantId Tenant ID.
     * @return workflowDefinition object from WorkflowDefinition class.
     */
    Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId);

    /**
     * List workflow definitions.
     *
     * @param searchQuery search workflows using searchQuery.
     * @param limit       page limit.
     * @param offSet      start number of list of every page.
     * @param tenantId    Tenant ID.
     * @return List<Workflow> of Workflow class.
     */
    List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId);

    /**
     * Delete a workflow Definition.
     *
     * @param wfId     ID of workflow to remove.
     * @param tenantId Tenant ID.
     */
    void deleteDefinition(String wfId, int tenantId);

    /**
     * Update an existing workflow Definition.
     *
     * @param wfId                      ID of workflow to update.
     * @param updatedWorkflowDefinition updatedWorkflowDefinition object from Workflow class.
     * @param tenantId                  Tenant ID.
     */
    void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId);

    /**
     * Checks whether the Workflow Definition already exists with the name.
     *
     * @param wfName Name of the Workflow Definition.
     * @param tenantId Tenant ID.
     * @return Existence of the function library.
     */
    boolean isWorkflowDefinitionExits(String wfName, int tenantId);
}


