package ndky.paper.kpimgrapp.Request;

public class StaffInfoRequest extends BaseQueryRequest {
    public StaffInfoRequest() {
    }

    public StaffInfoRequest(String role, Integer page, Integer count, String query) {
        super(role, page, count, query);
    }
}
