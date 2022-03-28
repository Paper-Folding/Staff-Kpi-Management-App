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
//    @Autowired
//    private AuthenticationMapper authenticationMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthorizationMapper authorizationMapper;

    /**
     * query user roles by auth user's authorizationId or username
     *
     * @return List<Role>
     */
    public List<Role> queryRoles(UserRoleRequest userRoleRequest) {
        if (userRoleRequest.getAuthenticationId() == null && (userRoleRequest.getUsername() == null || userRoleRequest.getUsername().equals("")))
            return new ArrayList<>();
        return authorizationMapper.queryRoles(userRoleRequest.getAuthenticationId(), userRoleRequest.getUsername());
    }

    /**
     * query user permissions by multiple conditions:
     * auth user's authorizationId or username, target roleId or roleName, target operationId or operationName,
     * target tableName
     *
     * @return List<UserPermission>
     */
    public List<UserPermission> queryPermissions(UserPermissionRequest userPermissionRequest) {
        if (userPermissionRequest.getAuthenticationId() == null && (userPermissionRequest.getUsername() == null || userPermissionRequest.getUsername().equals("")))
            return new ArrayList<>();
        return authorizationMapper.queryPermissions(userPermissionRequest);
    }

    /**
     * check if specified auth user has such a role
     */
    public Boolean userHasRole(Long authenticationId, String username, Long roleId, String roleName) {
        if (authenticationId == null && (username == null || username.equals("")))
            return false;
        if (roleId == null && (roleName == null || roleName.equals("")))
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

//    /**
//     * @param username    username to check
//     * @param targetRoles which role scope should be checked existence, can pass multiple, check with "OR"
//     * @return true for permission accepted, false for denied
//     */
//    public boolean checkIfPermissionExists(String username, RoleScope... targetRoles) {
//        // 1. 通过用户名获取权限集
//        List<RoleScope> roleScopes = authenticationMapper.selectRoleScopeByUsername(username, null);
//        // 2. 检查所属权限是否能够访问此接口
//        for (RoleScope scope : roleScopes)
//            for (RoleScope target : targetRoles)
//                if (target.equals(scope))
//                    return true;
//        return false;
//    }

//    public List<RoleScope> getUserRoleScopes(String username, Integer operationId) {
//        return authenticationMapper.selectRoleScopeByUsername(username, operationId);
//    }

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
