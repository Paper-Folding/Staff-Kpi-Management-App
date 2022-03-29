package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// authorization controls resource access
@Mapper
public interface AuthorizationMapper {

    /**
     * query user's roles
     *
     * @param authenticationId
     * @param username
     * @param expired          select expired? default is false
     */
    List<Role> queryRoles(Long authenticationId, String username, Boolean expired);

    /**
     * query user permissions
     *
     * @param userPermissionRequest
     * @param expired               select expired? default is false
     */
    List<UserPermission> queryPermissions(UserPermissionRequest userPermissionRequest, Boolean expired);

    /**
     * check if specified auth user has such a role
     */
    Boolean userExistsRole(Long authenticationId, String username, Long roleId, String roleName);

    /**
     * check if user currently selected role has specific permission
     */
    Boolean userExistsPermission(UserPermissionRequest userPermissionRequest);
}
