package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AuthenticationMapper {
    Boolean existsUser(String username);

    Optional<User> selectUserByUserName(String username);

    List<RoleScope> selectRoleScopeByUsername(String username, Integer operationId);
}
