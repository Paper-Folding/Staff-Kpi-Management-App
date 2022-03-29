package ndky.paper.kpimgrapp.Utils;

import ndky.paper.kpimgrapp.Mappers.AuthorizationMapper;
import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Request.UserRoleRequest;
import ndky.paper.kpimgrapp.Response.AccessDeniedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * AuthorizationUtil
 * Used for dealing with complicated user - role - role scope relations and chores.
 */
@Component
public class AuthorizationUtil {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthorizationMapper authorizationMapper;

    /**
     * query user roles by auth user's authenticationId or username
     *
     * @return List<Role>
     */
    public List<Role> queryRoles(UserRoleRequest userRoleRequest) {
        if (userRoleRequest.getAuthenticationId() == null && (userRoleRequest.getUsername() == null || "".equals(userRoleRequest.getUsername())))
            return new ArrayList<>();
        return authorizationMapper.queryRoles(userRoleRequest.getAuthenticationId(), userRoleRequest.getUsername(), false);
    }

    /**
     * query user permissions by multiple conditions:
     * auth user's authenticationId or username, target roleId or roleName, target operationId or operationName,
     * target tableName
     *
     * @return List<UserPermission>
     */
    public List<UserPermission> queryPermissions(UserPermissionRequest userPermissionRequest) {
        if (userPermissionRequest.getAuthenticationId() == null && (userPermissionRequest.getUsername() == null || "".equals(userPermissionRequest.getUsername())))
            return new ArrayList<>();
        return authorizationMapper.queryPermissions(userPermissionRequest, false);
    }

    /**
     * check if specified auth user has such a role
     */
    public Boolean userHasRole(Long authenticationId, String username, Long roleId, String roleName) {
        if (authenticationId == null && (username == null || "".equals(username)))
            return false;
        if (roleId == null && (roleName == null || "".equals(roleName)))
            return false;
        return authorizationMapper.userExistsRole(authenticationId, username, roleId, roleName);
    }

    /**
     * check if specified auth user has such a permission
     */
    public Boolean userHasPermission(UserPermissionRequest userPermissionRequest) {
        if (userPermissionRequest.getAuthenticationId() == null && (userPermissionRequest.getUsername() == null || userPermissionRequest.getUsername().equals("")))
            return false;
        if (userPermissionRequest.getRoleId() == null && (userPermissionRequest.getRoleName() == null || userPermissionRequest.getRoleName().equals("")))
            return false;
        return authorizationMapper.userExistsPermission(userPermissionRequest);
    }

    /**
     * get username from authorization field of request header
     */
    public String getUsernameFromRequest(HttpServletRequest request) {
        return jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromRequest(request));
    }

    /**
     * This method returns an error response body with a msg contains currently requested path
     */
    public ResponseEntity<?> getForbiddenResponseEntity(HttpServletRequest request) {
        return new AccessDeniedResponse(request.getServletPath()).responseEntity();
    }
}
