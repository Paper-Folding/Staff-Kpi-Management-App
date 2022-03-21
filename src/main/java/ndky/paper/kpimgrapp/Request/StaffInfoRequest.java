package ndky.paper.kpimgrapp.Request;

import ndky.paper.kpimgrapp.Models.StaffInfo;

public class StaffInfoRequest extends BaseQueryRequest {

    private StaffInfo query;

    public StaffInfoRequest() {
    }

    public StaffInfoRequest(Integer page, Integer count, StaffInfo query, String queryStr) {
        super(page, count, queryStr);
        this.query = query;
    }

    public StaffInfo getQuery() {
        return query == null ? new StaffInfo() : query;
    }

    public void setQuery(StaffInfo query) {
        this.query = query;
    }
}
