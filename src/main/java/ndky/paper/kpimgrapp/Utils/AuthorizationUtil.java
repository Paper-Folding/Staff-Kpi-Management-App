package ndky.paper.kpimgrapp.Utils;

import ndky.paper.kpimgrapp.Mappers.AuthorizationMapper;
import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.BaseQueryRequest;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Request.UserRoleRequest;
import ndky.paper.kpimgrapp.Response.AccessDeniedResponse;
import ndky.paper.kpimgrapp.Response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (userRoleRequest.getAuthenticationId() == null && (userRoleRequest.getUsername() == null || "".equals(userRoleRequest.getUsername())) && userRoleRequest.getStaffInfoId() == null)
            return new ArrayList<>();
        return authorizationMapper.queryRoles(userRoleRequest.getAuthenticationId(), userRoleRequest.getUsername(), userRoleRequest.getStaffInfoId(), false);
    }

    public List<Role> queryAllRoles() {
        return authorizationMapper.queryAllRoles();
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

    /**
     * get table fields name collection
     *
     * @return List<String>
     */
    public List<String> getFieldsForTable(String tableName, String... excludedFields) {
        return authorizationMapper.queryTableFields(tableName, Arrays.asList(excludedFields));
    }

    public Long getAuthenticationIdByStaffInfoId(Long StaffInfoId) {
        return authorizationMapper.getAuthenticationIdByStaffInfoId(StaffInfoId);
    }

    public Long getStaffInfoIdByAuthentication(Long authenticationId, String username) {
        return authorizationMapper.getStaffInfoIdByAuthentication(authenticationId, username);
    }

    /**
     * Make sure a role provided within a request is valid
     *
     * @param baseQueryRequest a base query request body
     * @param request          HttpServletRequest
     * @return if invalid return an access denied response while null if valid
     */
    public ResponseEntity<?> ensureRoleIsValidForRequest(BaseQueryRequest baseQueryRequest, HttpServletRequest request) {
        String username = getUsernameFromRequest(request);
        // 1. check if role field is present, this is used for returning role permitted fields
        if (baseQueryRequest.getRole() == null || "".equals(baseQueryRequest.getRole()))
            return new ErrorResponse("No role is provided.").responseEntity();
        // 2. check if logged user has provided role
        if (!userHasRole(null, username, null, baseQueryRequest.getRole()))
            return new ErrorResponse("Requesting user does not have such a role.").responseEntity();
        return null;
    }
}
