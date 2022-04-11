package org.wso2.carbon.identity.workflow.engine.dao.impl;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowDefinitionDAO;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineException;
import org.wso2.carbon.identity.workflow.engine.model.WorkflowDefinition;
import org.wso2.carbon.identity.workflow.engine.util.SQLConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WorkflowDefinitionDAOImpl implements WorkflowDefinitionDAO {

    @Override
    public String addDefinition(WorkflowDefinition workflowDefinition, int tenantId) {

        {
            try
                (Connection connection = getDBConnection()){
            PreparedStatement prepStmt = null;
            String query = SQLConstants.ADD_WORKFLOW_QUERY;
                prepStmt = connection.prepareStatement(query);
                prepStmt.setString(1, workflowDefinition.getWfId());
                prepStmt.setString(2, workflowDefinition.getWfName());
                prepStmt.setString(3, workflowDefinition.getWfDescription());
                prepStmt.setString(4, workflowDefinition.getApprovalSubject());
                prepStmt.setString(5, workflowDefinition.getApprovalDescription());
                prepStmt.setInt(6, tenantId);
                prepStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

           
        }
        return null;
    }

    @Override
    public Optional<WorkflowDefinition> getDefinition(String wfId, int tenantId) {

        return Optional.empty();
    }

    @Override
    public List<WorkflowDefinition> listDefinitions(String searchQuery, int limit, int offSet, int tenantId) {

        return null;
    }

    @Override
    public void deleteDefinition(String wfId, int tenantId) throws WorkflowEngineException {

    }

    @Override
    public void updateDefinition(String wfId, WorkflowDefinition updatedWorkflowDefinition, int tenantId) {

    }
}
