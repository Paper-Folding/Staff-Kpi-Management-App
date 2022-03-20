package ndky.paper.kpimgrapp.Models;

/**
 * user permission model, which is used by front-end to collect necessary permission data
 * e.g. if front end wants to know if the user has the permission to delete something, then this function is necessary
 */
public class UserPermission {
    private String username;
    private Long objectId;
    // targeting table
    private String tableName;
    // targeting column, select operation always use this to store fields of table, most commonly, it is '*' for all fields of targeting table
    private String columnName;
    private Long operationId;
    // operation name, e.g. insert/delete/select...
    private String operationName;

    public UserPermission() {
    }

    public UserPermission(String username, Long objectId, String tableName, String columnName, Long operationId, String operationName) {
        this.username = username;
        this.objectId = objectId;
        this.tableName = tableName;
        this.columnName = columnName;
        this.operationId = operationId;
        this.operationName = operationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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
}
