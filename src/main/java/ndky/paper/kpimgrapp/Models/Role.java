package ndky.paper.kpimgrapp.Models;

import java.util.List;

public class Role {
    private Long id;

    private String name;

    private String description;

    private List<RoleScope> roleScopes;

    public Role() {
    }

    public Role(Long id, String name, String description, List<RoleScope> roleScopes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roleScopes = roleScopes;
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
}
