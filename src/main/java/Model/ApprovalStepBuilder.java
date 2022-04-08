package Model;

import java.util.ArrayList;
import java.util.Map;
import javax.management.relation.Role;

public interface ApprovalStepBuilder {

    ApprovalStepBuilder addUsers(ArrayList(users));

    ApprovalStepBuilder addRoles();

    ApprovalStepBuilder addParameter(String key, Object value);

    ApprovalStep build();

    public static class Builder{

            private String name;
            private ArrayList<User> users;
            private ArrayList<Role> roles;
            Map<String, Object> parameterMap;

            public ApprovalStepBuilder addUsers(ArrayList<User>) {

                return this;
            }

            public ApprovalStepBuilder addRoles() {

                return null;
            }


            public ApprovalStepBuilder addParameter(String key, Object value) {

                return null;
            }

            public ApprovalStep build() {

                return null;
            }
        }

}
