package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.RoleScope;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoleMapper extends UtilMapper {
    Boolean existsRole(String name);

    Boolean existsRoleStrict(long roleId, String name);

    Optional<Role> selectRole(Role role);

    List<Role> selectAllRoles();

    /**
     * add role will not affect role_scope
     */
    Integer addRole(Role role);

    Integer addRoleScopesForRole(long roleId, List<RoleScope> roleScopes);

    Integer deleteRole(Role role);

    Integer deleteRoleScopesForRole(long roleId);

    /**
     * update role will not affect role_scope
     */
    Integer updateRole(Role role);
}
