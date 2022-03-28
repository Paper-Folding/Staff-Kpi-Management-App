package ndky.paper.kpimgrapp.Utils;

import ndky.paper.kpimgrapp.Mappers.AuthorizationMapper;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Response.AccessDeniedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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
     * query user permissions by multiple conditions:
     * auth user's authorizationId or username, target roleId or roleName, target operationId or operationName,
     * target tableName
     *
     * @return List<UserPermission>
     */
    public List<UserPermission> queryPermissions(UserPermissionRequest userPermissionRequest) {
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

    public ResponseEntity<?> getForbiddenResponseEntity(HttpServletRequest request) {
        return new AccessDeniedResponse(request.getServletPath()).responseEntity();
    }
}
