package ndky.paper.kpimgrapp.Request;

import ndky.paper.kpimgrapp.Models.Role;

public class RoleRequest extends BaseQueryRequest {

    private Role query;

    public RoleRequest() {
    }

    public RoleRequest(Integer page, Integer count, Role query, String queryStr) {
        super(page, count, queryStr);
        this.query = query;
    }

    public Role getQuery() {
        return query == null ? new Role() : query;
    }

    public void setQuery(Role query) {
        this.query = query;
    }
}
