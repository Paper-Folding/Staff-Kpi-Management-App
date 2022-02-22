package ndky.paper.kpimgrapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Role {
    private long id;

    private String name;

    @JsonIgnore
    private List<RoleScope> role_scope;

    public Role() {
    }

    public Role(long id, String name, List<RoleScope> role_scope) {
        this.id = id;
        this.name = name;
        this.role_scope = role_scope;
    }

    public long getId() {
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

    public List<RoleScope> getRole_scope() {
        return role_scope;
    }

    public void setRole_scope(List<RoleScope> role_scope) {
        this.role_scope = role_scope;
    }
}
