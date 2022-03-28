package ndky.paper.kpimgrapp.Response;

import javax.servlet.http.HttpServletResponse;

/**
 * response when forbidden
 */
public class AccessDeniedResponse extends BaseResponse {
    private String error, message, path;

    public AccessDeniedResponse(String path) {
        super(HttpServletResponse.SC_FORBIDDEN);
        this.path = path;
    }

    public String getError() {
        return "Forbidden";
    }

    public String getMessage() {
        return "Forbidden: Not enough permission to access " + path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
