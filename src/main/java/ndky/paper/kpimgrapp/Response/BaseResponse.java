package ndky.paper.kpimgrapp.Response;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public class BaseResponse {
    int code;

    public BaseResponse() {
        this.code = HttpServletResponse.SC_OK;
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

    public ResponseEntity<?> responseEntity() {
        return ResponseEntity.ok().body(this);
    }
}
