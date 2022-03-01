package ndky.paper.kpimgrapp.Response;

import javax.servlet.http.HttpServletResponse;

public class ModifyResponse extends BaseResponse {
    public static final int DUPLICATE = -1;

    int afflicted;
    String description;

    public ModifyResponse(int code, int afflicted, String description) {
        super(code);
        this.afflicted = afflicted;
        this.description = description;
    }

    public ModifyResponse(int afflicted, String description) {
        super(HttpServletResponse.SC_OK);
        this.afflicted = afflicted;
        this.description = description;
    }

    public int getAfflicted() {
        return afflicted;
    }

    public void setAfflicted(int afflicted) {
        this.afflicted = afflicted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
