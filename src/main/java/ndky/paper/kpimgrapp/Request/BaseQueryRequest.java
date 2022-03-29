package ndky.paper.kpimgrapp.Request;

/**
 * Base query request
 * contains base request params: current request page and per page amount (count here)
 */
public class BaseQueryRequest {
    // this is the role name that tells backend currently logged user is using which role for request
    private String role;

    // page is start from 1
    private Integer page;

    // count decides how many records should be returned
    private Integer count;

    // all fields ambiguous match
    private String query;

    public BaseQueryRequest() {
    }

    public BaseQueryRequest(String role, Integer page, Integer count, String query) {
        this.role = role;
        this.page = page;
        this.count = count;
        this.query = query;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPage() {
        return page == null ? 1 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count == null ? 10 : count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStartPos() {
        return (this.getPage() - 1) * this.getCount();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
