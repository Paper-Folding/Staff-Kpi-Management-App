package ndky.paper.kpimgrapp.Request;

public class UserPermissionRequest {
    private Long authorizationId, roleId;
    private String roleName, username, tableName, operationName;

    public UserPermissionRequest() {
    }

    public UserPermissionRequest(Long authorizationId, Long roleId, String roleName, String username, String tableName, String operationName) {
        this.authorizationId = authorizationId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.username = username;
        this.tableName = tableName;
        this.operationName = operationName;
    }

    public Long getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(Long authorizationId) {
        this.authorizationId = authorizationId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
