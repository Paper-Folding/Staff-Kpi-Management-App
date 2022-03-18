package ndky.paper.kpimgrapp.Models;

/**
 * operation object model is dict_operation_object table
 */
public class OperationObject {
    private Long id;
    private String tableName, columnName;

    public OperationObject() {
    }

    public OperationObject(Long id, String tableName, String columnName) {
        this.id = id;
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
