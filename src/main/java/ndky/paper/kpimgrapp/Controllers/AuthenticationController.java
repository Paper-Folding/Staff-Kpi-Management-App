package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.UtilMapper;
import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Request.UserRoleRequest;
import ndky.paper.kpimgrapp.Response.JwtResponse;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Security.Jwt.JwtUtils;
import ndky.paper.kpimgrapp.Security.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getStaffInfoId(),
                userDetails.getUsername(),
                userDetails.getRealName(),
                roles).responseEntity();
    }

    @Autowired
    private UtilMapper utilMapper;

    /**
     * used for collect user roles
     */
    @PostMapping("/get/role")
    public ResponseEntity<?> queryUserRole(@RequestBody UserRoleRequest req) {
        List<Role> list = utilMapper.selectRoleForUser(req.getId(), req.getUsername());
        return new QueryResponse(list, list.size()).responseEntity();
    }

    /**
     * Used for collect user permission info
     */
    @PostMapping("/get/permission")
    public ResponseEntity<?> queryUserPermission(@RequestBody UserPermissionRequest userPermissionRequest) {
        var userPermissions = utilMapper.selectUserPermissionBy(userPermissionRequest);
        return new QueryResponse(userPermissions, userPermissions.size()).responseEntity();
    }
}
