package ndky.paper.kpimgrapp.Response;

import javax.servlet.http.HttpServletResponse;

public class ErrorResponse extends BaseResponse {
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        super(HttpServletResponse.SC_FORBIDDEN);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
