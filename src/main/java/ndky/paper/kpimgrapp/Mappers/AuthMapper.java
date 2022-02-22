package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AuthMapper {
    Boolean existsUser(String username, String password);

    Optional<User> selectUserByUserName(String username);

    List<Role> selectRoleDetailByUsername(String username);

    List<RoleScope> selectRoleScopeByUsername(String username);
}
