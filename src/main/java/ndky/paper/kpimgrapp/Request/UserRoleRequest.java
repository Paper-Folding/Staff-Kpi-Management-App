package ndky.paper.kpimgrapp.Request;

public class UserRoleRequest {
    private Long authenticationId;
    private String username;

    public UserRoleRequest() {
    }

    public UserRoleRequest(Long authenticationId, String username) {
        this.authenticationId = authenticationId;
        this.username = username;
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
}
