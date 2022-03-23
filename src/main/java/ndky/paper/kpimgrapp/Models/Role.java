package ndky.paper.kpimgrapp.Models;

import java.util.List;

public class Role {
    private Long id;

    private String name;

    private String description;

    private List<RoleScope> roleScopes;

    // this is the role name that tells backend currently logged user is using which role
    private String role;

    public Role() {
    }

    public Role(Long id, String name, String description, List<RoleScope> roleScopes, String role) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roleScopes = roleScopes;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoleScope> getRoleScopes() {
        return roleScopes;
    }

    public void setRoleScopes(List<RoleScope> roleScopes) {
        this.roleScopes = roleScopes;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
