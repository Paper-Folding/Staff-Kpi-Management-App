package ndky.paper.kpimgrapp.Request;

import java.util.List;

public class StaffInfoImportRequest extends BaseQueryRequest {
    private List<StaffInfoRequest> list;

    public StaffInfoImportRequest() {
    }

    public StaffInfoImportRequest(String role, Integer page, Integer count, String query, List<StaffInfoRequest> list) {
        super(role, page, count, query);
        this.list = list;
    }

    public List<StaffInfoRequest> getList() {
        return list;
    }

    public void setList(List<StaffInfoRequest> list) {
        this.list = list;
    }
}
