package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Request.RoleRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoleMapper extends UtilMapper {
    Boolean existsRole(Long roleId, String roleName);

    Optional<Role> selectRole(RoleRequest roleRequest);

    /**
     * check if given username is the given role's creator
     */
    Boolean verifyRoleCreator(String requestedUsername, Long targetRoleId);

    List<Role> selectAllRoles(int from, int length, String query);

    /**
     * add role will not affect role_scope
     */
    Integer addRole(RoleRequest roleRequest);

    Integer addRoleScopesForRole(long roleId, List<RoleScope> roleScopes);

    Integer deleteRole(RoleRequest roleRequest);

    Integer deleteRoleScopesForRole(long roleId);

    /**
     * update role will not affect role_scope
     */
    Integer updateRole(RoleRequest roleRequest);

    @Select("select count(id)-3 from role")
    Integer selectRoleTotal();
}
