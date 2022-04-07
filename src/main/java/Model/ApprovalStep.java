package Model;

import java.util.ArrayList;
import java.util.Map;
import javax.management.relation.Role;

public class ApprovalStep {

    private String name;
    private ArrayList<user> users;
    private ArrayList<Role> roles;
    Map<String, Object> parameterMap;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public ArrayList<user> getUsers() {

        return users;
    }

    public void setUsers(ArrayList<user> users) {

        this.users = users;
    }

    public ArrayList<Role> getRoles() {

        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {

        this.roles = roles;
    }

    public Map<String, Object> getParameterMap() {

        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {

        this.parameterMap = parameterMap;
    }
}
