package ndky.paper.kpimgrapp.Models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;

    private Long staffInfoId;

    private String username, realName;

    private String password;

    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long id, Long staffInfoId, String username, String realName, String password, Set<Role> roles) {
        this.id = id;
        this.staffInfoId = staffInfoId;
        this.username = username;
        this.realName = realName;
        this.password = password;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
