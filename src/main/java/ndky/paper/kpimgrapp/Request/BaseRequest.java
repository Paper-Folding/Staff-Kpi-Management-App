package ndky.paper.kpimgrapp.Request;

public class BaseRequest {
    // this is the role name that tells backend currently logged user is using which role
    private String role;

    public BaseRequest() {
    }

    public BaseRequest(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
