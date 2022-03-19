package ndky.paper.kpimgrapp.Response;

import java.util.List;

public class JwtResponse extends BaseResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private Long staffInfoId;
    private String username, realName;
    private List<String> roles;

    public JwtResponse(int code, String token, Long id, Long staffInfoId, String username, String realName, List<String> roles) {
        super(code);
        this.token = token;
        this.id = id;
        this.staffInfoId = staffInfoId;
        this.username = username;
        this.realName = realName;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffInfoId() {
        return staffInfoId;
    }

    public void setStaffInfoId(Long staffInfoId) {
        this.staffInfoId = staffInfoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
