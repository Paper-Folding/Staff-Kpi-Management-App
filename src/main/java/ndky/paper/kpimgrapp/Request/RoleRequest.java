package ndky.paper.kpimgrapp.Request;

import ndky.paper.kpimgrapp.Models.RoleScope;

import java.util.List;

public class RoleRequest extends BaseQueryRequest {
    private Long id;

    private String name;

    private String description;

    private String expiration;

    // creator authentication id
    private Long creatorId;

    // creator authentication username
    private String username;

    private List<RoleScope> roleScopes;

    public RoleRequest() {
    }

    public RoleRequest(Long id) {
        this.id = id;
    }

    public RoleRequest(String role, Integer page, Integer count, String query, Long id, String name, String description, String expiration, Long creatorId, String username, List<RoleScope> roleScopes) {
        super(role, page, count, query);
        this.id = id;
        this.name = name;
        this.description = description;
        this.expiration = expiration;
        this.creatorId = creatorId;
        this.username = username;
        this.roleScopes = roleScopes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RoleScope> getRoleScopes() {
        return roleScopes;
    }

    public void setRoleScopes(List<RoleScope> roleScopes) {
        this.roleScopes = roleScopes;
    }
}
