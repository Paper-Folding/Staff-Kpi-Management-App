package ndky.paper.kpimgrapp.Request;

public class UserRoleRequest {
    private Long authenticationId;
    private String username;
    private Long staffInfoId;

    public UserRoleRequest() {
    }

    public UserRoleRequest(Long authenticationId, String username, Long staffInfoId) {
        this.authenticationId = authenticationId;
        this.username = username;
        this.staffInfoId = staffInfoId;
    }

    public Long getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(Long authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStaffInfoId() {
        return staffInfoId;
    }

    public void setStaffInfoId(Long staffInfoId) {
        this.staffInfoId = staffInfoId;
    }
}
