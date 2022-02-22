package ndky.paper.kpimgrapp.Models;

import java.util.Objects;

public class RoleScope {
    private long operationId;
    private long objectId;

    public RoleScope() {
    }

    public RoleScope(long operationId, long objectId) {
        this.operationId = operationId;
        this.objectId = objectId;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleScope roleScope = (RoleScope) o;
        return operationId == roleScope.operationId && objectId == roleScope.objectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, objectId);
    }
}
