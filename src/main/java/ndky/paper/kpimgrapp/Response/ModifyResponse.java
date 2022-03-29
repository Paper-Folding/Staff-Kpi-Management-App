package ndky.paper.kpimgrapp.Response;

import javax.servlet.http.HttpServletResponse;

public class ModifyResponse extends BaseResponse {
    public static final int DUPLICATE = -1;

    int afflicted;
    String message;

    public ModifyResponse(int code, int afflicted, String message) {
        super(code);
        this.afflicted = afflicted;
        this.message = message;
    }

    public ModifyResponse(int afflicted, String message) {
        super(HttpServletResponse.SC_OK);
        this.afflicted = afflicted;
        this.message = message;
    }

    public ModifyResponse(int afflicted) {
        super(HttpServletResponse.SC_OK);
        this.afflicted = afflicted;
        this.message = "done";
    }

    public int getAfflicted() {
        return afflicted;
    }

    public void setAfflicted(int afflicted) {
        this.afflicted = afflicted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
