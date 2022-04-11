package org.wso2.carbon.identity.workflow.engine.model;

import java.util.ArrayList;
import java.util.Map;
import javax.management.relation.Role;

public interface ApprovalStepBuilder {

    ApprovalStepBuilder addUsers();

    ApprovalStepBuilder addRoles();

    ApprovalStepBuilder addParameter(String key, Object value);

    ApprovalStep build();

    public static class ApprovalStep {

        String name;
        ArrayList<User> users;
        ArrayList<Role> roles;
        Map<String, Object> parameterMap;

        public ApprovalStep Name(String name) {

            this.name = name;
            return this;
        }

        public ApprovalStep addUsers() {

            return this;
        }

        public ApprovalStep addRoles() {

            return null;
        }

        public ApprovalStep addParameter(String key, Object value) {

            return null;
        }

        public ApprovalStepBuilder build() {

            return new ApprovalStepBuilderImpl(this);
        }
    }

}
