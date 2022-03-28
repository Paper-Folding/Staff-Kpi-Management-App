package ndky.paper.kpimgrapp.Request;

public class UserPermissionRequest {
    private Long authenticationId;
    private String username;

    private Long roleId;
    private String roleName;

    private Long operationId;
    private String operationName;
    private String tableName;

    public UserPermissionRequest() {
    }

    public UserPermissionRequest(Long authenticationId, String username, Long roleId, String roleName, Long operationId, String operationName, String tableName) {
        this.authenticationId = authenticationId;
        this.username = username;
        this.roleId = roleId;
        this.roleName = roleName;
        this.operationId = operationId;
        this.operationName = operationName;
        this.tableName = tableName;
    }

    public Long getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(Long authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
