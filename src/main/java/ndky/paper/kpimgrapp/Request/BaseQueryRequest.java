package ndky.paper.kpimgrapp.Request;

/**
 * Base query request
 * contains base request params: current request page and per page amount (count here)
 */
public class BaseQueryRequest {
    // page is start from 1
    private Integer page;

    // count decides how many records should be returned
    private Integer count;

    public BaseQueryRequest() {
    }

    public BaseQueryRequest(Integer page, Integer count) {
        this.page = page;
        this.count = count;
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
}
