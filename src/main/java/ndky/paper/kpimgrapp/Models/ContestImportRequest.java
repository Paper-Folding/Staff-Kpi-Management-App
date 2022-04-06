package ndky.paper.kpimgrapp.Models;

import ndky.paper.kpimgrapp.Request.ContestRequest;

import java.util.List;

public class ContestImportRequest {
    private List<ContestRequest> list;

    public ContestImportRequest() {
    }

    public ContestImportRequest(List<ContestRequest> list) {
        this.list = list;
    }

    public List<ContestRequest> getList() {
        return list;
    }

    public void setList(List<ContestRequest> list) {
        this.list = list;
    }
}
