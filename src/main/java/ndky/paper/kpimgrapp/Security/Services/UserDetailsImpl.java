package ndky.paper.kpimgrapp.Security.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ndky.paper.kpimgrapp.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private final Long id;

    private final Long staffInfoId;

    private final String username, realName;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, Long staffInfoId, String username, String realName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.staffInfoId = staffInfoId;
        this.username = username;
        this.realName = realName;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getId(),
                user.getStaffInfoId(),
                user.getUsername(),
                user.getRealName(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public Long getStaffInfoId() {
        return staffInfoId;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
