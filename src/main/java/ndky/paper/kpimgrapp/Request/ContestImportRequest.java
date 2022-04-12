package ndky.paper.kpimgrapp.Request;

import java.util.List;

public class ContestImportRequest extends BaseQueryRequest {
    private List<ContestRequest> list;

    public ContestImportRequest() {
    }

    public ContestImportRequest(String role, Integer page, Integer count, String query, List<ContestRequest> list) {
        super(role, page, count, query);
        this.list = list;
    }

    public List<ContestRequest> getList() {
        return list;
    }

    public void setList(List<ContestRequest> list) {
        this.list = list;
    }
}
