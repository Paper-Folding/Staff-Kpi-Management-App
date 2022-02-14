package ndky.paper.kpimgrapp.Response;

public class BaseResponse {
    int code;

    public BaseResponse() {
    }

    public BaseResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
