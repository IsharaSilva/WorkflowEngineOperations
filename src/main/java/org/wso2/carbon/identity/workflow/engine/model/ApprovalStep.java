package org.wso2.carbon.identity.workflow.engine.model;

import java.util.ArrayList;
import java.util.Map;
import javax.management.relation.Role;

public class ApprovalStep {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Role> roles;
    Map<String, Object> parameterMap;

}
