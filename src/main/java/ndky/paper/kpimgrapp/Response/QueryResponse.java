package ndky.paper.kpimgrapp.Response;

public class QueryResponse extends BaseResponse {
    private Object result;

    private long total;

    public QueryResponse(Object result, long total) {
        this.result = result;
        this.total = total;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
