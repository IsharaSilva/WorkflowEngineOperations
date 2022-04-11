package org.wso2.carbon.identity.workflow.engine.model;

import java.util.ArrayList;
import java.util.Map;
import javax.management.relation.Role;

public class ApprovalStepBuilderImpl implements ApprovalStepBuilder{
    private final String name;
    private final ArrayList<User> users;
    private final ArrayList<Role> roles;
    private  final Map<String, Object> parameterMap;

    public ApprovalStepBuilderImpl(ApprovalStepBuilder.ApprovalStep approvalStep) {

        this.name = approvalStep.name;
        this.users=approvalStep.users;
        this.roles=approvalStep.roles;
        this.parameterMap=approvalStep.parameterMap;
    }

    public String getName() {

        return name;
    }

    public ArrayList<User> getUsers() {

        return users;
    }

    public ArrayList<Role> getRoles() {

        return roles;
    }

    public Map<String, Object> getParameterMap() {

        return parameterMap;
    }

    @Override
    public ApprovalStepBuilder addUsers() {

        return null;
    }

    @Override
    public ApprovalStepBuilder addRoles() {

        return null;
    }

    @Override
    public ApprovalStepBuilder addParameter(String key, Object value) {

        return null;
    }

    @Override
    public ApprovalStep build() {

        return null;
    }
}
