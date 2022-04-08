package Model;

import java.util.ArrayList;
import java.util.Map;
import javax.management.relation.Role;

public class ApprovalStep implements ApprovalStepBuilder {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Role> roles;
    Map<String, Object> parameterMap;

    @Override
    public ApprovalStepBuilder addUsers() {

        return this;
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
